package com.csc.weatherapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by Oleg Doronin
 * WhetherApp
 * Copyright (c) 2016 CS. All rights reserved.
 */
public class DescriptionCityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_city);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_description, new DescriptionCityFragment())
                    .commit();
        }
    }
}
