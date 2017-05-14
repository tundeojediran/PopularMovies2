package com.alc.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alc.popularmovies.R;
import com.alc.popularmovies.models.MovieItem;
import com.alc.popularmovies.models.TrailerItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dannytee on 14/05/2017.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder>{

    private List<TrailerItem> trailerItems;

    private final TrailerAdapterOnClickHandler mClickHandler;


    public interface TrailerAdapterOnClickHandler {
        void onClick(TrailerItem trailerItem);
    }

    public TrailerAdapter(TrailerAdapterOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
    }

    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapterViewHolder trailerAdapterViewHolder, int position) {
        TrailerItem trailerItem = trailerItems.get(position);

        trailerAdapterViewHolder.mTrailerTitleTextView.setText(trailerItem.getName());

    }

    @Override
    public int getItemCount() {
        if (null == trailerItems) {
            return 0;
        }
        return trailerItems.size();
    }

    public void setTrailerData(List<TrailerItem> trailerData) {
        trailerItems = trailerData;
        notifyDataSetChanged();
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.trailer_title) TextView mTrailerTitleTextView;

        public TrailerAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            TrailerItem selectedTrailer = trailerItems.get(adapterPosition);
            mClickHandler.onClick(selectedTrailer);
        }
    }
}
