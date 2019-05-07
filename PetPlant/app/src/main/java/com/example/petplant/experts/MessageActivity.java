package com.example.petplant.experts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.support.v7.widget.Toolbar;

import com.example.petplant.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    DatabaseReference  databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser fuser;
    Toolbar toolbar;
    ImageButton btn_send;
    EditText text_send;

    MessageViewAdapter messageViewAdapter;
    List<Chat> mchat = new ArrayList<Chat>();

    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        toolbar = findViewById(R.id.toolbarMessage);
        btn_send = findViewById(R.id.button_send);
        text_send = findViewById(R.id.text_send);

        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        final String expertname = i.getStringExtra("expertname");
        setTitle(expertname);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("TestUser");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat = new ArrayList<Chat>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String oneMessage = data.child("message").getValue(String.class);
                    String oneSender = data.child("sender").getValue(String.class);
                    String oneReceiver = data.child("receiver").getValue(String.class);
                    if (oneSender.equals("TestUser") && oneReceiver.equals(expertname)){
                        int drawableId = getResources().getIdentifier("thenos", "drawable", getPackageName());
                        System.out.println("===");
                        System.out.println(drawableId);
                        Chat chat = new Chat(oneSender, oneReceiver, oneMessage, drawableId);
                        mchat.add(chat);
                    } else if (oneReceiver.equals("TestUser") && oneSender.equals(expertname))
                    {
                        String[] splited = oneSender.split("\\s+");
                        int drawableId = getResources().getIdentifier(splited[0].toLowerCase(), "drawable", getPackageName());
                        Chat chat = new Chat(oneSender, oneReceiver, oneMessage, drawableId);
                        mchat.add(chat);
                    }
                }
                setAdapterAndUpdateData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //send message
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if(!msg.equals("")){
                    sendMessage("TestUser", expertname, msg);
                    text_send.setText("");
                }
            }
        });

        // recyclerView
        recyclerView = findViewById(R.id.recyclerViewMessage);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        setAdapterAndUpdateData();
        messageViewAdapter.notifyDataSetChanged();

    }


    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference referenceSender = FirebaseDatabase.getInstance().getReference(sender);
        DatabaseReference referenceReceiver = FirebaseDatabase.getInstance().getReference(receiver);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        referenceSender.push().setValue(hashMap);
        referenceReceiver.push().setValue(hashMap);
    }

    private void setAdapterAndUpdateData() {
        // create a new adapter with the updated mComments array
        // this will "refresh" our recycler view
        messageViewAdapter = new MessageViewAdapter(mchat);
        recyclerView.setAdapter(messageViewAdapter);

        // scroll to the last comment
        if (mchat.size() == 0) {
            recyclerView.smoothScrollToPosition(0);
        } else {
            recyclerView.smoothScrollToPosition(mchat.size() - 1);
        }
    }

}
