package com.cleytongoncalves.popmovies;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class GridFragment extends Fragment {
    private List<Movie> mMovies;

    public GridFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grid_view_fragment, container, false);

        createDummyData();

        GridViewAdapter gridAdapter = new GridViewAdapter(rootView.getContext(), R.layout.grid_item_view, mMovies);

        GridView gridView = (GridView) rootView.findViewById(R.id.grid_view);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movieClicked = mMovies.get(position);

                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra("movie", movieClicked);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void createDummyData() {
        List<Movie> items = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);

        for (int i = 0; i < imgs.length(); i++) {
            int imageId = imgs.getResourceId(i, -1);
            items.add(new Movie(getResources().getStringArray(R.array.movies_name)[i], imageId, "2016", "6.6/10", "When her family is gunned down in cold blood, a young girl convinces a bounty hunter to train her as a gunfighter so she can seek vengeance with a six-shooter."));
        }

        mMovies = items;
    }
}
