package com.example.petplant.reminders;

import java.sql.Time;
import java.util.Calendar;

public class Reminder {
    String title;
    Calendar time;

    public Reminder(String title, Calendar time) {
        this.title = title;
        this.time = time;
    }
}
