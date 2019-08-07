package com.example.cdc_test.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.cdc_test.data.Coordinates.CoordinatesRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CustomCoordinatesViewModelFactory implements ViewModelProvider.Factory {
    private CoordinatesRepository repository;

    @Inject
    public CustomCoordinatesViewModelFactory(CoordinatesRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CoordinatesViewModel.class)) {
            return (T) new CoordinatesViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
