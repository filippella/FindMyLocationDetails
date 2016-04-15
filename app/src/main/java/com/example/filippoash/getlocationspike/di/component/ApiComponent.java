package com.example.filippoash.getlocationspike.di.component;

import com.example.filippoash.getlocationspike.di.module.ApiModule;
import com.example.filippoash.getlocationspike.di.module.NetworkModule;
import com.example.filippoash.getlocationspike.di.scope.CustomScope;
import com.example.filippoash.getlocationspike.service.LocationApiService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by filippo.ash on 15, April 2016
 */
@Singleton
@Component(modules = {ApiModule.class, NetworkModule.class})
public interface ApiComponent {

    LocationApiService service();
}
