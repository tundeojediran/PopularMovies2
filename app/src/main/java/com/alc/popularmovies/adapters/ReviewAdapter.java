package com.alc.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alc.popularmovies.R;
import com.alc.popularmovies.models.ReviewItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dannytee on 14/05/2017.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder>{

    private List<ReviewItem> reviewItems;

    public ReviewAdapter() {
        this.reviewItems = new ArrayList<>();
    }

    public ReviewAdapter(List<ReviewItem> reviewItems) {
        this.reviewItems = reviewItems;
    }

    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder reviewAdapterViewHolder, int position) {
        ReviewItem reviewItem = reviewItems.get(position);

        reviewAdapterViewHolder.mReviewTextView.setText(reviewItem.getContent());
        reviewAdapterViewHolder.mAuthorTextView.setText(reviewItem.getAuthor());


    }

    @Override
    public int getItemCount() {
        if (null == reviewItems) {
            return 0;
        }
        return reviewItems.size();
    }

    public void setReviewData(List<ReviewItem> reviewData) {
        reviewItems = reviewData;
        notifyDataSetChanged();
    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_review) TextView mReviewTextView;
        @BindView(R.id.textview_author) TextView mAuthorTextView;

        public ReviewAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
