package com.example.cdc_test.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.cdc_test.data.Path.PathRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CustomPathViewModelFactory implements ViewModelProvider.Factory {
    PathRepository repository;

    @Inject
    public CustomPathViewModelFactory(PathRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PathViewModel.class)) {
            return (T) new PathViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }j
}
