package com.example.cdc_test.data.Path;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PathDAO {

    @Query("SELECT * FROM Path")
    LiveData<List<Path>> getPaths();

    @Query("SELECT * FROM Path WHERE id = :id")
    LiveData<Path> getPathById(Long id);

    @Insert
    Long insertPath(Path path);
}
