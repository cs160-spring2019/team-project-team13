package com.example.petplant.reminders;

public class Old {
}

/*
private ArrayList<Reminder> dataset;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    ReminderRecyclerAdapter(Context context, ArrayList<Reminder> dataset) {
        this.mInflater = LayoutInflater.from(context);
        this.dataset = dataset;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.reminder_recycler_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        String theText = ReminderUtilities.reminderDisplayText(this.dataset.get(position));
        holder.myTextView.setText(theText);
        if (theText.equals("")) {
            holder.myTextView.setBackgroundColor(0);
        }//TODO
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return this.dataset.size();
    } //TODO


    // stores and recycles views as they are scrolled off screen
    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.info_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

/*    // convenience method for getting data at click position
    String getItem(int id) {
        return this.dataset.get(id); //TODO
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

// parent activity will implement this method to respond to click events
public interface ItemClickListener {
    void onItemClick(View view, int position);
}
 */

/*
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
 //RecyclerView.Adapter adapter = new ReminderAdapter()
    }

@Override
public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
        }

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
                    if (theGettedReminder.calendar.get(Calendar.HOUR_OF_DAY) >= 12) {
                        isAM = false;
                    }
                    int realTime = theGettedReminder.calendar.get(Calendar.HOUR);
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
        if (reminder.calendar.getTimeInMillis() < currentDate.getTimeInMillis()) {
            return - 1;
                                                    //more than 6 days from now
        } else if (reminder.calendar.getTimeInMillis() > 864_000_000L * 6L + currentDate.getTimeInMillis()){
            return -1;
        }
        int remDay = reminder.calendar.get(Calendar.DAY_OF_MONTH);
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
 */
