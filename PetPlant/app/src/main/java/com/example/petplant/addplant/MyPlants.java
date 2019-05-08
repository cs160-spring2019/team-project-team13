package com.example.petplant.addplant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.petplant.R;
import com.example.petplant.camera.view.DiseaseActivity;
import com.example.petplant.camera.view.TakePhotoActivity;
import com.example.petplant.experts.ExpertMain;
import com.example.petplant.reminders.reminders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MyPlants extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private LinearLayout linearLayout;
    private RecyclerView mrecyclerView;

    public FloatingActionButton fab;
    private MyPlantsAdapter mAdapter;
    private ArrayList<PlantProfileCard> listPlants = new ArrayList<PlantProfileCard>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plants);

        initPlants();
        linearLayout = findViewById(R.id.empty_view);
        mrecyclerView = findViewById(R.id.search_plant);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(this);
        fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popupMenu = new PopupMenu(MyPlants.this, fab);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("Scan")){
                            Intent intent = new Intent(MyPlants.this, PlantInfoActivity.class);
                            intent.putExtra("ifScan", 1);

                            startActivity(intent);
                            return true;
                        }
                        if(item.getTitle().equals("Search")){
                            Intent intent = new Intent(MyPlants.this, SearchPlantActivity.class);

                            startActivity(intent);
                            return true;
                        }
                        Toast.makeText(MyPlants.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popupMenu.show();

            }
        });

       setAdapterAndUpdateData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);




        return true;
    }

    private void initPlants() {
        //https://www.youtube.com/watch?v=h71Ia9iFWfI
        String json;
        try {
            InputStream is = getAssets().open("my_plants.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(json);

            // read all bear status

            for(int i = 0; i < jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                int drawableId = getResources().getIdentifier(obj.getString("plant_img_filename"), "drawable", getPackageName());
                listPlants.add(new PlantProfileCard(drawableId, obj.getString("plant_name"), obj.getString("plant_title"),  obj.getString("plant_img_filename")));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void setAdapterAndUpdateData() {

        mAdapter = new MyPlantsAdapter(this, listPlants);
        mrecyclerView.setAdapter(mAdapter);
        if (mAdapter.getItemCount() == 0) {
            mrecyclerView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            mrecyclerView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.action_settings:
                Intent preferenceIntent = new Intent(this, PreferenceActivity.class);
                startActivity(preferenceIntent);
                return true;
            case R.id.action_about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;*/
        }
        return super.onOptionsItemSelected(item);
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
