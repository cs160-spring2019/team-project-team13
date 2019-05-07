package com.example.petplant.reminders;

import java.util.ArrayList;
import java.util.Calendar;

public class ReminderScreenDataset {
    Calendar currentDate;
    Reminder[] reminders;
    ArrayList<Reminder>[] remindersByDay;
    ArrayList<String> components;
    int[] daysOfMonth;
    String[] daysOfWeek;

    //Will just display 7 days starting from currentDate
    public ReminderScreenDataset(Calendar currentDate, Reminder[] reminders) {//TODO
        this.currentDate = currentDate;
        this.reminders = reminders; //TODO
        this.remindersByDay = new ArrayList[7];
        for (int i = 0; i < 7; i++) {
            this.remindersByDay[i] = new ArrayList<>();
        }
        this.components = new ArrayList<>();

        int currentMonthInt = currentDate.get(Calendar.MONTH);
        String currentMonth = monthIntToName(currentMonthInt);
        daysOfMonth = new int[7];
        daysOfWeek = new String[7];
        for (int i = 0; i < 7; i++) {
            Calendar thisDate = (Calendar)currentDate.clone();
            thisDate.add(Calendar.DATE, i);
            daysOfMonth[i] = thisDate.get(Calendar.DAY_OF_MONTH);
            daysOfWeek[i] = dayOfWeekIntToNameAbbreviation(thisDate.get(Calendar.DAY_OF_WEEK));
        }

        for (int i = 0; i < reminders.length; i++) {
            int dayItFitsIn = findDayItFitsIn(reminders[i]);
            if (dayItFitsIn != -1) {
                remindersByDay[dayItFitsIn].add(reminders[i]);
            }
        }
        for (int i = 0; i < 7; i++) {
            components.add(daysOfWeek[i] + " " + daysOfMonth[i]);
            if (remindersByDay[i].isEmpty()) {
                components.add("");
                components.add("");
            } else {
                for (int j = 0; j < remindersByDay[i].size(); j++) {
                    Reminder theGettedReminder = remindersByDay[i].get(j);
                    boolean isAM = true;
                    if (theGettedReminder.time.get(Calendar.HOUR_OF_DAY) >= 12) {
                        isAM = false;
                    }
                    int realTime = theGettedReminder.time.get(Calendar.HOUR);
                    if (realTime == 0) {
                        realTime = 12;
                    }
                    String time = realTime + " PM";
                    if (isAM) {
                        time = realTime + "AM";
                    }
                    components.add(theGettedReminder.title + "\n" + time);
                    components.add("");
                }
            }
            components.add("");
        }
    }

    //return 0-6, 0 is today, or -1, if it is not in range
    private int findDayItFitsIn(Reminder reminder) {
        if (reminder.time.getTimeInMillis() < currentDate.getTimeInMillis()) {
            return - 1;
                                                    //more than 6 days from now
        } else if (reminder.time.getTimeInMillis() > 864_000_000L * 6L + currentDate.getTimeInMillis()){
            return -1;
        }
        int remDay = reminder.time.get(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < 7; i++) {
            if (remDay == daysOfMonth[i]) {
                return i;
            }
        }
        System.out.println("Error: Doesn't fit in a day but should");
        System.exit(666);
        return -1;
    }

    public int numItems() {
        return 13;
    }//TODO

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
}
