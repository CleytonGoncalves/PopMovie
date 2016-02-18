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


public class DetailFragment extends Fragment {

    private Movie mMovie;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent callerIntent = getActivity().getIntent();

        if (callerIntent != null && callerIntent.hasExtra("movie")) {
            mMovie = callerIntent.getParcelableExtra("movie");

            ((TextView) rootView.findViewById(R.id.movie_name_detail)).setText(mMovie.name);
            ((TextView) rootView.findViewById(R.id.movie_release_year_detail)).setText(mMovie.releaseYear);
            ((TextView) rootView.findViewById(R.id.movie_rating_detail)).setText(mMovie.rating);
            ((TextView) rootView.findViewById(R.id.movie_overview_detail)).setText(mMovie.overview);
            Glide.with(this)
                    .load(mMovie.posterPath)
                    .placeholder(R.drawable.placeholder_poster)
                    .error(R.drawable.error_poster)
                    .centerCrop()
                    .crossFade()
                    .into((ImageView) rootView.findViewById(R.id.movie_poster_detail));
        }

        return rootView;
    }

}
