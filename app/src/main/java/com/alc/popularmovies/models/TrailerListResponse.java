package com.alc.popularmovies.models;

import android.os.Parcelable;

import java.util.List;

/**
 * Created by dannytee on 14/05/2017.
 */

public class TrailerListResponse {

    private String id;

    private List<TrailerItem> results;

    public TrailerListResponse(String id, List<TrailerItem> results) {
        this.id = id;
        this.results = results;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }


    public List<TrailerItem> getResults() {
        return results;
    }

    public void setResults(List<TrailerItem> results) {
        this.results = results;
    }
}
