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

import butterknife.BindView;
import butterknife.ButterKnife;

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
        Picasso.with(context)
                .load(posterPath)
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .into(movieAdapterViewHolder.mMoviePosterImageView);

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

        @BindView(R.id.iv_item_poster) ImageView mMoviePosterImageView;
        @BindView(R.id.tv_item_title) TextView mMovieTitleTextView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

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
