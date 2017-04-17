package com.alc.popularmovies.models;

import java.util.List;

/**
 * Created by dannytee on 17/04/2017.
 */

public class MovieDBResponse {

    private List<MovieItem> results;

    private String page;

    private String total_pages;

    private String total_results;

    public MovieDBResponse(List<MovieItem> results, String page, String total_pages, String total_results) {
        this.results = results;
        this.page = page;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public List<MovieItem> getResults ()
    {
        return results;
    }

    public void setResults (List<MovieItem> results)
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

}
