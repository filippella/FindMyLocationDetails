package org.dalol.getlocationspike.di.component;

import org.dalol.getlocationspike.di.module.ApiModule;
import org.dalol.getlocationspike.di.module.NetworkModule;
import org.dalol.getlocationspike.service.LocationApiService;

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
