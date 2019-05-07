package com.example.petplant.reminders.adapters;

/**import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.petplant.reminders.ReminderScreenDataset;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {
    ReminderScreenDataset dataset;

    public static class ReminderViewHolder extends RecyclerView.ViewHolder {
        View view;
        public ReminderViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }

    public ReminderAdapter(ReminderScreenDataset dataset) {
        this.dataset = dataset;
    }

    @Override
    public ReminderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //create view
        //return viewholder
    }

    @Override
    public void onBindViewHolder(ReminderViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataset.numItems();
    }
}**/
