package com.cleytongoncalves.popmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
        TextView name_view;
        ImageView poster_view;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);

            convertView.setTag(R.id.movie_name_textView, convertView.findViewById(R.id.movie_name_textView));
            convertView.setTag(R.id.movie_poster_imageView, convertView.findViewById(R.id.movie_poster_imageView));
        }

        name_view = (TextView) convertView.getTag(R.id.movie_name_textView);
        poster_view = (ImageView) convertView.getTag(R.id.movie_poster_imageView);

        Movie item = (Movie) data.get(position);

        Glide.with(parent.getContext())
                .load(item.drawableId)
                .placeholder(R.drawable.placeholder_poster)
                .error(R.drawable.error_poster)
                .centerCrop()
                .crossFade()
                .into(poster_view);

        name_view.setText(item.name);

        return convertView;
    }
}