package com.example.petplant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petplant.Experts.ExpertMain;
import com.example.petplant.camera.view.DiseaseActivity;
import com.example.petplant.reminders.reminders;
import com.example.petplant.scanDiseases.CameraDemoApp;
import com.example.petplant.camera.model.PermissionsModel;
import com.example.petplant.camera.util.BitmapUtil;
import com.example.petplant.camera.view.TakePhotoActivity;

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

        Button buttonExpert = findViewById(R.id.expertb);
        buttonExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExpertMain.class);

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
                    Intent intent = new Intent(MainActivity.this, DiseaseActivity.class);
                    startActivity(intent);
//                    startActivityForResult(intent, TakePhotoActivity.REQUEST_CAPTRUE_CODE);
                }
            }
        });
    }
}
