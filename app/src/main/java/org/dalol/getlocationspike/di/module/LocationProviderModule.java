package org.dalol.getlocationspike.di.module;

import android.content.Context;
import android.location.LocationManager;


import org.dalol.getlocationspike.di.scope.CustomScope;

import dagger.Module;
import dagger.Provides;

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
}
