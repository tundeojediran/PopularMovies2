package com.alc.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alc.popularmovies.R;
import com.alc.popularmovies.models.MovieItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dannytee on 17/04/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private List<MovieItem> movieItems;


    private final MovieAdapterOnClickHandler mClickHandler;


    public interface MovieAdapterOnClickHandler {
        void onClick(MovieItem movieItem);
    }


    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }



    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        MovieItem movieItem = movieItems.get(position);

        movieAdapterViewHolder.mMovieTitleTextView.setText(movieItem.getOriginal_title());

        Context context = movieAdapterViewHolder.mMoviePosterImageView.getContext();
        String posterPath = "http://image.tmdb.org/t/p/w500/" + movieItem.getPoster_path();
        Picasso.with(context).load(posterPath).into(movieAdapterViewHolder.mMoviePosterImageView);

    }

    @Override
    public int getItemCount() {
        if (null == movieItems) return 0;
        return movieItems.size();
    }


    public void setMovieData(List<MovieItem> movieData) {
        movieItems = movieData;
        notifyDataSetChanged();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mMoviePosterImageView;
        public final TextView mMovieTitleTextView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mMoviePosterImageView = (ImageView) view.findViewById(R.id.iv_item_poster);
            mMovieTitleTextView = (TextView) view.findViewById(R.id.tv_item_title);


            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieItem selectedMovie = movieItems.get(adapterPosition);
            mClickHandler.onClick(selectedMovie);
        }
    }
}
