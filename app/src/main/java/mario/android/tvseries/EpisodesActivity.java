package mario.android.tvseries;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import mario.android.tvseries.model.TVSeriesService;
import mario.android.tvseries.model.episodes.Episodes;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;


public class EpisodesActivity extends AppCompatActivity {

    private static final String TAG = "EpisodesActivity";

    private Subscription subscription;
    private RecyclerView tvEpisodeRecyclerView;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private TextView infoTextView;
    private String tvShowId;
    private String tvShowName;
    private String tvShowUrl;
    private Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);

        tvShowId = getIntent().getStringExtra("tvShowId");
        tvShowName = getIntent().getStringExtra("tvShowName");
        tvShowUrl = getIntent().getStringExtra("tvShowUrl");

        realm = Realm.getDefaultInstance();

        toolbar = (Toolbar) findViewById(R.id.episodes_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(tvShowName);
        }

        progressBar = (ProgressBar) findViewById(R.id.episodes_loading_progress);
        tvEpisodeRecyclerView = (RecyclerView) findViewById(R.id.episodes_recycler_view);
        setupRecyclerView(tvEpisodeRecyclerView);

        infoTextView = (TextView) findViewById(R.id.episodes_text_info);

        loadEpisodes(tvShowId);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.episode_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, ShowsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;

            case R.id.more_info:
                Intent i = new Intent(EpisodesActivity.this, ShowInfoActivity.class);
                i.putExtra("tvShowUrl", tvShowUrl);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupRecyclerView(RecyclerView tvShowRecyclerView) {
        final EpisodesAdapter adapter = new EpisodesAdapter();

        // when user click on checkbox we add or delete data in DB
        adapter.setCheckedCallback(new EpisodesAdapter.CheckedCallback() {
            @Override
            public void onCheckBoxClick(final Episodes episode, int position) {

                if (episode.isWatched()) {
                    adapter.setEpisodeWatched(position, false);

                    final Episodes resultUnwatched = realm.where(Episodes.class)
                            .equalTo("id", episode.getId())
                            .findFirst();

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            resultUnwatched.deleteFromRealm();
                        }
                    });
                } else {

                    adapter.setEpisodeWatched(position, true);

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Episodes episodeDB = realm.createObject(Episodes.class);
                            episodeDB.setTvShowId(tvShowId);
                            episodeDB.setWatched(true);
                            episodeDB.setId(episode.getId());
                        }
                    });
                }
                adapter.notifyDataSetChanged();
            }
        });

        tvShowRecyclerView.setAdapter(adapter);
        tvShowRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadEpisodes(String id) {
        progressBar.setVisibility(View.VISIBLE);
        tvEpisodeRecyclerView.setVisibility(View.GONE);
        infoTextView.setVisibility(View.GONE);

        SeriesApplication application = SeriesApplication.get(this);
        TVSeriesService mTVSeriesService = application.getTVSeriesService();

        subscription = mTVSeriesService.listOfEpisodes(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<Episodes>>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                        if (tvEpisodeRecyclerView.getAdapter().getItemCount() > 0) {
                            tvEpisodeRecyclerView.requestFocus();
                            tvEpisodeRecyclerView.setVisibility(View.VISIBLE);
                        } else {
                            infoTextView.setText("No search results");
                            infoTextView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "Error loading list of episodes", error);
                        progressBar.setVisibility(View.GONE);
                        if (error instanceof HttpException
                                && ((HttpException) error).code() == 404) {
                            infoTextView.setText(R.string.not_found_404);
                        } else {
                            infoTextView.setText(R.string.error_loading_data);
                        }
                        infoTextView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(List<Episodes> episodes) {

                        RealmResults<Episodes> episodesRealmResults = realm.where(Episodes.class)
                                .equalTo("tvShowId", tvShowId)
                                .findAll();


                        if (episodesRealmResults.size() > 0) {
                            for (Episodes episodeInDb : episodesRealmResults) {
                                for (int i = 0; i < episodes.size(); i++) {
                                    if (episodeInDb.getId().equals(episodes.get(i).getId())) {
                                        episodes.get(i).setWatched(true);
                                    }
                                }
                            }
                        }

                        EpisodesAdapter adapter = (EpisodesAdapter) tvEpisodeRecyclerView.getAdapter();
                        adapter.setEpisodesList(episodes);
                        adapter.notifyDataSetChanged();
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null) subscription.unsubscribe();

        if (realm != null) realm.close();
    }
}
