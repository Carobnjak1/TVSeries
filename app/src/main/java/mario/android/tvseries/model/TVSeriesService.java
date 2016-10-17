package mario.android.tvseries.model;

import java.util.List;

import mario.android.tvseries.model.episodes.Episodes;
import mario.android.tvseries.model.show.TVShow;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


public interface TVSeriesService {

    @GET("search/shows")
    Observable<List<TVShow>> listOfShows(@Query("q") String name);

    @GET("shows/{id}/episodes")
    Observable<List<Episodes>> listOfEpisodes(@Path("id") String id);


    class Factory {
        public static TVSeriesService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.tvmaze.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(TVSeriesService.class);
        }
    }
}
