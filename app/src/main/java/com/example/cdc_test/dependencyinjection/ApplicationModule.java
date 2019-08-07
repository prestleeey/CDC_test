package com.example.cdc_test.dependencyinjection;


import android.app.Application;

import com.example.cdc_test.RoomDemoApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final RoomDemoApplication application;

    public ApplicationModule(RoomDemoApplication application) {
        this.application = application;
    }

    @Provides
    RoomDemoApplication provideRoomDemoApplication(){
        return application;
    }

    @Provides
    Application provideApplication(){
        return application;
    }

}
