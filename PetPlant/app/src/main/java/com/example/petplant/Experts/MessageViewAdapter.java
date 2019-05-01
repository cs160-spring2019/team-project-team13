package com.example.petplant.Experts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.petplant.R;

import java.util.ArrayList;
import java.util.List;

class MessageViewHolder extends RecyclerView.ViewHolder{

    public TextView show_message;
    public ImageView profile_image;

    public MessageViewHolder(View itemView) {
        super(itemView);
        show_message = itemView.findViewById(R.id.show_message);
        profile_image = itemView.findViewById(R.id.profile_image);
    }
}

public class MessageViewAdapter extends RecyclerView.Adapter<MessageViewHolder>{

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private List<Chat> mChat;


    public MessageViewAdapter(List<Chat> listChats){
        this.mChat = listChats;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == MSG_TYPE_RIGHT) {
            View oneMessage = inflater.inflate(R.layout.chat_item_right, parent, false);
            return new MessageViewHolder(oneMessage);
        } else {
            View oneMessage = inflater.inflate(R.layout.chat_item_left, parent, false);
            return new MessageViewHolder(oneMessage);
        }
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {

        Chat chat = mChat.get(position);
        holder.show_message.setText(chat.getMessage());
        holder.profile_image.setImageResource(chat.getImage());
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mChat.get(position).getSender().equals("TestUser")){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}