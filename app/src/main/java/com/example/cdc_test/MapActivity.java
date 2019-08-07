package com.example.cdc_test;

import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.cdc_test.data.Coordinates.Coordinates;
import com.example.cdc_test.viewmodel.CoordinatesViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String ID_PATH = "pathId";
    public static final String IS_RECORDING = "is_recording";


    @Inject
    @Named("CoordinatesViewModelFactory")
    ViewModelProvider.Factory viewModelFactory;

    CoordinatesViewModel coordinatesViewModel;

    GoogleMap map;

    @BindView(R.id.btn_path_save)
    Button btnSavePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ButterKnife.bind(this);

        ((RoomDemoApplication) getApplication())
                .getApplicationComponent()
                .inject(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Long pathId = getIntent().getLongExtra(ID_PATH, 0);

        coordinatesViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CoordinatesViewModel.class);
        coordinatesViewModel.getCoordinatesByPathId(pathId).observe(this, this::createLinePath);

        boolean record = getIntent().getBooleanExtra(IS_RECORDING, false);

        if (record) {

            btnSavePath.setVisibility(View.VISIBLE);

            Intent intentLocationService = new Intent(
                    this,
                    LocationService.class
            );
            intentLocationService.putExtra(ID_PATH, pathId);
            startService(intentLocationService);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, LocationService.class));
    }

    @OnClick(R.id.btn_path_save)
    void savePath() {
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
    }

    public void createLinePath(List<Coordinates> coordinatesList) {

        PolylineOptions polylineOptions = new PolylineOptions();

        for(Coordinates coordinates : coordinatesList) {

            LatLng latLng = new LatLng(
                    coordinates.getLatitude(),
                    coordinates.getLongitude()
            );

            polylineOptions.add(latLng);
        }

        map.clear();
        map.addPolyline(polylineOptions);
    }
}
