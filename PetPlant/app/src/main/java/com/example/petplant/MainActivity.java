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

import com.example.petplant.Experts.ExpertMain;
import com.example.petplant.reminders.reminders;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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

}
