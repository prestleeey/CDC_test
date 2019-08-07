package com.example.cdc_test.data.Path;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Path {

    @PrimaryKey(autoGenerate = true)
    Long id;
    String name;
    String date;

    public Path(String name) {
        this.name = name;
        this.date = (new SimpleDateFormat("dd.MM.yyyy")).format(new Date());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
