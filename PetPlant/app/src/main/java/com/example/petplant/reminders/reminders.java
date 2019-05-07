package com.example.petplant.reminders;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.petplant.R;

import java.util.Calendar;

public class reminders extends AppCompatActivity implements ReminderRecyclerAdapter.ItemClickListener{
    ReminderRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setItemIconTintList(null);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(reminders.this, CreateEditReminder.class);

                startActivity(intent);
            }
        });

        // data to populate the RecyclerView with
        String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48"};
        Reminder[] theReminders = new Reminder[]{
                new Reminder("Water Cactus", Calendar.getInstance()),
                new Reminder("Repot Spider Plant", Calendar.getInstance()),
                new Reminder("Water Flowers", Calendar.getInstance()),
                new Reminder("Water Lily", Calendar.getInstance()),
                new Reminder("Trim Holly Tree", Calendar.getInstance()),
                new Reminder("Water Rose Bush", Calendar.getInstance()),
                new Reminder("Water Thorn Bush", Calendar.getInstance()),
                new Reminder("Repot Lily Pad", Calendar.getInstance()),
                new Reminder("Trim Orchid", Calendar.getInstance()),
                new Reminder("Water Hasta Plant", Calendar.getInstance()),
        };
        int hoursToAdd = 11;
        for (int i = 0; i < theReminders.length; i++) {
            theReminders[i].time.add(Calendar.HOUR_OF_DAY, hoursToAdd);
            hoursToAdd += 11;
        }
        ReminderScreenDataset dataset = new ReminderScreenDataset(Calendar.getInstance(), theReminders);
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new ReminderRecyclerAdapter(this, dataset);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        /**RecyclerView calendar_recycler_view = (RecyclerView) findViewById(R.id.calendar_recycler_view);
        RecyclerView.LayoutManager recycler_layout = new GridLayoutManager(this, 2);
        calendar_recycler_view.setLayoutManager(recycler_layout);
        Reminder[] reminders = {
                new Reminder("Water 1", Calendar.getInstance()),
                new Reminder("Trim 2", Calendar.getInstance()),
                new Reminder("Water 3", Calendar.getInstance()),
                new Reminder("Rem11", Calendar.getInstance()),
                new Reminder("Rem12", Calendar.getInstance()),
                new Reminder("Rem13", Calendar.getInstance()),
                new Reminder("Rem14", Calendar.getInstance()),
                new Reminder("Rem15", Calendar.getInstance())
        };
        int hoursToAdd = 11;
        for (int i = 0; i < reminders.length; i++) {
            reminders[i].time.add(Calendar.HOUR_OF_DAY, hoursToAdd);
            hoursToAdd += 11;
        }
        ReminderScreenDataset dataset = new ReminderScreenDataset(Calendar.getInstance(), reminders);
        //RecyclerView.Adapter adapter = new ReminderAdapter()**/
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
    }

    public static void createNewReminder(Calendar time, String title, String notes, RepeatOption repeatOption) {

    }
}
