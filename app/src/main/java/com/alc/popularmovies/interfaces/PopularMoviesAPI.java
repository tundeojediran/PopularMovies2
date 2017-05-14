package com.alc.popularmovies.interfaces;

import com.alc.popularmovies.models.MovieDBResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dannytee on 17/04/2017.
 */

public interface PopularMoviesAPI {

    public static final String API_KEY = "a07ecbd17356b85d2c741e0973d490e1";

    /*
     * Retrofit get annotation with URL
     * And method that will return popular movies.
    */
    @GET("popular")
    Call<MovieDBResponse> getPopularMovies(@Query("api_key") String api_key);


    /*
     * Retrofit get annotation with URL
     * And method that will return highest rated movies.
    */
    @GET("top_rated")
    Call<MovieDBResponse> getHighestRatedMovies(@Query("api_key") String api_key);


    /*
    * Retrofit get annotation with URL
    * And method that will return trailers for a movie.
   */
    @GET("movie/{movie_id}/videos")
    Call<MovieDBResponse> getMovieTrailers(@Path("movie_id") String movie_id, @Query("api_key") String api_key);


    /*
   * Retrofit get annotation with URL
   * And method that will return the reviews for a movie.
  */
    @GET("movie/{movie_id}/videos")
    Call<MovieDBResponse> getMovieReviews(@Path("movie_id") String movie_id, @Query("api_key") String api_key);


}
