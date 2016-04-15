package org.dalol.getlocationspike.di.component;

import org.dalol.getlocationspike.di.module.LocationProviderModule;
import org.dalol.getlocationspike.di.scope.CustomScope;
import org.dalol.getlocationspike.ui.MainActivity;

import dagger.Component;

/**
 * Created by filippo.ash on 15, April 2016
 */
@CustomScope
@Component(modules = LocationProviderModule.class, dependencies = ApiComponent.class)
public interface LocationProviderComponent {

    void inject(MainActivity activity);
}
