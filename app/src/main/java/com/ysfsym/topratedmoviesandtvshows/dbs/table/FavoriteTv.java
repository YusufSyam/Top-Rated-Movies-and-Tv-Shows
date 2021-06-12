package com.ysfsym.topratedmoviesandtvshows.dbs.table;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "favorites_tv")
public class FavoriteTv implements Serializable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

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

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "img_path")
    private String imgPath;

    @ColumnInfo(name = "vote_average")
    private Double voteAverage;

    @ColumnInfo(name = "release_date")
    private String releaseDate;

    public FavoriteTv(){}

    public FavoriteTv(int id){
        this.id = id;
    }

    public FavoriteTv(int id, String title, String imgPath, Double voteAverage, String releaseDate){
        this.id = id;
        this.title = title;
        this.imgPath = imgPath;
        this.voteAverage= voteAverage;
        this.releaseDate= releaseDate;
    }

    public FavoriteTv(int id, String title, String imgUrl){
        this.id = id;
        this.title = title;
        this.imgPath = imgUrl;
    }


}
