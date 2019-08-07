package com.example.cdc_test.data.Path;

import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class PathRepository {
    private final PathDAO pathDAO;

    @Inject
    public PathRepository(PathDAO pathDAO) {
        this.pathDAO = pathDAO;
    }

    public LiveData<List<Path>> getListOfPaths() {
        return pathDAO.getPaths();
    }

    public Single<Long> setPath(String name) {
        Path path = new Path(name);
        return Single.fromCallable(() -> pathDAO.insertPath(path));
    }
}
