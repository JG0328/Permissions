package com.pucmm.permissions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

public class PermissionsActivity extends AppCompatActivity {
    private Button btnStorage, btnLocation, btnCamera, btnPhone, btnContacts;
    private ConstraintLayout mainLayout;
    private String msgGranted = "Permission already granted";
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnStorage = findViewById(R.id.btnStorage);
        btnLocation = findViewById(R.id.btnLocation);
        btnCamera = findViewById(R.id.btnCamera);
        btnPhone = findViewById(R.id.btnPhone);
        btnContacts = findViewById(R.id.btnContacts);

        mainLayout = findViewById(R.id.mainLayout);

        btnStorage.setOnClickListener((v) -> {
            if (!isGranted(MainActivity.PM_STORAGE)) showDenied();
            else {
                snackbar = getSnackbar();
                snackbar.setAction("OPEN", (a) -> {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.setType("image/*");
                    startActivity(intent);
                });
                snackbar.show();
            }
        });

        btnLocation.setOnClickListener((v) -> {
            if (!isGranted(MainActivity.PM_LOCATION)) showDenied();
            else {
                snackbar = getSnackbar();
                snackbar.setAction("OPEN", (a) -> {
                    Uri navigationIntentUri = Uri.parse("geo:" + 19.417086 + "," + -70.699644);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                    startActivity(mapIntent);
                });
                snackbar.show();
            }
        });

        btnCamera.setOnClickListener((v) -> {
            if (!isGranted(MainActivity.PM_CAMERA)) showDenied();
            else {
                snackbar = getSnackbar();
                snackbar.setAction("OPEN", (a) -> {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(intent);
                });
                snackbar.show();
            }
        });

        btnPhone.setOnClickListener((v) -> {
            if (!isGranted(MainActivity.PM_PHONE)) showDenied();
            else {
                snackbar = getSnackbar();
                snackbar.setAction("OPEN", (a) -> {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:18299830166"));
                    startActivity(intent);
                });
                snackbar.show();
            }
        });

        btnContacts.setOnClickListener((v) -> {
            if (!isGranted(MainActivity.PM_CONTACTS)) showDenied();
            else {
                snackbar = getSnackbar();
                snackbar.setAction("OPEN", (a) -> {
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivity(intent);
                });
                snackbar.show();
            }
        });
    }

    private Boolean isGranted(String permission) {
        return ContextCompat.checkSelfPermission(PermissionsActivity.this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    private Snackbar getSnackbar() {
        return Snackbar.make(mainLayout, msgGranted, Snackbar.LENGTH_SHORT);
    }

    private void showDenied() {
        Snackbar snackbar = Snackbar.make(mainLayout, "Please request permission", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}