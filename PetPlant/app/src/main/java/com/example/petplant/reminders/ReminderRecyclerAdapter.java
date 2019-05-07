package com.example.petplant.reminders;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.petplant.R;

public class ReminderRecyclerAdapter extends RecyclerView.Adapter<ReminderRecyclerAdapter.RecyclerViewHolder> {

    private ReminderScreenDataset dataset;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    ReminderRecyclerAdapter(Context context, ReminderScreenDataset dataset) {
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
        String theText = this.dataset.components.get(position);
        holder.myTextView.setText(theText);
        if (theText.equals("")) {
            holder.myTextView.setBackgroundColor(0);
        }//TODO
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return this.dataset.components.size();
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

    // convenience method for getting data at click position
    String getItem(int id) {
        return this.dataset.components.get(id); //TODO
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
