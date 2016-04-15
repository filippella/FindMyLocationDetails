package com.example.filippoash.getlocationspike.model;

/**
 * Created by filippo.ash on 15, April 2016
 */
public class LocationResponse {

    private Results[] results;

    private String status;

    public Results[] getResults ()
    {
        return results;
    }

    public void setResults (Results[] results)
    {
        this.results = results;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [results = "+results+", status = "+status+"]";
    }
}
