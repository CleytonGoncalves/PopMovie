package com.cleytongoncalves.popmovies.data.models;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

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
    @JsonField private Date releaseDate;
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getReleaseYear() {
        if (releaseDate == null || releaseDate.toString().length() < 4) {
            releaseYear = "----";
        } else {
            releaseYear = releaseDate.toString();
        }

        return releaseYear;
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

    @Override public String toString() {
        return title + " / " + releaseDate + "\n";
    }

    void setId(int id) {
        this.id = id;
    }

    void setTitle(String title) {
        this.title = title;
    }

    void setReleaseDate(Date releaseDate) {
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
        dest.writeLong(this.releaseDate != null ? this.releaseDate.getTime() : - 1);
        dest.writeString(this.overview);
        dest.writeString(this.voteAverage);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeString(this.releaseYear);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        long tmpReleaseDate = in.readLong();
        this.releaseDate = tmpReleaseDate == - 1 ? null : new Date(tmpReleaseDate);
        this.overview = in.readString();
        this.voteAverage = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.releaseYear = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}