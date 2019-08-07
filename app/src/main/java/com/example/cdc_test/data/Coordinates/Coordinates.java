package com.example.cdc_test.data.Coordinates;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Coordinates {

    @PrimaryKey(autoGenerate = true)
    Long id;
    Long idPath;
    double latitude;
    double longitude;

    public Coordinates(Long idPath, double latitude, double longitude) {
        this.idPath = idPath;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPath() {
        return idPath;
    }

    public void setIdPath(Long idPath) {
        this.idPath = idPath;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
