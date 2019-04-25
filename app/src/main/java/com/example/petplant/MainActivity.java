package com.example.petplant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.petplant.Experts.Activity_Expert;
import com.example.petplant.reminders.reminders;
import com.example.petplant.scanDiseases.CameraDemoApp;
import com.awen.camera.model.PermissionsModel;
import com.awen.camera.util.BitmapUtil;
import com.awen.camera.view.TakePhotoActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTextMessage;
    private Button cameraBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraBtn = (Button) findViewById(R.id.scanb);
        cameraBtn.setOnClickListener(this);

        Button button = findViewById(R.id.remindersb);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, reminders.class);

                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        PermissionsModel permissionsModel = new PermissionsModel(this);
        permissionsModel.checkCameraPermission(new PermissionsModel.PermissionListener() {
            @Override
            public void onPermission(boolean isPermission) {
                if (isPermission) {
                    Intent intent = new Intent(MainActivity.this, TakePhotoActivity.class);
                    startActivityForResult(intent, TakePhotoActivity.REQUEST_CAPTRUE_CODE);
                }
            }
        });
    }
}
