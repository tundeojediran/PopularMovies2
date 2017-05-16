package com.alc.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
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
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.alc.popularmovies.adapters.FavouritesCursorAdapter;
import com.alc.popularmovies.adapters.MovieAdapter;
import com.alc.popularmovies.data.PopularMoviesContract;
import com.alc.popularmovies.interfaces.PopularMoviesAPI;
import com.alc.popularmovies.models.MovieDBResponse;
import com.alc.popularmovies.models.MovieItem;
import com.alc.popularmovies.services.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler,  LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rv_movies) RecyclerView mRecyclerView;
    @BindView(R.id.tv_error_message_display) TextView mErrorMessageDisplay;
    @BindView(R.id.pb_loading_indicator) ProgressBar mLoadingIndicator;

    private MovieAdapter mMovieAdapter;
    private FavouritesCursorAdapter favouritesCursorAdapter;
    private PopularMoviesAPI popularMoviesAPIService;

    ArrayList<MovieItem> mResponseMovieItems;
    MovieDBResponse mMovieDBResponse;

    public static final String MOVIE_ID = "movie_id";
    public static final String MOVIE_TITLE = "movie_title";
    public static final String MOVIE_IMAGE = "movie_image";
    public static final String MOVIE_RELEASE_DATE = "movie_release_date";
    public static final String MOVIE_VOTES = "movie_votes";
    public static final String MOVIE_SYNOPSIS = "movie_synopsis";

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String MOVIES_LIST = "movies_list";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(gridLayoutManager);
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
                        mMovieDBResponse = response.body();
                        Log.d("response body", mMovieDBResponse.toString());
                        Log.d("totalpages", mMovieDBResponse.getTotal_pages());

                        mResponseMovieItems = mMovieDBResponse.getResults();

                        mMovieAdapter.setMovieData(mResponseMovieItems);


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
                        mMovieDBResponse = response.body();
                        mResponseMovieItems = mMovieDBResponse.getResults();

                        Log.d("response", mMovieDBResponse.getTotal_pages());
                        Log.d("bull", mResponseMovieItems.size() + "");

                        mMovieAdapter.setMovieData(mResponseMovieItems);


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

        movieDetailsIntent.putExtra(MOVIE_ID, movieItem.getId());
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

//        MenuItem item = menu.findItem(R.id.spinner_sort);
//        Spinner sortSpinner = (Spinner) MenuItemCompat.getActionView(item);
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.spinner_list_item_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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
            mMovieAdapter.setMovieData(null);
            loadPopularMovies();
            return true;
        }

        if (id == R.id.action_rated) {
            mMovieAdapter.setMovieData(null);
            loadHighestRatedMovies();
            return true;
        }

        if (id == R.id.action_favourites) {
            //TODO load favourites
            loadFavouriteMovies();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadFavouriteMovies() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        favouritesCursorAdapter = new FavouritesCursorAdapter(this);
        mRecyclerView.setAdapter(favouritesCursorAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize a Cursor, this will hold all the movie data
            Cursor mMovieData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mMovieData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mMovieData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                // Will implement to load data

                // Query and load all task data in the background; sort by priority
                // [Hint] use a try/catch block to catch any errors in loading data

                try {
                    return getContentResolver().query(PopularMoviesContract.MovieEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mMovieData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        favouritesCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favouritesCursorAdapter.swapCursor(null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mMovieDBResponse != null){
            outState.putParcelableArrayList(MOVIES_LIST, mMovieDBResponse.getResults());
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null){
            mResponseMovieItems =  savedInstanceState.getParcelableArrayList(MOVIES_LIST);
            mMovieAdapter.setMovieData(mResponseMovieItems);
        }


    }
}
