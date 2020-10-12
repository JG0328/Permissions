package com.pucmm.permissions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PermissionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}