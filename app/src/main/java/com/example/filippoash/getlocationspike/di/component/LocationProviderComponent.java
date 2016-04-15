package com.example.filippoash.getlocationspike.di.component;

import com.example.filippoash.getlocationspike.di.module.LocationProviderModule;
import com.example.filippoash.getlocationspike.di.scope.CustomScope;
import com.example.filippoash.getlocationspike.ui.MainActivity;

import dagger.Component;

/**
 * Created by filippo.ash on 15, April 2016
 */
@CustomScope
@Component(modules = LocationProviderModule.class, dependencies = ApiComponent.class)
public interface LocationProviderComponent {

    void inject(MainActivity activity);
}
