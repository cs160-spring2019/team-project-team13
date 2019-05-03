package com.example.petplant.addplant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;


import com.example.petplant.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.widget.LinearLayout.HORIZONTAL;

public class SearchPlantActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private RecyclerView mrecyclerView;
    private List<PlantProfileCard> listPlant = new ArrayList<>();
    private SearchPlantAdapter lpadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_plant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initPlants();

        mrecyclerView = findViewById(R.id.search_plant);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //DividerItemDecoration itemDecor = new DividerItemDecoration(SearchPlantActivity.this, HORIZONTAL);
        //mrecyclerView.addItemDecoration(itemDecor);
        setAdapterAndUpdateData();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        String userInput = s.toLowerCase();

        List<PlantProfileCard> newList = new ArrayList<PlantProfileCard>();

        for(PlantProfileCard p:listPlant){
            if(p.name.toLowerCase().contains(userInput.toLowerCase())){
                newList.add(p);
            }
        }

        lpadapter.updateList(newList);
        return true;
    }
    private void initPlants() {
        //https://www.youtube.com/watch?v=h71Ia9iFWfI
        String json;
        try {
            InputStream is = getAssets().open("plants.json");
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
                listPlant.add(new PlantProfileCard(drawableId, obj.getString("plant_name"), obj.getString("plant_title"),  obj.getString("plant_img_filename")));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void setAdapterAndUpdateData() {

        lpadapter = new SearchPlantAdapter(this, listPlant);
        mrecyclerView.setAdapter(lpadapter);

    }



}


