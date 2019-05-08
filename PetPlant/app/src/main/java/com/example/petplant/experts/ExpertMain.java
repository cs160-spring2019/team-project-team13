package com.example.petplant.experts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.petplant.R;
import com.example.petplant.addplant.MyPlants;
import com.example.petplant.camera.view.DiseaseActivity;
import com.example.petplant.reminders.reminders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExpertMain extends AppCompatActivity implements SearchView.OnQueryTextListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter adapter;
    private List<OneExpert> listExperts = new ArrayList<OneExpert>();

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(this);

        initExperts();

        // recyclerView
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(listExperts);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        adapter.notifyDataSetChanged();

        //toolbar
        toolbar = findViewById(R.id.toolbarExpert);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Experts");

//                setDisplayHomeAsUpEnabled(true);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search_icon);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    private void initExperts() {
        //https://www.youtube.com/watch?v=h71Ia9iFWfI
        String json;
        try {
            InputStream is = getAssets().open("experts_info.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            // read all bear status
            for(int i = 0; i < jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                int drawableId = getResources().getIdentifier(obj.getString("expert_img_filename"), "drawable", getPackageName());
                System.out.println("===");
                System.out.println(drawableId);
                listExperts.add(new OneExpert(drawableId, obj.getString("expert_name"), obj.getString("expert_title"), obj.getString("expert_specialties"), obj.getString("expert_img_filename")));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        String userInput = s.toLowerCase();

        List<OneExpert> newList = new ArrayList<OneExpert>();

        for(OneExpert oneExpert:listExperts){
            if(oneExpert.getName().toLowerCase().contains(userInput)){
                newList.add(oneExpert);
            }
        }

        adapter.updateList(newList);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getTitle() == getString(R.string.title_myplants)) {
            Intent i = new Intent(this, MyPlants.class);
            startActivity(i);
        } else if (menuItem.getTitle() == getString(R.string.title_reminders)) {
            Intent i = new Intent(this, reminders.class);
            startActivity(i);
        } else if (menuItem.getTitle() == getString(R.string.title_scan)) {
            Intent i = new Intent(this, DiseaseActivity.class);
            startActivity(i);
        } else if (menuItem.getTitle() == getString(R.string.title_expert)) {
            Intent i = new Intent(this, ExpertMain.class);
            startActivity(i);
        }
        return true;
    }
}
