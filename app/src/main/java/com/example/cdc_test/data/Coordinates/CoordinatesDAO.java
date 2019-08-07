package com.example.cdc_test.data.Coordinates;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CoordinatesDAO {

    @Query("SELECT * FROM Coordinates WHERE idPath = :idPath")
    LiveData<List<Coordinates>> getCoordinatesByPathId(Long idPath);

    @Insert
    void insertCoordinates(Coordinates coordinates);
}
