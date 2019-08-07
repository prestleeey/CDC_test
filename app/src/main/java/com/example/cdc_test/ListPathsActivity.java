package com.example.cdc_test;

import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cdc_test.viewmodel.PathViewModel;
import com.rey.material.app.BottomSheetDialog;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListPathsActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION_FINE_LOCATION = 1337;

    private PathAdapter adapter;

    @Inject
    @Named("PathViewModelFactory")
    ViewModelProvider.Factory viewModelFactory;

    PathViewModel pathViewModel;

    @BindView(R.id.rec_list_activity)
    RecyclerView mPathRecycler;

    @BindView(R.id.tlb_list_paths_activity)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        toolbar.setTitle(R.string.paths);

        ((RoomDemoApplication) getApplication())
                .getApplicationComponent()
                .inject(this);

        PathAdapter.OnUserClickListener onUserClickListener =
                path -> {
                    Intent intent = new Intent(
                            ListPathsActivity.this,
                            MapActivity.class
                    );
                    intent.putExtra(MapActivity.ID_PATH, path.getId());
                    startActivity(intent);
                };

        adapter = new PathAdapter(onUserClickListener);
        mPathRecycler.setLayoutManager(new LinearLayoutManager(this));
        mPathRecycler.setAdapter(adapter);

        pathViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(PathViewModel.class);

        pathViewModel.getPaths().observe(
                this,
                paths -> adapter.setPaths(paths)
        );

        pathViewModel.getNewPathId().observe(
                this,
                pathId -> {
                    Intent intent = new Intent(
                            ListPathsActivity.this,
                            MapActivity.class
                    );
                    intent.putExtra(MapActivity.ID_PATH, pathId);
                    intent.putExtra(MapActivity.IS_RECORDING, true);
                    startActivity(intent);
                });

    }

    @OnClick(R.id.fab_create_new_path)
    void createPopUp() {
        if(!checkLocationPermission())
            return;

        BottomSheetDialog mDialog = new BottomSheetDialog(this);
        View PopUpView = LayoutInflater.from(this).inflate(R.layout.popup_create_path, null);

        TextView txtvNamePath = PopUpView.findViewById(R.id.name_path_create);
        Button btnCreatePath = PopUpView.findViewById(R.id.btn_path_create);

        btnCreatePath.setOnClickListener(v -> {
            String name = txtvNamePath.getText().toString();
            pathViewModel.insertPath(name);
            mDialog.cancel();
        });

        mDialog.contentView(PopUpView)
                .heightParam(ViewGroup.LayoutParams.WRAP_CONTENT)
                .inDuration(500)
                .cancelable(true)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_FINE_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createPopUp();
                }
            break;
        }
    }

    boolean checkLocationPermission() {
        int permissionGPS = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        );

        if (permissionGPS == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_PERMISSION_FINE_LOCATION);
            return false;
        }
    }
}
