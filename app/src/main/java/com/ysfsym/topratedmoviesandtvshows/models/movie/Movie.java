package com.ysfsym.topratedmoviesandtvshows.models.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie {
    private int id;

    @SerializedName("poster_path")
    @Expose
    private String posterImage;

    @SerializedName("vote_average")
    private Double voteAverage;

    @SerializedName("release_date")
    private String releaseDate;

    private String title;

    private  String overview;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverview(String overview){ this.overview= overview; }

    public String getOverview(){ return this.overview; }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
