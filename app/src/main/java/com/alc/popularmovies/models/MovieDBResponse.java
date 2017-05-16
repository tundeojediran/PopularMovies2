package com.alc.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dannytee on 17/04/2017.
 */

public class MovieDBResponse implements Parcelable {

    private ArrayList<MovieItem> results;

    private String page;

    private String total_pages;

    private String total_results;

    public MovieDBResponse(ArrayList<MovieItem> results, String page, String total_pages, String total_results) {
        this.results = results;
        this.page = page;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    protected MovieDBResponse(Parcel in) {
        results = in.createTypedArrayList(MovieItem.CREATOR);
        page = in.readString();
        total_pages = in.readString();
        total_results = in.readString();
    }

    public static final Creator<MovieDBResponse> CREATOR = new Creator<MovieDBResponse>() {
        @Override
        public MovieDBResponse createFromParcel(Parcel in) {
            return new MovieDBResponse(in);
        }

        @Override
        public MovieDBResponse[] newArray(int size) {
            return new MovieDBResponse[size];
        }
    };

    public ArrayList<MovieItem> getResults ()
    {
        return results;
    }

    public void setResults (ArrayList<MovieItem> results)
    {
        this.results = results;
    }

    public String getPage ()
    {
        return page;
    }

    public void setPage (String page)
    {
        this.page = page;
    }

    public String getTotal_pages ()
    {
        return total_pages;
    }

    public void setTotal_pages (String total_pages)
    {
        this.total_pages = total_pages;
    }

    public String getTotal_results ()
    {
        return total_results;
    }

    public void setTotal_results (String total_results)
    {
        this.total_results = total_results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(results);
        parcel.writeString(page);
        parcel.writeString(total_pages);
        parcel.writeString(total_results);
    }
}
