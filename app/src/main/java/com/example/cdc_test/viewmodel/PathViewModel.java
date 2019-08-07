package com.example.cdc_test.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.cdc_test.data.Path.Path;
import com.example.cdc_test.data.Path.PathRepository;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PathViewModel extends ViewModel {
    final private PathRepository repository;

    final private MutableLiveData<Long> pathId = new MutableLiveData<>();

    final private CompositeDisposable disposables = new CompositeDisposable();

    public PathViewModel(PathRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Path>> getPaths() {
        return repository.getListOfPaths();
    }

    public MutableLiveData<Long> getNewPathId() {
        return pathId;
    }

    public void insertPath(String name) {
        disposables.add(repository.setPath(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pathId::setValue)
        );
    }


}
