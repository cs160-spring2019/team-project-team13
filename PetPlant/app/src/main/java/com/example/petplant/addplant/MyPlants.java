package com.example.petplant.addplant;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.petplant.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MyPlants extends AppCompatActivity {

    private RecyclerView mrecyclerView;
    LinearLayout layout;
    private RecyclerView.Adapter mAdapter;
    private List<PlantProfileCard> listPlants = new ArrayList<PlantProfileCard>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plants);

        initPlants();
        layout =  findViewById(R.id.plant_layout);
        mrecyclerView = findViewById(R.id.plant_recycler);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setItemIconTintList(null);

       setAdapterAndUpdateData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.search_icon);


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

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            // read all bear status

            for(int i = 0; i < jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                int drawableId = getResources().getIdentifier(obj.getString("expert_img_filename"), "drawable", getPackageName());
                listPlants.add(new PlantProfileCard(drawableId, obj.getString("expert_name"), obj.getString("expert_title"),  obj.getString("expert_img_filename")));
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
}
