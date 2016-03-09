package com.cleytongoncalves.popmovies.data.remote;

import android.util.Log;

import com.cleytongoncalves.popmovies.BuildConfig;
import com.cleytongoncalves.popmovies.util.NetworkUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/*****
 * Intercepts the request to add Api Key, Log time and Cache
 *****/
public final class RequestInterceptors {
    private static final String LOG_TAG = RequestInterceptors.class.getSimpleName();
    private static final String API_PARAM = "appid";

    public static final Interceptor API_KEY = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            String requestLog = String.format("Sending request %s on %s%n%s", original.url(),
                    chain.connection(), original.headers());
            Log.d(LOG_TAG, requestLog);

            long t1 = System.nanoTime();

            HttpUrl newUrl = original.url().newBuilder()
                    .addQueryParameter(API_PARAM, BuildConfig.TMDB_API_KEY)
                    .build();

            Request newRequest = original.newBuilder().url(newUrl).build();

            Response response = chain.proceed(newRequest);

            long t2 = System.nanoTime();

            String responseLog = String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers());
            Log.d(LOG_TAG, responseLog);

            return response;
        }
    };

    public static final Interceptor CACHING_CONTROL = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            String cacheHeaderValue = NetworkUtils.isOnline() ? "public, max-age=60" :
                    "public, only-if-cached";

            request = request.newBuilder().header("Cache-Control", cacheHeaderValue).build();

            return chain.proceed(request);
        }
    };

}
