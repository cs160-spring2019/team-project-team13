package com.example.petplant.reminders;

import java.util.ArrayList;
import java.util.Calendar;

public class ReminderUtilities {
    public static String monthIntToName(int monthInt) {
        switch (monthInt) {
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November";
            case 11:
                return "December";
            default:
                System.out.println("Strange month number?: " + monthInt);
                System.exit(666);
        }
        return "Error";
    }

    public static String dayOfWeekIntToNameAbbreviation(int dayOfWeek) {
        switch (dayOfWeek) {
            case 1:
                return "SUN";
            case 2:
                return "MON";
            case 3:
                return "TUES";
            case 4:
                return "WED";
            case 5:
                return "THUR";
            case 6:
                return "FRI";
            case 7:
                return "SAT";
            default:
                System.out.println("Day of week value is this for some reason?: " + dayOfWeek);
                System.exit(666);
        }
        return "Error";
    }

    public static String dayOfWeekIntToName(int dayOfWeek) {
        switch (dayOfWeek) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
            default:
                System.out.println("Day of week value is this for some reason?: " + dayOfWeek);
                System.exit(666);
        }
        return "Error";
    }

    public static String toStringReadableTime(Calendar calendar) {
        Calendar cal = (Calendar)calendar.clone();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        String AMslashPM = "AM";
        if (hour >= 12) {
            AMslashPM = "PM";
            hour = hour - 12;
        }
        if (hour == 0) {
            hour = 12;
        }
        String minuteString = "" + minute;
        if (minute < 10) {
            minuteString = "0" + minute;
        }
        return hour + ":" + minuteString + " " + AMslashPM;
    }

    public static String toStringReadableDate(Calendar calendar) {
        Calendar cal = (Calendar)calendar.clone();
        Calendar now = Calendar.getInstance();
        int nowMonth = now.get(Calendar.MONTH);
        int nowDayOfMonth = now.get(Calendar.DAY_OF_MONTH);
        int calMonth = cal.get(Calendar.MONTH);
        int calDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String returnVal = monthIntToName(calMonth) + " " + calDayOfMonth;
        if (nowMonth == calMonth) {
            if (nowDayOfMonth == calDayOfMonth) {
                returnVal = "Today";
            } else if (nowDayOfMonth + 1 == calDayOfMonth) {
                returnVal = "Tomorrow";
            } else if (nowDayOfMonth + 2 == calDayOfMonth) {
                returnVal = dayOfWeekIntToName(cal.get(Calendar.DAY_OF_WEEK));
            } else if (nowDayOfMonth + 3 == calDayOfMonth) {
                returnVal = dayOfWeekIntToName(cal.get(Calendar.DAY_OF_WEEK));
            } else if (nowDayOfMonth + 4 == calDayOfMonth) {
                returnVal = dayOfWeekIntToName(cal.get(Calendar.DAY_OF_WEEK));
            } else if (nowDayOfMonth + 5 == calDayOfMonth) {
                returnVal = dayOfWeekIntToName(cal.get(Calendar.DAY_OF_WEEK));
            } else if (nowDayOfMonth + 6 == calDayOfMonth) {
                returnVal = dayOfWeekIntToName(cal.get(Calendar.DAY_OF_WEEK));
            }
        }
        return returnVal;
    }

    public static String reminderDisplayText(Reminder reminder) {
        String day = toStringReadableDate(reminder.calendar);
        String time = toStringReadableTime(reminder.calendar);
        return day + ", " + time + ": " + reminder.title; //TODO
    }

    public static ArrayList<Reminder> sortReminders(ArrayList<Reminder> reminders) {
        ArrayList<Reminder> returnVal = new ArrayList<>();
        while (! reminders.isEmpty()) {
            long minTime = Long.MAX_VALUE;
            int minIndex = -1;
            for (int i = 0; i < reminders.size(); i++) {
                if (reminders.get(i).calendar.getTimeInMillis() < minTime) {
                    minIndex = i;
                }
            }
            returnVal.add(reminders.get(minIndex));
            reminders.remove(minIndex);
        }
        return returnVal;
    }
}
