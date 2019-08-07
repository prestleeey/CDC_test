package com.example.cdc_test.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.cdc_test.data.Coordinates.Coordinates;
import com.example.cdc_test.data.Coordinates.CoordinatesDAO;
import com.example.cdc_test.data.Path.Path;
import com.example.cdc_test.data.Path.PathDAO;

@Database(entities = {Path.class, Coordinates.class}, version = 1)
public abstract class PathDatabase extends RoomDatabase {
    public abstract PathDAO pathDAO();
    public abstract CoordinatesDAO coordinatesDAO();
}
