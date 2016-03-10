package com.cleytongoncalves.popmovies.data.remote;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;

import com.cleytongoncalves.popmovies.App;
import com.cleytongoncalves.popmovies.data.models.MovieResponse;
import com.cleytongoncalves.popmovies.ui.feedmovies.GridFragment;
import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesService {
    String ENDPOINT = "http://api.themoviedb.org/3/";
    String SORT_PARAM = "sort_by";

    @GET("discover/movie")
    Call<MovieResponse> getMovieFeed(@Query(SORT_PARAM) String sort);

    /*****
     * Helper class to set up new service and handle Asynchronous request
     *****/
    class Creator {
        private final static String LOG_TAG = Creator.class.getSimpleName();

        private final static OkHttpClient httpClient = new OkHttpClient.Builder()
                .cache(new Cache(App.getApplication().getCacheDir(), 10 * 1024 * 1024)) //10MB
                .addInterceptor(RequestInterceptors.API_KEY)
                .build();

        public static MoviesService newMoviesService() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .client(httpClient)
                    .addConverterFactory(LoganSquareConverterFactory.create())
                    .build();

            return retrofit.create(MoviesService.class);
        }

        public static void requestMovieResponse(final GridFragment fragment, String sort, MoviesService service) {
            Call<MovieResponse> call = service.getMovieFeed(sort);

            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    if (response.isSuccess()) {
                        MovieResponse movieResponse = response.body();
                        fragment.setMoviesAdapter(movieResponse);
                        Log.d(LOG_TAG, "SUCCESS: " + movieResponse);
                    } else {
                        Log.w(LOG_TAG, "ERROR: Response from onResponse method failed: " +
                                response.errorBody());
                    }
                }

                @Override public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.w(LOG_TAG, "ERROR: Failure to receive response. " + t.getMessage(), t);
                }
            });
        }
    }

}