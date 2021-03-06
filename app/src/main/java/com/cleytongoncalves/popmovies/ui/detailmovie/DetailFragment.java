package com.cleytongoncalves.popmovies.ui.detailmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cleytongoncalves.popmovies.R;
import com.cleytongoncalves.popmovies.data.models.Movie;

import butterknife.Bind;
import butterknife.ButterKnife;


public class DetailFragment extends Fragment {
    private final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";

    @Bind(R.id.movie_name_detail) TextView name;
    @Bind(R.id.movie_release_year_detail) TextView releaseYear;
    @Bind(R.id.movie_rating_detail) TextView rating;
    @Bind(R.id.movie_overview_detail) TextView overview;
    @Bind(R.id.movie_poster_detail) ImageView poster;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        ButterKnife.bind(this, rootView);

        Intent callerIntent = getActivity().getIntent();

        if (callerIntent != null && callerIntent.hasExtra("movie")) {
            final Movie movie = callerIntent.getParcelableExtra("movie");

            name.setText(movie.getTitle());
            releaseYear.setText(movie.getReleaseYear());
            rating.setText(movie.getVoteAverage());
            overview.setText(movie.getOverview());

            Glide.with(this)
                    .load(POSTER_BASE_URL + movie.getPosterPath())
                    .placeholder(R.drawable.placeholder_poster)
                    .error(R.drawable.error_poster)
                    .centerCrop()
                    .crossFade()
                    .into(poster);
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
