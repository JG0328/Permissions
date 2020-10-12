package com.pucmm.permissions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String PM_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PM_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PM_CAMERA = Manifest.permission.CAMERA;
    public static final String PM_PHONE = Manifest.permission.CALL_PHONE;
    public static final String PM_CONTACTS = Manifest.permission.READ_CONTACTS;

    private Switch swStorage, swLocation, swCamera, swPhone, swContacts;
    private Button btnCancel, btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swStorage = findViewById(R.id.swStorage);
        swLocation = findViewById(R.id.swLocation);
        swCamera = findViewById(R.id.swCamera);
        swPhone = findViewById(R.id.swPhone);
        swContacts = findViewById(R.id.swContacts);

        btnCancel = findViewById(R.id.btnCancel);
        btnContinue = findViewById(R.id.btnContinue);

        if (isGranted(PM_STORAGE)) swStorage.setChecked(true);
        if (isGranted(PM_LOCATION)) swLocation.setChecked(true);
        if (isGranted(PM_CAMERA)) swCamera.setChecked(true);
        if (isGranted(PM_PHONE)) swPhone.setChecked(true);
        if (isGranted(PM_CONTACTS)) swContacts.setChecked(true);

        btnCancel.setOnClickListener(v -> finishAffinity());

        btnContinue.setOnClickListener((v) -> {
            List<String> permissionsList = new ArrayList<>();

            if (swStorage.isChecked() && !isGranted(PM_STORAGE)) permissionsList.add(PM_STORAGE);
            if (swLocation.isChecked() && !isGranted(PM_LOCATION)) permissionsList.add(PM_LOCATION);
            if (swCamera.isChecked() && !isGranted(PM_CAMERA)) permissionsList.add(PM_CAMERA);
            if (swPhone.isChecked() && !isGranted(PM_PHONE)) permissionsList.add(PM_PHONE);
            if (swContacts.isChecked() && !isGranted(PM_CONTACTS)) permissionsList.add(PM_CONTACTS);

            if (permissionsList.size() > 0)
                ActivityCompat.requestPermissions(MainActivity.this, permissionsList.toArray(new String[0]), 1);
            else {
                goToNextActivity();
            }
        });
    }

    private Boolean isGranted(String permission) {
        return ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        goToNextActivity();
    }

    private void goToNextActivity() {
        Intent intent = new Intent(MainActivity.this, PermissionsActivity.class);
        startActivity(intent);
    }
}