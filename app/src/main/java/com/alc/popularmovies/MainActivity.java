package com.alc.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alc.popularmovies.adapters.MovieAdapter;
import com.alc.popularmovies.interfaces.PopularMoviesAPI;
import com.alc.popularmovies.models.MovieDBResponse;
import com.alc.popularmovies.models.MovieItem;
import com.alc.popularmovies.services.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private PopularMoviesAPI popularMoviesAPIService;

    public static final String MOVIE_TITLE = "movie_title";
    public static final String MOVIE_IMAGE = "movie_image";
    public static final String MOVIE_RELEASE_DATE = "movie_release_date";
    public static final String MOVIE_VOTES = "movie_votes";
    public static final String MOVIE_SYNOPSIS = "movie_synopsis";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);


        loadPopularMovies();

    }

    private void loadPopularMovies() {
        showMovieDataView();
        mLoadingIndicator.setVisibility(View.VISIBLE);

        // check for network connectivity
        if (!isConnected(this)) {

            showErrorMessage();
            return;

        }

        popularMoviesAPIService = ServiceGenerator.createService(PopularMoviesAPI.class);

        getPopularMovies();
    }

    private void loadHighestRatedMovies() {
        showMovieDataView();
        mLoadingIndicator.setVisibility(View.VISIBLE);

        // check for network connectivity
        if (!isConnected(this)) {

            showErrorMessage();
            return;

        }

        popularMoviesAPIService = ServiceGenerator.createService(PopularMoviesAPI.class);

        getHighestRatedMovies();
    }


    // get popular movies
    private void getPopularMovies() {

        Call<MovieDBResponse> movieDBResponseCall = popularMoviesAPIService.getPopularMovies(PopularMoviesAPI.API_KEY);
        movieDBResponseCall.enqueue(new Callback<MovieDBResponse>() {
            @Override
            public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {


                if (response.isSuccessful()) {

                  showMovieDataView();
                    mLoadingIndicator.setVisibility(View.INVISIBLE);

                    try {
                        MovieDBResponse movieDBResponse = response.body();
                        Log.d("totalpages", movieDBResponse.getTotal_pages());

                        List<MovieItem> movieItems = movieDBResponse.getResults();


                        mMovieAdapter.setMovieData(movieItems);


                    } catch (Exception e) {
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                    }

                } else {
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                    showErrorMessage();

                }

            }

            @Override
            public void onFailure(Call<MovieDBResponse> call, Throwable t) {
                Log.d("onFailure", t.toString());
                mLoadingIndicator.setVisibility(View.INVISIBLE);
                showErrorMessage();


            }
        });

    }

    // get highest rated movies
    private void getHighestRatedMovies() {
        Call<MovieDBResponse> movieDBResponseCall = popularMoviesAPIService.getHighestRatedMovies(PopularMoviesAPI.API_KEY);
        movieDBResponseCall.enqueue(new Callback<MovieDBResponse>() {
            @Override
            public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {

                if (response.isSuccessful()) {
                    showMovieDataView();
                    mLoadingIndicator.setVisibility(View.INVISIBLE);



                    try {
                        MovieDBResponse movieDBResponse = response.body();
                        List<MovieItem> movieItems = movieDBResponse.getResults();

                        Log.d("response", movieDBResponse.getTotal_pages());
                        Log.d("bull", movieItems.size() + "");

                        mMovieAdapter.setMovieData(movieItems);


                    } catch (Exception e) {
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                    }

                } else {
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                    showErrorMessage();

                }

            }

            @Override
            public void onFailure(Call<MovieDBResponse> call, Throwable t) {
                Log.d("onFailure", t.toString());
                mLoadingIndicator.setVisibility(View.INVISIBLE);
                showErrorMessage();


            }
        });
    }


    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected()
                && networkInfo.isAvailable();

    }

    private void showMovieDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {

        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(MovieItem movieItem) {

        Context context = this;
        Class movieDetailsClass = MovieDetailsActivity.class;
        Intent movieDetailsIntent = new Intent(context, movieDetailsClass);

        movieDetailsIntent.putExtra(MOVIE_TITLE, movieItem.getTitle());
        movieDetailsIntent.putExtra(MOVIE_IMAGE, movieItem.getPoster_path());
        movieDetailsIntent.putExtra(MOVIE_RELEASE_DATE, movieItem.getRelease_date());
        movieDetailsIntent.putExtra(MOVIE_VOTES, movieItem.getVote_count());
        movieDetailsIntent.putExtra(MOVIE_SYNOPSIS, movieItem.getOverview());
        startActivity(movieDetailsIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.menu_sort, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            mMovieAdapter.setMovieData(null);
            loadPopularMovies();
            return true;
        }

        if (id == R.id.action_popular) {
            loadPopularMovies();
            return true;
        }

        if (id == R.id.action_rated) {
            loadHighestRatedMovies();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
