package com.ysfsym.topratedmoviesandtvshows.dbs.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ysfsym.topratedmoviesandtvshows.dbs.table.FavoriteMovie;
import com.ysfsym.topratedmoviesandtvshows.dbs.table.FavoriteTv;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;


@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorites_movie")
    List<FavoriteMovie> getAllMovie();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable addFavoriteMovie(FavoriteMovie favoriteMovies);

    @Delete
    Completable deleteFavoriteMovie(FavoriteMovie favoriteMovie);

    @Query("SELECT * FROM favorites_tv")
    List<FavoriteTv> getAllTvSow();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable addFavoriteTvShow(FavoriteTv favoritetv);

    @Delete()
    Completable deleteFavoriteTv(FavoriteTv favoriteTv);

    @Query("SELECT EXISTS (SELECT * FROM favorites_movie WHERE id = :id )")
    boolean isMovieExists(int id);

    @Query("SELECT EXISTS (SELECT * FROM favorites_tv WHERE id = :id )")
    boolean isTvExists(int id);

    @Query("SELECT * FROM favorites_movie WHERE id=:id LIMIT 1 ")
    FavoriteMovie findByMovieId(int id);

    @Query("SELECT * FROM favorites_tv WHERE id=:id LIMIT 1 ")
    FavoriteTv findByTvId(int id);

}
