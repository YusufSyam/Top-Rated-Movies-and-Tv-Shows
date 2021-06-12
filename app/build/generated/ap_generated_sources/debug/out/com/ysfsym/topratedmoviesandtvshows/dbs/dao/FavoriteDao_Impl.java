package com.ysfsym.topratedmoviesandtvshows.dbs.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ysfsym.topratedmoviesandtvshows.dbs.table.FavoriteMovie;
import com.ysfsym.topratedmoviesandtvshows.dbs.table.FavoriteTv;
import io.reactivex.rxjava3.core.Completable;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.lang.Void;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class FavoriteDao_Impl implements FavoriteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FavoriteMovie> __insertionAdapterOfFavoriteMovie;

  private final EntityInsertionAdapter<FavoriteTv> __insertionAdapterOfFavoriteTv;

  private final EntityDeletionOrUpdateAdapter<FavoriteMovie> __deletionAdapterOfFavoriteMovie;

  private final EntityDeletionOrUpdateAdapter<FavoriteTv> __deletionAdapterOfFavoriteTv;

  public FavoriteDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFavoriteMovie = new EntityInsertionAdapter<FavoriteMovie>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `favorites_movie` (`id`,`title`,`img_path`,`vote_average`,`release_date`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FavoriteMovie value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getImgPath() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getImgPath());
        }
        if (value.getVoteAverage() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindDouble(4, value.getVoteAverage());
        }
        if (value.getReleaseDate() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getReleaseDate());
        }
      }
    };
    this.__insertionAdapterOfFavoriteTv = new EntityInsertionAdapter<FavoriteTv>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `favorites_tv` (`id`,`title`,`img_path`,`vote_average`,`release_date`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FavoriteTv value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getImgPath() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getImgPath());
        }
        if (value.getVoteAverage() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindDouble(4, value.getVoteAverage());
        }
        if (value.getReleaseDate() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getReleaseDate());
        }
      }
    };
    this.__deletionAdapterOfFavoriteMovie = new EntityDeletionOrUpdateAdapter<FavoriteMovie>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `favorites_movie` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FavoriteMovie value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__deletionAdapterOfFavoriteTv = new EntityDeletionOrUpdateAdapter<FavoriteTv>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `favorites_tv` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FavoriteTv value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public Completable addFavoriteMovie(final FavoriteMovie favoriteMovies) {
    return Completable.fromCallable(new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFavoriteMovie.insert(favoriteMovies);
          __db.setTransactionSuccessful();
          return null;
        } finally {
          __db.endTransaction();
        }
      }
    });
  }

  @Override
  public Completable addFavoriteTvShow(final FavoriteTv favoritetv) {
    return Completable.fromCallable(new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFavoriteTv.insert(favoritetv);
          __db.setTransactionSuccessful();
          return null;
        } finally {
          __db.endTransaction();
        }
      }
    });
  }

  @Override
  public Completable deleteFavoriteMovie(final FavoriteMovie favoriteMovie) {
    return Completable.fromCallable(new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfFavoriteMovie.handle(favoriteMovie);
          __db.setTransactionSuccessful();
          return null;
        } finally {
          __db.endTransaction();
        }
      }
    });
  }

  @Override
  public Completable deleteFavoriteTv(final FavoriteTv favoriteTv) {
    return Completable.fromCallable(new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfFavoriteTv.handle(favoriteTv);
          __db.setTransactionSuccessful();
          return null;
        } finally {
          __db.endTransaction();
        }
      }
    });
  }

  @Override
  public List<FavoriteMovie> getAllMovie() {
    final String _sql = "SELECT * FROM favorites_movie";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfImgPath = CursorUtil.getColumnIndexOrThrow(_cursor, "img_path");
      final int _cursorIndexOfVoteAverage = CursorUtil.getColumnIndexOrThrow(_cursor, "vote_average");
      final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "release_date");
      final List<FavoriteMovie> _result = new ArrayList<FavoriteMovie>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final FavoriteMovie _item;
        _item = new FavoriteMovie();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        _item.setTitle(_tmpTitle);
        final String _tmpImgPath;
        if (_cursor.isNull(_cursorIndexOfImgPath)) {
          _tmpImgPath = null;
        } else {
          _tmpImgPath = _cursor.getString(_cursorIndexOfImgPath);
        }
        _item.setImgPath(_tmpImgPath);
        final Double _tmpVoteAverage;
        if (_cursor.isNull(_cursorIndexOfVoteAverage)) {
          _tmpVoteAverage = null;
        } else {
          _tmpVoteAverage = _cursor.getDouble(_cursorIndexOfVoteAverage);
        }
        _item.setVoteAverage(_tmpVoteAverage);
        final String _tmpReleaseDate;
        if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
          _tmpReleaseDate = null;
        } else {
          _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
        }
        _item.setReleaseDate(_tmpReleaseDate);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<FavoriteTv> getAllTvSow() {
    final String _sql = "SELECT * FROM favorites_tv";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfImgPath = CursorUtil.getColumnIndexOrThrow(_cursor, "img_path");
      final int _cursorIndexOfVoteAverage = CursorUtil.getColumnIndexOrThrow(_cursor, "vote_average");
      final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "release_date");
      final List<FavoriteTv> _result = new ArrayList<FavoriteTv>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final FavoriteTv _item;
        _item = new FavoriteTv();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        _item.setTitle(_tmpTitle);
        final String _tmpImgPath;
        if (_cursor.isNull(_cursorIndexOfImgPath)) {
          _tmpImgPath = null;
        } else {
          _tmpImgPath = _cursor.getString(_cursorIndexOfImgPath);
        }
        _item.setImgPath(_tmpImgPath);
        final Double _tmpVoteAverage;
        if (_cursor.isNull(_cursorIndexOfVoteAverage)) {
          _tmpVoteAverage = null;
        } else {
          _tmpVoteAverage = _cursor.getDouble(_cursorIndexOfVoteAverage);
        }
        _item.setVoteAverage(_tmpVoteAverage);
        final String _tmpReleaseDate;
        if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
          _tmpReleaseDate = null;
        } else {
          _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
        }
        _item.setReleaseDate(_tmpReleaseDate);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean isMovieExists(final int id) {
    final String _sql = "SELECT EXISTS (SELECT * FROM favorites_movie WHERE id = ? )";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final boolean _result;
      if(_cursor.moveToFirst()) {
        final int _tmp;
        _tmp = _cursor.getInt(0);
        _result = _tmp != 0;
      } else {
        _result = false;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean isTvExists(final int id) {
    final String _sql = "SELECT EXISTS (SELECT * FROM favorites_tv WHERE id = ? )";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final boolean _result;
      if(_cursor.moveToFirst()) {
        final int _tmp;
        _tmp = _cursor.getInt(0);
        _result = _tmp != 0;
      } else {
        _result = false;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public FavoriteMovie findByMovieId(final int id) {
    final String _sql = "SELECT * FROM favorites_movie WHERE id=? LIMIT 1 ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfImgPath = CursorUtil.getColumnIndexOrThrow(_cursor, "img_path");
      final int _cursorIndexOfVoteAverage = CursorUtil.getColumnIndexOrThrow(_cursor, "vote_average");
      final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "release_date");
      final FavoriteMovie _result;
      if(_cursor.moveToFirst()) {
        _result = new FavoriteMovie();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        _result.setTitle(_tmpTitle);
        final String _tmpImgPath;
        if (_cursor.isNull(_cursorIndexOfImgPath)) {
          _tmpImgPath = null;
        } else {
          _tmpImgPath = _cursor.getString(_cursorIndexOfImgPath);
        }
        _result.setImgPath(_tmpImgPath);
        final Double _tmpVoteAverage;
        if (_cursor.isNull(_cursorIndexOfVoteAverage)) {
          _tmpVoteAverage = null;
        } else {
          _tmpVoteAverage = _cursor.getDouble(_cursorIndexOfVoteAverage);
        }
        _result.setVoteAverage(_tmpVoteAverage);
        final String _tmpReleaseDate;
        if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
          _tmpReleaseDate = null;
        } else {
          _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
        }
        _result.setReleaseDate(_tmpReleaseDate);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public FavoriteTv findByTvId(final int id) {
    final String _sql = "SELECT * FROM favorites_tv WHERE id=? LIMIT 1 ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfImgPath = CursorUtil.getColumnIndexOrThrow(_cursor, "img_path");
      final int _cursorIndexOfVoteAverage = CursorUtil.getColumnIndexOrThrow(_cursor, "vote_average");
      final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "release_date");
      final FavoriteTv _result;
      if(_cursor.moveToFirst()) {
        _result = new FavoriteTv();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        _result.setTitle(_tmpTitle);
        final String _tmpImgPath;
        if (_cursor.isNull(_cursorIndexOfImgPath)) {
          _tmpImgPath = null;
        } else {
          _tmpImgPath = _cursor.getString(_cursorIndexOfImgPath);
        }
        _result.setImgPath(_tmpImgPath);
        final Double _tmpVoteAverage;
        if (_cursor.isNull(_cursorIndexOfVoteAverage)) {
          _tmpVoteAverage = null;
        } else {
          _tmpVoteAverage = _cursor.getDouble(_cursorIndexOfVoteAverage);
        }
        _result.setVoteAverage(_tmpVoteAverage);
        final String _tmpReleaseDate;
        if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
          _tmpReleaseDate = null;
        } else {
          _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
        }
        _result.setReleaseDate(_tmpReleaseDate);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
