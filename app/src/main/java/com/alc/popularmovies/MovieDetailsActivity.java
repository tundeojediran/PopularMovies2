package com.alc.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_poster_image)  ImageView mPosterImageView;
    @BindView(R.id.tv_title) TextView mMovieTitle;
    @BindView(R.id.tv_release_date) TextView mReleaseDateTextView;
    @BindView(R.id.tv_votes) TextView mVotesTextView;
    @BindView(R.id.tv_plot_synopsis) TextView mSynopsisTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);

//        mPosterImageView = (ImageView) findViewById(R.id.iv_poster_image);
//        mMovieTitle = (TextView) findViewById(R.id.tv_title);
//        mReleaseDateTextView = (TextView) findViewById(R.id.tv_release_date);
//        mVotesTextView = (TextView) findViewById(R.id.tv_votes);
//        mSynopsisTextView = (TextView) findViewById(R.id.tv_plot_synopsis);

        Intent receivedIntent = getIntent();

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



    }
}
