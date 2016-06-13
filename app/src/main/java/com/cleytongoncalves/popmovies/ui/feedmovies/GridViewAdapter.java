package com.cleytongoncalves.popmovies.ui.feedmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cleytongoncalves.popmovies.R;
import com.cleytongoncalves.popmovies.data.models.Movie;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GridViewAdapter extends ArrayAdapter {
    private final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";

    private Context context;
    private int layoutResourceId;
    private List data;

    public GridViewAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResourceId = resource;
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Movie item = (Movie) data.get(position);

        Glide.with(parent.getContext())
                .load(POSTER_BASE_URL + item.getPosterPath())
                .placeholder(R.drawable.placeholder_poster)
                .error(R.drawable.error_poster)
                .centerCrop()
                .crossFade()
                .into(holder.posterView);

        holder.name.setText(item.getTitle());

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.movie_poster_imageView) ImageView posterView;
        @Bind(R.id.movie_name_textView) TextView name;

        private ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
    }
}