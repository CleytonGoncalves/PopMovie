package com.cleytongoncalves.popmovies.data.remote;

import com.cleytongoncalves.popmovies.data.models.MovieResponse;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesService {
    String ENDPOINT = "http://api.themoviedb.org/3/discover/movie";
    String SORT_PARAM = "sort_by";

    @GET("discover/movie")
    Call<MovieResponse> getMovieFeed(@Query(SORT_PARAM) String sort);

    /*****
     * Helper class to set up new service
     *****/
    class Creator {
        public static MoviesService newMoviesService() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .client(httpClient)
                    .addConverterFactory(new LoganSquareConverter())
                    .build();

            return retrofit.create(MoviesService.class);
        }

        private final static OkHttpClient httpClient = new OkHttpClient
                .Builder()
                .addInterceptor(RequestInterceptors.API_KEY)
                .addInterceptor(RequestInterceptors.CACHING_CONTROL)
                .build();
    }
}