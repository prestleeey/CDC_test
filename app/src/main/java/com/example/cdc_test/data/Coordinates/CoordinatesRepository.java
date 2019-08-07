package com.example.cdc_test.data.Coordinates;

import android.arch.lifecycle.LiveData;
import android.location.Location;

import java.util.List;

import javax.inject.Inject;

public class CoordinatesRepository {
    private CoordinatesDAO coordinatesDAO;

    @Inject
    public CoordinatesRepository(CoordinatesDAO coordinatesDAO) {
        this.coordinatesDAO = coordinatesDAO;
    }

    public LiveData<List<Coordinates>> getCoordinatesByPathId(Long idPath) {
        return coordinatesDAO.getCoordinatesByPathId(idPath);
    }

    public void insertCoordinates(Long idPath, Location location) {

        Coordinates coordinates = new Coordinates(
                idPath,
                location.getLatitude(),
                location.getLongitude()
        );

        coordinatesDAO.insertCoordinates(coordinates);
    }
}
