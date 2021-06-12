package com.ysfsym.topratedmoviesandtvshows.dbs;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.ysfsym.topratedmoviesandtvshows.dbs.dao.FavoriteDao;
import com.ysfsym.topratedmoviesandtvshows.dbs.dao.FavoriteDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile FavoriteDao _favoriteDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `favorites_movie` (`id` INTEGER NOT NULL, `title` TEXT, `img_path` TEXT, `vote_average` REAL, `release_date` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `favorites_tv` (`id` INTEGER NOT NULL, `title` TEXT, `img_path` TEXT, `vote_average` REAL, `release_date` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'ea3f24147477730a10851bb248b90747')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `favorites_movie`");
        _db.execSQL("DROP TABLE IF EXISTS `favorites_tv`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsFavoritesMovie = new HashMap<String, TableInfo.Column>(5);
        _columnsFavoritesMovie.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoritesMovie.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoritesMovie.put("img_path", new TableInfo.Column("img_path", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoritesMovie.put("vote_average", new TableInfo.Column("vote_average", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoritesMovie.put("release_date", new TableInfo.Column("release_date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFavoritesMovie = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFavoritesMovie = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFavoritesMovie = new TableInfo("favorites_movie", _columnsFavoritesMovie, _foreignKeysFavoritesMovie, _indicesFavoritesMovie);
        final TableInfo _existingFavoritesMovie = TableInfo.read(_db, "favorites_movie");
        if (! _infoFavoritesMovie.equals(_existingFavoritesMovie)) {
          return new RoomOpenHelper.ValidationResult(false, "favorites_movie(com.ysfsym.topratedmoviesandtvshows.dbs.table.FavoriteMovie).\n"
                  + " Expected:\n" + _infoFavoritesMovie + "\n"
                  + " Found:\n" + _existingFavoritesMovie);
        }
        final HashMap<String, TableInfo.Column> _columnsFavoritesTv = new HashMap<String, TableInfo.Column>(5);
        _columnsFavoritesTv.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoritesTv.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoritesTv.put("img_path", new TableInfo.Column("img_path", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoritesTv.put("vote_average", new TableInfo.Column("vote_average", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoritesTv.put("release_date", new TableInfo.Column("release_date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFavoritesTv = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFavoritesTv = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFavoritesTv = new TableInfo("favorites_tv", _columnsFavoritesTv, _foreignKeysFavoritesTv, _indicesFavoritesTv);
        final TableInfo _existingFavoritesTv = TableInfo.read(_db, "favorites_tv");
        if (! _infoFavoritesTv.equals(_existingFavoritesTv)) {
          return new RoomOpenHelper.ValidationResult(false, "favorites_tv(com.ysfsym.topratedmoviesandtvshows.dbs.table.FavoriteTv).\n"
                  + " Expected:\n" + _infoFavoritesTv + "\n"
                  + " Found:\n" + _existingFavoritesTv);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "ea3f24147477730a10851bb248b90747", "73734cd8ade83ff7165880f048a9462e");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "favorites_movie","favorites_tv");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `favorites_movie`");
      _db.execSQL("DELETE FROM `favorites_tv`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(FavoriteDao.class, FavoriteDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public FavoriteDao favoriteDao() {
    if (_favoriteDao != null) {
      return _favoriteDao;
    } else {
      synchronized(this) {
        if(_favoriteDao == null) {
          _favoriteDao = new FavoriteDao_Impl(this);
        }
        return _favoriteDao;
      }
    }
  }
}
