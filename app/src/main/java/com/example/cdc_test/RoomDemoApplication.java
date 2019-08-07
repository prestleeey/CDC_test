package com.example.cdc_test;

import android.app.Application;

import com.example.cdc_test.dependencyinjection.ApplicationComponent;
import com.example.cdc_test.dependencyinjection.ApplicationModule;
import com.example.cdc_test.dependencyinjection.DaggerApplicationComponent;
import com.example.cdc_test.dependencyinjection.RoomCoordinatesModule;
import com.example.cdc_test.dependencyinjection.RoomPathModule;

public class RoomDemoApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .roomPathModule(new RoomPathModule(this))
                .roomCoordinatesModule(new RoomCoordinatesModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }
}
