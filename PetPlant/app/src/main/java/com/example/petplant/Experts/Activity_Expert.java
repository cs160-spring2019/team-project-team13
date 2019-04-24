package com.example.petplant.Experts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.petplant.R;

import org.w3c.dom.Text;

public class Activity_Expert extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert);
        setTitle("Experts");

        TextView mTextMessage = (TextView) findViewById(R.id.expert_message);

        mTextMessage.setText("lalala expert activity!");

    }
}
