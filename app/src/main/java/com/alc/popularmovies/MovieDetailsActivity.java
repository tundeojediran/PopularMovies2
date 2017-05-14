package com.alc.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alc.popularmovies.adapters.MovieAdapter;
import com.alc.popularmovies.adapters.ReviewAdapter;
import com.alc.popularmovies.interfaces.PopularMoviesAPI;
import com.alc.popularmovies.models.MovieDBResponse;
import com.alc.popularmovies.models.MovieItem;
import com.alc.popularmovies.models.ReviewItem;
import com.alc.popularmovies.models.ReviewListResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_poster_image)  ImageView mPosterImageView;
    @BindView(R.id.tv_title) TextView mMovieTitle;
    @BindView(R.id.tv_release_date) TextView mReleaseDateTextView;
    @BindView(R.id.tv_votes) TextView mVotesTextView;
    @BindView(R.id.tv_plot_synopsis) TextView mSynopsisTextView;
    @BindView(R.id.recyclerview_reviews) RecyclerView mRecyclerViewReviews;
    @BindView(R.id.recyclerview_trailers) RecyclerView getmRecyclerViewTrailers;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;


    private ReviewAdapter mReviewAdapter;
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

        mReviewAdapter = new ReviewAdapter();

        getReviews(movieId);

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
            public void onFailure(Call<ReviewListResponse> call, Throwable t) {
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
}
