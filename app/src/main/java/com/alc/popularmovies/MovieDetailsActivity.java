package com.alc.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alc.popularmovies.adapters.MovieAdapter;
import com.alc.popularmovies.adapters.ReviewAdapter;
import com.alc.popularmovies.adapters.TrailerAdapter;
import com.alc.popularmovies.interfaces.PopularMoviesAPI;
import com.alc.popularmovies.models.MovieDBResponse;
import com.alc.popularmovies.models.MovieItem;
import com.alc.popularmovies.models.ReviewItem;
import com.alc.popularmovies.models.ReviewListResponse;
import com.alc.popularmovies.models.TrailerItem;
import com.alc.popularmovies.models.TrailerListResponse;
import com.alc.popularmovies.services.ServiceGenerator;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler {

    @BindView(R.id.iv_poster_image)  ImageView mPosterImageView;
    @BindView(R.id.tv_title) TextView mMovieTitle;
    @BindView(R.id.tv_release_date) TextView mReleaseDateTextView;
    @BindView(R.id.tv_votes) TextView mVotesTextView;
    @BindView(R.id.tv_plot_synopsis) TextView mSynopsisTextView;
    @BindView(R.id.recyclerview_reviews) RecyclerView mRecyclerViewReviews;
    @BindView(R.id.recyclerview_trailers) RecyclerView mRecyclerViewTrailers;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;


    private ReviewAdapter mReviewAdapter;
    private TrailerAdapter mTrailerAdapter;
    private PopularMoviesAPI popularMoviesAPIService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);


        Intent receivedIntent = getIntent();

        String movieId = receivedIntent.getStringExtra(MainActivity.MOVIE_ID);
        String posterImage = receivedIntent.getStringExtra(MainActivity.MOVIE_IMAGE);
        String title = receivedIntent.getStringExtra(MainActivity.MOVIE_TITLE);
        String date = receivedIntent.getStringExtra(MainActivity.MOVIE_RELEASE_DATE);
        String votes = receivedIntent.getStringExtra(MainActivity.MOVIE_VOTES);
        String synopsis = receivedIntent.getStringExtra(MainActivity.MOVIE_SYNOPSIS);

        String posterPath = "http://image.tmdb.org/t/p/w500/" + posterImage;
        Picasso.with(this).load(posterPath).into(mPosterImageView);

        mMovieTitle.setText(title);
        mReleaseDateTextView.setText(date);
        mVotesTextView.setText(votes);
        mSynopsisTextView.setText(synopsis);

        // layout for reviews
        LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewReviews.setLayoutManager(reviewLayoutManager);
        mRecyclerViewReviews.setHasFixedSize(true);

        mReviewAdapter = new ReviewAdapter();
        mRecyclerViewReviews.setAdapter(mReviewAdapter);

        // layout for movie trailers
        LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewTrailers.setLayoutManager(trailerLayoutManager);
        mRecyclerViewTrailers.setHasFixedSize(true);

        mTrailerAdapter = new TrailerAdapter(this);
        mRecyclerViewTrailers.setAdapter(mTrailerAdapter);


        loadReviews(movieId);
        loadMovieTrailers(movieId);

    }


    private void loadReviews(String id) {
//        showMovieDataView();
//        mLoadingIndicator.setVisibility(View.VISIBLE);

        // check for network connectivity
        if (!isConnected(this)) {

//            showErrorMessage();
            return;

        }

        popularMoviesAPIService = ServiceGenerator.createService(PopularMoviesAPI.class);

        getReviews(id);
    }

    private void loadMovieTrailers(String id) {
//        showMovieDataView();
//        mLoadingIndicator.setVisibility(View.VISIBLE);

        // check for network connectivity
        if (!isConnected(this)) {

//            showErrorMessage();
            return;

        }

        popularMoviesAPIService = ServiceGenerator.createService(PopularMoviesAPI.class);

        getMovieTrailers(id);
    }


    // get reviews
    private void getReviews(String movieId) {
        Call<ReviewListResponse> reviewListResponseCall = popularMoviesAPIService.getMovieReviews(movieId, PopularMoviesAPI.API_KEY);
        reviewListResponseCall.enqueue(new Callback<ReviewListResponse>() {
            @Override
            public void onResponse(Call<ReviewListResponse> call, Response<ReviewListResponse> response) {

                if (response.isSuccessful()) {
//                    showReviewDataView();
//                    mLoadingIndicator.setVisibility(View.INVISIBLE);



                    try {
                        ReviewListResponse reviewListResponse = response.body();
                        List<ReviewItem> reviewItems = reviewListResponse.getResults();

                        Log.d("response", reviewListResponse.getTotal_pages());
                        Log.d("reviewSize", reviewItems.size() + "");

                        mReviewAdapter.setReviewData(reviewItems);


                    } catch (Exception e) {
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                    }

                } else {
//                    mLoadingIndicator.setVisibility(View.INVISIBLE);
//                    showErrorMessage();

                }

            }

            @Override
            public void onFailure(Call<ReviewListResponse> call, Throwable t) {
                Log.d("onFailure", t.toString());
//                mLoadingIndicator.setVisibility(View.INVISIBLE);
//                showErrorMessage();


            }
        });
    }

    // get movie trailers
    private void getMovieTrailers(String movieId) {
        Call<TrailerListResponse> trailerListResponseCall = popularMoviesAPIService.getMovieTrailers(movieId, PopularMoviesAPI.API_KEY);
        trailerListResponseCall.enqueue(new Callback<TrailerListResponse>() {
            @Override
            public void onResponse(Call<TrailerListResponse> call, Response<TrailerListResponse> response) {

                if (response.isSuccessful()) {
//                    showReviewDataView();
//                    mLoadingIndicator.setVisibility(View.INVISIBLE);



                    try {
                        TrailerListResponse trailerListResponse = response.body();
                        List<TrailerItem> trailerItems = trailerListResponse.getResults();

                        Log.d("reviewSize", trailerItems.size() + "");

                        mTrailerAdapter.setTrailerData(trailerItems);


                    } catch (Exception e) {
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                    }

                } else {
//                    mLoadingIndicator.setVisibility(View.INVISIBLE);
//                    showErrorMessage();

                }

            }

            @Override
            public void onFailure(Call<TrailerListResponse> call, Throwable t) {
                Log.d("onFailure", t.toString());
//                mLoadingIndicator.setVisibility(View.INVISIBLE);
//                showErrorMessage();


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

    @Override
    public void onClick(TrailerItem trailerItem) {

        String videoKey = trailerItem.getKey();

//        String trailerUrl  = "https://www.youtube.com/watch?v=" + trailerItem.getKey();
        openYoutubeVideo(videoKey);

    }


    // launch youtube app or browsert to play video
    public void openYoutubeVideo(String key) {
        Intent videoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + key));
        try {
            startActivity(videoIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(browserIntent);
        }
    }
}
