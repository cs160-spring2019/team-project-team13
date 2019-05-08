package com.example.petplant.reminders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.petplant.R;

import java.io.ObjectOutputStream;
import java.util.Calendar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class EditReminder extends AppCompatActivity{


    @BindView(R.id.time)
    TextView timeText;
    @BindView(R.id.date) TextView dateText;
    private Calendar calendar;
    private Spinner reminderActionsSpinner;
    private TextView repeatOptionTextView;
    private LinearLayout repeatRow;
    private int placementInDataset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_reminder);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }

        calendar = Calendar.getInstance();

        reminderActionsSpinner = findViewById(R.id.reminder_actions_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.reminder_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reminderActionsSpinner.setAdapter(adapter);

        repeatOptionTextView = findViewById(R.id.repeat_day);
        repeatRow = findViewById(R.id.repeat_row);

        Intent intent = getIntent();
        placementInDataset = intent.getIntExtra("placementInDataset", 0);
        Reminder theReminder = (Reminder) intent.getSerializableExtra("reminder");
    }
    @OnClick(R.id.time_row)
    public void timePicker() {
        TimePickerDialog TimePicker = new TimePickerDialog(EditReminder.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(android.widget.TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                timeText.setText(ReminderUtilities.toStringReadableTime(calendar));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(this));
        TimePicker.show();
    }
    @OnClick(R.id.date_row)
    public void datePicker(View view) {
        DatePickerDialog DatePicker = new DatePickerDialog(EditReminder.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker DatePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateText.setText(ReminderUtilities.toStringReadableDate(calendar));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        DatePicker.show();
    }
    @OnClick(R.id.repeat_row)
    public void repeatPicker() {
        //TODO doing popup menu wrong somehow. Repeat row recognizes click but menu
        // does not pop up
        // Using this and CreateEditReminder.this gives the same result
        PopupMenu popup = new PopupMenu(this, repeatRow);
        popup.getMenuInflater().inflate(R.menu.repeat_row_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                repeatOptionTextView.setText(item.getTitle());
                return true;
            }
        });

        popup.show();
    }
    @OnClick(R.id.save_button)
    public void saveReminder() {
        String task = reminderActionsSpinner.getSelectedItem().toString();
        task = task.substring(0, task.length() - 6);
        String notes = ((EditText)findViewById(R.id.notification_content)).getText().toString();
        RepeatOption repeatOption = RepeatOption.fromString(repeatOptionTextView.getText().toString());
        Reminder newReminder = new Reminder(calendar, task, notes, repeatOption);
        Intent i = new Intent(this, reminders.class);
        i.putExtra("newReminder", newReminder);
        startActivity(i);
    }
    @OnClick(R.id.delete_button)
    public void deleteReminder() {
        Intent i = new Intent(this, reminders.class);
        i.putExtra("deleteReminder", placementInDataset);
        startActivity(i);
    }
}
