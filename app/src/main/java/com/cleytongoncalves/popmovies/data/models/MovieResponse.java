package com.cleytongoncalves.popmovies.data.models;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonObject(fieldNamingPolicy = JsonObject.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
public final class MovieResponse {
    public static final int AMOUNT_MOVIES_RESPONSE = 20;

    @JsonField private int page;
    @JsonField private List<Movie> results;

    MovieResponse() {
        this.results = new ArrayList<>(AMOUNT_MOVIES_RESPONSE);
    }

    public int getPage() {
        return page;
    }

    public List<Movie> getResults() {
        return new ArrayList<>(results);
    }

    void setPage(int page) {
        this.page = page;
    }

    void setResults(List<Movie> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MovieResponse {" +
                "results (list size) = " + results.size() +
                ", page = " + page +
                '}';
    }
}
