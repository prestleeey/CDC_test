package com.example.cdc_test;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.example.cdc_test.data.Coordinates.CoordinatesRepository;

import javax.inject.Inject;

public class LocationService extends Service {

    @Inject
    CoordinatesRepository repository;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private Long idPath;

    @Override
    public void onCreate() {
        super.onCreate();

        ((RoomDemoApplication) getApplication())
                .getApplicationComponent()
                .inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        idPath = intent.getLongExtra(MapActivity.ID_PATH,0);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new PathLocationListener();
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                50,
                locationListener
        );

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }

    private class PathLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            new Thread(() -> repository.insertCoordinates(idPath, location)).start();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            stopSelf();
        }
    }
}
