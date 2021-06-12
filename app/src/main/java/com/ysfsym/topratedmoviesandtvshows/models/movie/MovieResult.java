package com.ysfsym.topratedmoviesandtvshows.models.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResult {
    @SerializedName("results")
    @Expose
    private List<Movie> movieList;

    public List<Movie> getMovieList() {
        return movieList;
    }
}
