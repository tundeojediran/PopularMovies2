package com.alc.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alc.popularmovies.R;
import com.alc.popularmovies.data.PopularMoviesContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dannytee on 15/05/2017.
 */

public class FavouritesCursorAdapter extends RecyclerView.Adapter<FavouritesCursorAdapter.FavouritesViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public FavouritesCursorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public FavouritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.favourite_item, parent, false);

        return new FavouritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavouritesViewHolder holder, int position) {

        int idIndex = mCursor.getColumnIndex(PopularMoviesContract.MovieEntry._ID);
        int movieIdIndex = mCursor.getColumnIndex(PopularMoviesContract.MovieEntry.COLUMN_MOVIE_ID);
        int movieTitleIndex = mCursor.getColumnIndex(PopularMoviesContract.MovieEntry.COLUMN_MOVIE_TITLE);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        final int id = mCursor.getInt(idIndex);
        String movieId = mCursor.getString(movieIdIndex);
        String movieTitle = mCursor.getString(movieTitleIndex);

        holder.itemView.setTag(movieId);
        holder.movieTitleTextView.setText(movieTitle);

    }

    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    class FavouritesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textview_movie_title) TextView movieTitleTextView;

        public FavouritesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }



}
