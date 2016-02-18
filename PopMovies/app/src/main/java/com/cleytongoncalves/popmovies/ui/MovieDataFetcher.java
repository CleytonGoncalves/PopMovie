package com.cleytongoncalves.popmovies.ui;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.cleytongoncalves.popmovies.ui.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*****
 * INNER-CLASS TO DO BACKGROUND FETCHING AND PARSING OF THE DATA, COMPLETELY DECOUPLED FROM
 * THE ACTIVITY (MEANING IT CAN SURVIVE ORIENTATION CHANGE INTACT)
 */
public class MovieDataFetcher extends AsyncTask<String, Void, Movie[]> {
    private final String LOG_TAG = MovieDataFetcher.class.getSimpleName();
    private final String API_KEY = "7ca7df9023524265afdf4479d07c4ad0";

    public final String SORT_POPULARITY = "0";
    public final String SORT_RATING = "1";

    @Override
    protected Movie[] doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }

        //Must be declared outside, because if a error happens anytime after their declaration, it would remain open
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String discoverJsonStr = null;

        try {

            final String BASE_URL = "http://api.themoviedb.org/3/discover/movie";
            final String SORT_PARAM = "sort_by";
            final String API_PARAM = "api_key";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_PARAM, params[0] == SORT_RATING ? "vote_average.desc" : "popularity.desc")
                    .appendQueryParameter(API_PARAM, API_KEY)
                    .build();

            URL theMovieDb = new URL(builtUri.toString());

            //Create the request and open connection
            urlConnection = (HttpURLConnection) theMovieDb.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //Gets input stream from the page
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }

            if (builder.length() == 0) {
                //Stream was empty
                return null;
            }

            discoverJsonStr = builder.toString();
        } catch (IOException e) {
            //Happens if connection doesn't open
            Log.e(LOG_TAG, "Error", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error closing the stream", e);
                }
            }
        }

        try {
            return getDiscoverDataFromJson(discoverJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

        //Happens only if it fails to get or parse the data
        return null;
    }

    private Movie[] getDiscoverDataFromJson(String discoverJsonStr) throws JSONException {

        final String TMDB_RESULTS = "results";
        final String TMDB_TITLE = "title";
        final String TMDB_POSTER_PATH = "poster_path";
        final String TMDB_RELEASE_DATE = "releaseYear";
        final String TMDB_RATING = "vote_average";
        final String TMDB_OVERVIEW = "overview";

        JSONObject discoverJson = new JSONObject(discoverJsonStr);
        JSONArray moviesJsonArray = discoverJson.getJSONArray(TMDB_RESULTS);

        Movie[] resultMovies = new Movie[moviesJsonArray.length()];

        for (int i = 0; i < moviesJsonArray.length(); i++) {
            JSONObject movieData = moviesJsonArray.getJSONObject(i);

            String movieName = movieData.getString(TMDB_TITLE);
            String posterPath = movieData.getString(TMDB_POSTER_PATH);
            String release_year = extractYear(movieData.getString(TMDB_RELEASE_DATE));
            String rating = movieData.getString(TMDB_RATING);
            String overview = movieData.getString(TMDB_OVERVIEW);

            resultMovies[i] = new Movie(movieName, posterPath, release_year, rating, overview);
        }

        return resultMovies;
    }

    private String extractYear(String releaseDate) {
        return releaseDate.substring(0, 3);
    }
}