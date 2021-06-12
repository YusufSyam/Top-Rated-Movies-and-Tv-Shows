package com.ysfsym.topratedmoviesandtvshows.helper;
import com.ysfsym.topratedmoviesandtvshows.dbs.table.FavoriteMovie;
import com.ysfsym.topratedmoviesandtvshows.dbs.table.FavoriteTv;

public interface OnItemClickListener<T> {
    void onClick(T t);
}
