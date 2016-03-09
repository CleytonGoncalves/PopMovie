package com.cleytongoncalves.popmovies.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public final class Movie implements Parcelable {
    private final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";

    public final String name;
    public final String posterPath;
    public final String releaseYear;
    public final String rating;
    public final String overview;

    public Movie(String name, String posterPath, String releaseYear, String rating, String overview) {
        this.name = name;
        this.posterPath = IMAGE_BASE_URL + posterPath;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.overview = overview;
    }

    protected Movie(Parcel in) {
        name = in.readString();
        posterPath = in.readString();
        releaseYear = in.readString();
        rating = in.readString();
        overview = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(posterPath);
        dest.writeString(releaseYear);
        dest.writeString(rating);
        dest.writeString(overview);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}