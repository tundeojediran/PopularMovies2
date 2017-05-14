package com.alc.popularmovies.models;

import java.util.List;

/**
 * Created by dannytee on 14/05/2017.
 */

public class ReviewListResponse {

    private String id;

    private List<ReviewItem> results;

    private String page;

    private String total_pages;

    private String total_results;

    public ReviewListResponse(String id, List<ReviewItem> results, String page, String total_pages, String total_results) {
        this.id = id;
        this.results = results;
        this.page = page;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public List<ReviewItem> getResults() {
        return results;
    }

    public void setResults(List<ReviewItem> results) {
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
}
