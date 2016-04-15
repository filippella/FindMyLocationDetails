package org.dalol.getlocationspike.service;

import org.dalol.getlocationspike.model.LocationResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by filippo.ash on 15, April 2016
 */
public interface LocationApiService {

    @GET("/maps/api/geocode/json")
    Call<LocationResponse> getAddressesResponse(@Query("latlng") String latlng, @Query("sensor") boolean sensor);
}
