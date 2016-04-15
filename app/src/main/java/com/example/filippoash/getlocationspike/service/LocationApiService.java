package com.example.filippoash.getlocationspike.service;

import com.example.filippoash.getlocationspike.model.LocationResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by filippo.ash on 15, April 2016
 */
public interface LocationApiService {

//    @GET("/maps/api/geocode/json?latlng=53.1889069,-3.0517969&sensor=false")
    @GET("/maps/api/geocode/json")
    Call<LocationResponse> getAddressesResponse(@Query("latlng") String latlng, @Query("sensor") boolean sensor);
}
