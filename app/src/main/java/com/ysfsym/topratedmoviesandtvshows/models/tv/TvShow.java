package com.ysfsym.topratedmoviesandtvshows.models.tv;

import com.google.gson.annotations.SerializedName;

public class TvShow {
    @SerializedName("poster_path")
    private String posterImage;

    @SerializedName("name")
    private String name;

    @SerializedName("vote_average")
    private Double voteAverage;

    @SerializedName("first_air_date")
    private String firstAirDate;

    int id;

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }
}
