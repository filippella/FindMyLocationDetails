package com.example.filippoash.getlocationspike.di.module;

import com.example.filippoash.getlocationspike.di.scope.CustomScope;
import com.example.filippoash.getlocationspike.service.LocationApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by filippo.ash on 15, April 2016
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    LocationApiService provideService(Retrofit retrofit) {
        return retrofit.create(LocationApiService.class);
    }
}
