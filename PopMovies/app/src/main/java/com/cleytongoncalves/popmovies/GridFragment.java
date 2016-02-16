package com.cleytongoncalves.popmovies;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class GridFragment extends Fragment {

    public GridFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grid_view_fragment, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.grid_view);
        gridView.setAdapter(new GridViewAdapter(rootView.getContext(), R.layout.grid_item_view, createDummyData()));

        return rootView;
    }

    private List<GridViewAdapter.Item> createDummyData() {
        List<GridViewAdapter.Item> items = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);

        for (int i = 0; i < imgs.length(); i++) {
            int imageId = imgs.getResourceId(i, -1);
            items.add(new GridViewAdapter.Item(getResources().getStringArray(R.array.movies_name)[i], imageId));
        }

        return items;
    }
}
