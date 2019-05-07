package com.example.petplant.reminders;

public enum RepeatOption {
    NONE, DAILY, EVERY_OTHER_DAY, WEEKLY, MONTHLY;

    public static RepeatOption fromString(String option) {
        if (option.equals("Daily")) {
            return DAILY;
        } else if (option.equals("Every other day")) {
            return EVERY_OTHER_DAY;
        } else if (option.equals("Weekly")) {
            return WEEKLY;
        } else if (option.equals("Monthly")) {
            return MONTHLY;
        } else {
            return NONE;
        }
    }
}
