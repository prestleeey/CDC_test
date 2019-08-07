package com.example.cdc_test.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.example.cdc_test.data.Coordinates.Coordinates;
import com.example.cdc_test.data.Coordinates.CoordinatesRepository;
import java.util.List;

public class CoordinatesViewModel extends ViewModel {
    private CoordinatesRepository repository;

    public CoordinatesViewModel(CoordinatesRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Coordinates>> getCoordinatesByPathId(Long idPath) {
        return repository.getCoordinatesByPathId(idPath);
    }
}
