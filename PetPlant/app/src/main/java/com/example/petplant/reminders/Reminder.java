package com.example.petplant.reminders;

import java.io.Serializable;
import java.util.Calendar;

public class Reminder implements Serializable {
    Calendar calendar;
    String title;
    String notes;
    RepeatOption repeatOption;

    public Reminder(Calendar calendar, String title, String notes, RepeatOption repeatOption) {
        this.calendar = calendar;
        this.title = title;
        this.notes = notes;
        this.repeatOption = repeatOption;
    }
}
