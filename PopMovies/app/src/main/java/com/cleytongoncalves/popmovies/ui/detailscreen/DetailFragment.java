package com.cleytongoncalves.popmovies.ui.detailscreen;

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
import com.cleytongoncalves.popmovies.ui.models.Movie;

import butterknife.Bind;


public class DetailFragment extends Fragment {
    private Movie mMovie;

    @Bind(R.id.movie_name_detail) TextView name;
    @Bind(R.id.movie_release_year_detail) TextView releaseYear;
    @Bind(R.id.movie_rating_detail) TextView rating;
    @Bind(R.id.movie_overview_detail) TextView overview;

    @Bind(R.id.movie_poster_detail) ImageView poster;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent callerIntent = getActivity().getIntent();

        if (callerIntent != null && callerIntent.hasExtra("movie")) {
            mMovie = callerIntent.getParcelableExtra("movie");

            name.setText(mMovie.name);
            releaseYear.setText(mMovie.releaseYear);
            rating.setText(mMovie.rating);
            overview.setText(mMovie.overview);

            Glide.with(this)
                    .load(mMovie.posterPath)
                    .placeholder(R.drawable.placeholder_poster)
                    .error(R.drawable.error_poster)
                    .centerCrop()
                    .crossFade()
                    .into(poster);
        }

        return rootView;
    }

}
