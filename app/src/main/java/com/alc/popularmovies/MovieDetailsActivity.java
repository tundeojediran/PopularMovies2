package com.alc.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView mPosterImageView;
    private TextView mMovieTitle;
    private TextView mReleaseDateTextView;
    private TextView mVotesTextView;
    private TextView mSynopsisTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mPosterImageView = (ImageView) findViewById(R.id.iv_poster_image);
        mMovieTitle = (TextView) findViewById(R.id.tv_title);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_release_date);
        mVotesTextView = (TextView) findViewById(R.id.tv_votes);
        mSynopsisTextView = (TextView) findViewById(R.id.tv_plot_synopsis);

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
