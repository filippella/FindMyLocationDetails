package com.example.filippoash.getlocationspike.di.module;

import android.content.Context;
import android.location.LocationManager;

import com.example.filippoash.getlocationspike.di.scope.CustomScope;
import com.example.filippoash.getlocationspike.service.LocationApiService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by filippo.ash on 15, April 2016
 */
@Module
public class LocationProviderModule {

    private Context context;

    public LocationProviderModule(Context context) {
        this.context = context;
    }

    @Provides
    @CustomScope
    LocationManager provideLocationManager() {
        return (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
    }

//    @Provides
//    @CustomScope
//    LocationApiService provideService(Retrofit retrofit) {
//        return retrofit.create(LocationApiService.class);
//    }
}
