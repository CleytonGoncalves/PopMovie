package com.cleytongoncalves.popmovies.data.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

@JsonObject(fieldNamingPolicy = JsonObject.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
public final class Movie implements Parcelable {
    //w185, w342, w500, w780, w1000, original
    //private final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";
    //private final String BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w342";

    @JsonField private int id;
    @JsonField private String title;
    @JsonField private String releaseDate;
    @JsonField private String overview;
    @JsonField private String voteAverage;
    @JsonField private String posterPath;
    @JsonField private String backdropPath;

    private String releaseYear;

    Movie() {
        this.releaseYear = null;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    /* Method used to return a proper formatted year from the releaseDate */
    public String getReleaseYear() {
        String releaseYear;

        if (releaseDate == null || releaseDate.length() < 8) {
            releaseYear = "----";
        } else {
            releaseYear = releaseDate.substring(0, 4);
        }

        return releaseYear;
    }

    void setId(int id) {
        this.id = id;
    }

    void setTitle(String title) {
        this.title = title;
    }

    void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    void setOverview(String overview) {
        this.overview = overview;
    }

    void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.voteAverage);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeString(this.releaseYear);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.voteAverage = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.releaseYear = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}