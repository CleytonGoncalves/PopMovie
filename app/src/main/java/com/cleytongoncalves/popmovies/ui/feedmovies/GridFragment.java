package com.cleytongoncalves.popmovies.ui.feedmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.cleytongoncalves.popmovies.R;
import com.cleytongoncalves.popmovies.data.models.Movie;
import com.cleytongoncalves.popmovies.data.models.MovieResponse;
import com.cleytongoncalves.popmovies.data.remote.MoviesService.Creator;
import com.cleytongoncalves.popmovies.ui.detailmovie.DetailActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

import static com.cleytongoncalves.popmovies.data.remote.MoviesService.Creator.newMoviesService;

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

        updateMovies();
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
        Creator.requestMovieResponse(this, mSortBy.value, newMoviesService());
    }

    public void setMoviesAdapter(MovieResponse response) {
        if (response != null && !response.getResults().isEmpty()) {
            mMoviesAdapter.clear();
            mMoviesAdapter.addAll(response.getResults());
        }
    }

    private enum Sort {
        POPULARITY ("popularity.desc"),
        RATING ("vote_average.desc");

        private final String value;

        Sort(String value) {
            this.value = value;
        }
    }

}
