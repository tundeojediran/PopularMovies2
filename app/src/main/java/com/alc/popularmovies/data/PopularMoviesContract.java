package com.alc.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dannytee on 15/05/2017.
 */

public final class PopularMoviesContract {


    private PopularMoviesContract(){}

    public static final String CONTENT_AUTHORITY = "com.alc.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES)
                .build();

        public static final String TABLE_NAME = "movies";


        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static final String COLUMN_MOVIE_TITLE = "movie_title";

        public static Uri buildMovieUriWithMovieId(String movieId) {
            return CONTENT_URI.buildUpon()
                    .appendPath(movieId)
                    .build();
        }

    }



}
