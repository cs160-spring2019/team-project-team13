package com.example.petplant.reminders;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.petplant.R;

import java.util.ArrayList;

public class ReminderRecyclerAdapter extends RecyclerView.Adapter<ReminderRecyclerAdapter.RecyclerViewHolder> {
    private ArrayList<Reminder> dataset;
    private LayoutInflater layoutInflater;
    private ItemClickListener itemClickListener;

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.reminder_text_on_reminder_home_screen);
        }

        @Override
        public void onClick(View view) {
            System.exit(909);
            if (itemClickListener != null) {
                itemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public ReminderRecyclerAdapter(Context context, ArrayList<Reminder> dataset) {
        layoutInflater = LayoutInflater.from(context);
        this.dataset = dataset;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.reminder_recycler_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.textView.setText(ReminderUtilities.reminderDisplayText(dataset.get(position)));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
