package mario.android.tvseries;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import mario.android.tvseries.model.TVSeriesService;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by Mario on 14.9.2016.
 */
public class SeriesApplication extends Application {

    private TVSeriesService mTVSeriesService;
    private Scheduler defaultSubscribeScheduler;

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("TVShows.realm")
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static SeriesApplication get(Context context){
        return (SeriesApplication) context.getApplicationContext();
    }

    public TVSeriesService getTVSeriesService(){
        if (mTVSeriesService == null){
            mTVSeriesService = TVSeriesService.Factory.create();
        }
        return mTVSeriesService;
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }
}
