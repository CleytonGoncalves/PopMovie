package com.cleytongoncalves.popmovies.ui.feedmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.cleytongoncalves.popmovies.BuildConfig;
import com.cleytongoncalves.popmovies.R;
import com.cleytongoncalves.popmovies.ui.detailmovie.DetailActivity;
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
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class GridFragment extends Fragment {
    private GridViewAdapter mMoviesAdapter;
    private Sort mSortBy = Sort.POPULARITY;

    @Bind(R.id.grid_view) GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mMoviesAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item_view, new
                ArrayList<Movie>());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, rootView);

        gridView.setAdapter(mMoviesAdapter);

        return rootView;
    }

    @OnItemClick(R.id.grid_view)
    protected void onItemClick(int position) {
        Movie movieClicked = (Movie) mMoviesAdapter.getItem(position);

        Intent intent = new Intent(getActivity(), DetailActivity.class)
                .putExtra("movie", movieClicked);

        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_fragment, menu);

        MenuItem menu_sort_popularity = menu.findItem(R.id.menu_sort_popularity);
        MenuItem menu_sort_rating = menu.findItem(R.id.menu_sort_rating);

        if (mSortBy == Sort.RATING) {
            if (!menu_sort_rating.isChecked()) {
                menu_sort_rating.setChecked(true);
            }
        } else {
            if (!menu_sort_popularity.isChecked()) {
                menu_sort_popularity.setChecked(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_sort_popularity:
                item.setChecked(true);
                mSortBy = Sort.POPULARITY;
                updateMovies();
                return true;

            case R.id.menu_sort_rating:
                item.setChecked(true);
                mSortBy = Sort.RATING;
                updateMovies();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateMovies() {
        MovieDataFetcher movieTask = new MovieDataFetcher();
        movieTask.execute(mSortBy);
    }

    /*****
     * SORTING VALUE TO MovieDataFetcher.SORT_PARAM
     *****/
    private enum Sort {
        POPULARITY ("popularity.desc"),
        RATING ("vote_average.desc");

        private final String value;

        Sort(String value) {
            this.value = value;
        }
    }

    /*****
     * INNER-CLASS TO DO BACKGROUND FETCHING AND PARSING OF THE DATA
     *****/
    class MovieDataFetcher extends AsyncTask<Sort, Void, Movie[]> {
        private final String LOG_TAG = MovieDataFetcher.class.getSimpleName();

        @Override
        protected Movie[] doInBackground(Sort... params) {

            if (params.length == 0) {
                return null;
            }

            //Must be declared outside, because if a error happens anytime after their
            // declaration, it would remain open
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String discoverJsonStr = null;

            try {

                final String BASE_URL = "http://api.themoviedb.org/3/discover/movie";
                final String SORT_PARAM = "sort_by";
                final String API_PARAM = "api_key";

                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_PARAM, params[0].value)
                        .appendQueryParameter(API_PARAM, BuildConfig.TMDB_API_KEY)
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
            final String TMDB_RELEASE_DATE = "release_date";
            final String TMDB_RATING = "vote_average";
            final String TMDB_OVERVIEW = "overview";

            JSONObject discoverJson = new JSONObject(discoverJsonStr);
            JSONArray moviesJsonArray = discoverJson.getJSONArray(TMDB_RESULTS);

            Movie[] resultMovies = new Movie[moviesJsonArray.length()];

            for (int i = 0; i < moviesJsonArray.length(); i++) {
                JSONObject movieData = moviesJsonArray.getJSONObject(i);

                String movieName = movieData.getString(TMDB_TITLE);
                String posterPath = movieData.getString(TMDB_POSTER_PATH);
                String releaseYear = extractYear(movieData.getString(TMDB_RELEASE_DATE));
                String rating = movieData.getString(TMDB_RATING);
                String overview = movieData.getString(TMDB_OVERVIEW);

                resultMovies[i] = new Movie(movieName, posterPath, releaseYear, rating, overview);
            }

            return resultMovies;
        }

        private String extractYear(String releaseDate) {
            try {
                return releaseDate.substring(0, 4);
            } catch (StringIndexOutOfBoundsException e) {
                return "----";
            }
        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            if (movies != null) {
                mMoviesAdapter.clear();
                mMoviesAdapter.addAll(Arrays.asList(movies));
            }
        }
    }
}
