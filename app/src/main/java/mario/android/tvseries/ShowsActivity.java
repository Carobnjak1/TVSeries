package mario.android.tvseries;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import mario.android.tvseries.model.TVSeriesService;
import mario.android.tvseries.model.show.Show;
import mario.android.tvseries.model.show.TVShow;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class ShowsActivity extends AppCompatActivity {

    private static final String TAG = "ShowsActivity";

    private Subscription subscription;
    private RecyclerView showsRecyclerView;
    private Toolbar toolbar;
    private EditText showNameEditText;
    private ProgressBar progressBar;
    private TextView infoTextView;
    private ImageButton searchButton;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows);

        realm = Realm.getDefaultInstance();

        progressBar = (ProgressBar) findViewById(R.id.progress);
        infoTextView = (TextView) findViewById(R.id.text_info);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showsRecyclerView = (RecyclerView) findViewById(R.id.shows_recycler_view);
        setupRecyclerView(showsRecyclerView);

        searchButton = (ImageButton) findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTVSeries(showNameEditText.getText().toString());
            }
        });

        showNameEditText = (EditText) findViewById(R.id.edit_text_show_name);
        showNameEditText.addTextChangedListener(mHideShowButtonTextWatcher);
        showNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String seriesName = showNameEditText.getText().toString();
                    if (seriesName.length() > 0) {
                        loadTVSeries(seriesName);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void loadTVSeries(String seriesName) {
        progressBar.setVisibility(View.VISIBLE);
        showsRecyclerView.setVisibility(View.GONE);
        infoTextView.setVisibility(View.GONE);
        SeriesApplication application = SeriesApplication.get(this);
        TVSeriesService mTVSeriesService = application.getTVSeriesService();

        subscription = mTVSeriesService.listOfShows(seriesName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<TVShow>>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                        if (showsRecyclerView.getAdapter().getItemCount() > 0) {
                            showsRecyclerView.requestFocus();
                            hideSoftKeyboard();
                            showsRecyclerView.setVisibility(View.VISIBLE);
                        } else {
                            infoTextView.setText("No search results");
                            infoTextView.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "Error loading data ", error);
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
                    public void onNext(List<TVShow> tvShows) {

                        // find all shows with "like" in DB
                        RealmQuery<Show> queryLikes = realm.where(Show.class);
                        RealmResults<Show> result = queryLikes.findAll();

                        // compare list from API with list from DB
                        for (Show showInDb : result) {
                            for (int i = 0; i < tvShows.size(); i++) {
                                if (showInDb.getId().equals(tvShows.get(i).getShow().getId())) {
                                    // set liked to true so we can use this data in adapter
                                    tvShows.get(i).getShow().setLiked(true);
                                }
                            }
                        }


                        ShowsAdapter adapter = (ShowsAdapter) showsRecyclerView.getAdapter();
                        adapter.setShowList(tvShows);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void setupRecyclerView(RecyclerView seriesRecyclerView) {
        final ShowsAdapter adapter = new ShowsAdapter();
        adapter.setCallback(new ShowsAdapter.Callback() {
            @Override
            public void onItemClick(TVShow tvShow) {
                Intent i = new Intent(ShowsActivity.this, EpisodesActivity.class);
                i.putExtra("tvShowId", tvShow.getShow().getId());
                i.putExtra("tvShowName", tvShow.getShow().getName());
                i.putExtra("tvShowUrl", tvShow.getShow().getUrl());
                startActivity(i);
            }
        });

        adapter.setLikeCallback(new ShowsAdapter.LikeCallback() {
            @Override
            public void onLikeClick(final TVShow tvShow, int position) {

                if (tvShow.getShow().isLiked()) {
                    // we set like to false and delete Show from DB
                    adapter.setShowLike(position, false);

                    final Show resultUnlike = realm.where(Show.class)
                            .equalTo("id", tvShow.getShow().getId())
                            .findFirst();

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            resultUnlike.deleteFromRealm();
                        }
                    });
                } else {

                    // we set like to true and add Show to DB
                    adapter.setShowLike(position, true);

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Show showDb = realm.createObject(Show.class); // Create a new object
                            showDb.setName(tvShow.getShow().getName());
                            showDb.setId(tvShow.getShow().getId());
                        }
                    });

                }

                adapter.notifyDataSetChanged();
            }
        });

        seriesRecyclerView.setAdapter(adapter);
        seriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private TextWatcher mHideShowButtonTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            searchButton.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(showNameEditText.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null) subscription.unsubscribe();

        if (realm != null) realm.close();
    }
}
