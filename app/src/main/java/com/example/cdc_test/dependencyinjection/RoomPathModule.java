package com.example.cdc_test.dependencyinjection;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;

import com.example.cdc_test.data.Path.PathDAO;
import com.example.cdc_test.data.PathDatabase;
import com.example.cdc_test.data.Path.PathRepository;
import com.example.cdc_test.viewmodel.CustomPathViewModelFactory;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomPathModule {
    private final PathDatabase database;

    public RoomPathModule(Application application) {
        this.database = Room.databaseBuilder(
            application,
            PathDatabase.class,
            "Paths.db"
        )
        .fallbackToDestructiveMigration()
        .build();
    }

    @Provides
    @Singleton
    PathDatabase providePathDatabase(Application application) {
        return database;
    }

    @Provides
    @Singleton
    PathDAO providePathDAO(PathDatabase database) {
        return database.pathDAO();
    }

    @Provides
    @Singleton
    PathRepository providePathRepository(PathDAO pathDAO) {
        return new PathRepository(pathDAO);
    }

    @Named("PathViewModelFactory")
    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(PathRepository repository) {
        return new CustomPathViewModelFactory(repository);
    }
}
