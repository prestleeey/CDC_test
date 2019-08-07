package com.example.cdc_test.dependencyinjection;

import android.arch.lifecycle.ViewModelProvider;

import com.example.cdc_test.data.Coordinates.CoordinatesDAO;
import com.example.cdc_test.data.Coordinates.CoordinatesRepository;
import com.example.cdc_test.data.PathDatabase;
import com.example.cdc_test.viewmodel.CustomCoordinatesViewModelFactory;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = RoomPathModule.class)
public class RoomCoordinatesModule {
    //private final CoordinatesDatabase database;

    public RoomCoordinatesModule() {
//        this.database = Room.databaseBuilder(
//                application,
//                CoordinatesDatabase.class,
//                "Paths.db"
//        ).build();
    }

//    @Provides
//    @Singleton
//    CoordinatesDatabase provideCoordinatesDatabase(Application application) {
//        return database;
//    }

    @Provides
    @Singleton
    CoordinatesDAO provideCoordinatesDAO(PathDatabase database) {
        return database.coordinatesDAO();
    }

    @Provides
    @Singleton
    CoordinatesRepository provideCoordinatesRepository(CoordinatesDAO coordinatesDAO) {
        return new CoordinatesRepository(coordinatesDAO);
    }

    @Named("CoordinatesViewModelFactory")
    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(CoordinatesRepository repository) {
        return new CustomCoordinatesViewModelFactory(repository);
    }
}
