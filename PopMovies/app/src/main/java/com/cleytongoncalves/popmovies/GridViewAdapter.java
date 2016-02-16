package com.cleytongoncalves.popmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private List data = new ArrayList();

    public GridViewAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResourceId = resource;
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView name;
        ImageView poster;

        if (v == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            v = inflater.inflate(layoutResourceId, parent, false);

            name = (TextView) v.findViewById(R.id.movie_name_textView);
            poster = (ImageView) v.findViewById(R.id.movie_poster_imageView);

            v.setTag(R.id.movie_name_textView, name);
            v.setTag(R.id.movie_poster_imageView, poster);
        } else {
            name = (TextView) v.getTag(R.id.movie_name_textView);
            poster = (ImageView) v.getTag(R.id.movie_poster_imageView);
        }

        Item item = (Item) data.get(position);
        name.setText(item.name);
        poster.setImageResource(item.drawableId);

        return v;
    }

    protected static class Item {
        private final String name;
        private final int drawableId;

        public Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }
}