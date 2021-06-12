package com.ysfsym.topratedmoviesandtvshows.networks;

import com.ysfsym.topratedmoviesandtvshows.models.movie.MovieDetail;
import com.ysfsym.topratedmoviesandtvshows.models.movie.MovieResult;
import com.ysfsym.topratedmoviesandtvshows.models.tv.TvResult;
import com.ysfsym.topratedmoviesandtvshows.models.tv.TvShowDetail;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {
    @GET("movie/top_rated")
    Call<MovieResult> topRatedMovies(
        @Query("api_key") String apiKey,
        @Query("language") String languange,
        @Query("page") int page
    );

    @GET("movie/{movie_id}")
    Call<MovieDetail> movieDetail(@Path("movie_id") int movieId, @QueryMap Map<String, String> options);

    @GET("tv/top_rated")
    Call<TvResult> topRatedtvShows(
        @Query("api_key") String apiKey,
        @Query("language") String languange,
        @Query("page") int page
    );

    @GET("tv/{tv_id}")
    Call<TvShowDetail> tvDetail(@Path ("tv_id") int movieId, @QueryMap Map<String, String> options);

    @GET("search/movie")
    Call<MovieResult> searchMovie(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") int page
    );

    @GET("search/tv")
    Call<TvResult> searchTv(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") int page
    );
}
