package com.example.cdc_test.dependencyinjection;

import com.example.cdc_test.ListPathsActivity;
import com.example.cdc_test.LocationService;
import com.example.cdc_test.MapActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RoomPathModule.class, RoomCoordinatesModule.class, ApplicationModule.class})
public interface ApplicationComponent {

    void inject(ListPathsActivity listPathsActivity);
    void inject(MapActivity mapActivity);
    void inject(LocationService locationService);
}
