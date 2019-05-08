package com.example.petplant.reminders;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.petplant.R;
import com.example.petplant.addplant.MyPlants;
import com.example.petplant.camera.view.DiseaseActivity;
import com.example.petplant.experts.ExpertMain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.OnClick;

public class reminders extends AppCompatActivity implements ReminderRecyclerAdapter.ItemClickListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private ReminderRecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Reminder> dataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("HI THERE");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        navigation.setItemIconTintList(null);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(reminders.this, CreateEditReminder.class);

                startActivity(intent);
            }
        });
        dataset = new ArrayList<>();
        try {
            ObjectInputStream inputStream = new ObjectInputStream(openFileInput("reminderz.obj"));
            while (true) {
                try {
                    Object nextObject = inputStream.readObject();
                    Reminder nextReminder = (Reminder) nextObject;
                    dataset.add(nextReminder);
                } catch (Exception e) {
                    break;
                }
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            new File(getFilesDir(), "reminderz.obj");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-12);
        }
        Intent intent = getIntent();
        Serializable newObject = intent.getSerializableExtra("newReminder");
        if (newObject != null) {
            Reminder theNewReminder = (Reminder)newObject;
            dataset.add(theNewReminder);
            Calendar nextDate = (Calendar)theNewReminder.calendar.clone();
            switch (theNewReminder.repeatOption) {
                case NONE:
                    break;
                case DAILY:
                    for (int i = 0; i < 6; i++) {
                        nextDate = (Calendar)nextDate.clone();
                        nextDate.add(Calendar.DAY_OF_YEAR, 1);
                        dataset.add(new Reminder(nextDate,
                                theNewReminder.title, theNewReminder.notes, theNewReminder.repeatOption));
                    }
                    break;
                case EVERY_OTHER_DAY:
                    for (int i = 0; i < 6; i++) {
                        nextDate = (Calendar)nextDate.clone();
                        nextDate.add(Calendar.DAY_OF_YEAR, 2);
                        dataset.add(new Reminder(nextDate,
                                theNewReminder.title, theNewReminder.notes, theNewReminder.repeatOption));
                    }
                    break;
                case WEEKLY:
                    for (int i = 0; i < 6; i++) {
                        nextDate = (Calendar)nextDate.clone();
                        nextDate.add(Calendar.DAY_OF_YEAR, 7);
                        dataset.add(new Reminder(nextDate,
                                theNewReminder.title, theNewReminder.notes, theNewReminder.repeatOption));
                    }
                    break;
                case MONTHLY:
                    for (int i = 0; i < 6; i++) {
                        nextDate = (Calendar)nextDate.clone();
                        nextDate.add(Calendar.MONTH, 1);
                        dataset.add(new Reminder(nextDate,
                                theNewReminder.title, theNewReminder.notes, theNewReminder.repeatOption));
                    }
                    break;
            }
        } else {
            int itemToDelete = intent.getIntExtra("deleteReminder", -1);
            if (itemToDelete != -1) {
                dataset.remove(itemToDelete);
            }
        }

        dataset = ReminderUtilities.sortReminders(dataset);

        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(openFileOutput("reminderz.obj", 0));
            for (int i = 0; i < dataset.size(); i++) {
                Reminder currentReminder = dataset.get(i);
                outputStream.writeObject(currentReminder);
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-11);
        }


        recyclerView = findViewById(R.id.reminders_recycler_view);
        recyclerView.setHasFixedSize(true); //TODO correct?
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ReminderRecyclerAdapter(this, dataset);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        System.exit(789);
        Intent i = new Intent(this, EditReminder.class);
        i.putExtra("reminder", dataset.get(position));
        i.putExtra("placementInDataset", position);
        startActivity(i);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getTitle() == getString(R.string.title_myplants)) {
            Intent i = new Intent(this, MyPlants.class);
            startActivity(i);
        } else if (menuItem.getTitle() == getString(R.string.title_reminders)) {
            Intent i = new Intent(this, reminders.class);
            startActivity(i);
        } else if (menuItem.getTitle() == getString(R.string.title_scan)) {
            Intent i = new Intent(this, DiseaseActivity.class);
            startActivity(i);
        } else if (menuItem.getTitle() == getString(R.string.title_expert)) {
            Intent i = new Intent(this, ExpertMain.class);
            startActivity(i);
        }
        return true;
    }
}
