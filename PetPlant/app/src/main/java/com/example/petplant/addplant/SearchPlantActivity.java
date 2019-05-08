package com.example.petplant.addplant;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import cz.msebera.android.httpclient.Header;

import com.example.petplant.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import static android.widget.LinearLayout.HORIZONTAL;

public class SearchPlantActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private AsyncTask<String, Void, String> analyze = new SearchPlantActivity.analyzeTask();
    private RecyclerView mrecyclerView;
    public ArrayList<PlantProfileCard> listPlant = new ArrayList<>();

    private SearchPlantAdapter lpadapter;
    private static final String plantURL = "https://trefle.io/api/plants?token=aUVNSXhKdTZFbGZ2cGprbzRFRkZSZz09&q=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_plant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        lpadapter = new SearchPlantAdapter(this, listPlant);
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
    public void fetchData(String query) {
        searchClient client = new searchClient();

        client.getSearch(query, new JsonHttpResponseHandler()  {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                JSONArray obj = response;
                List<PlantProfileCard> lp = new ArrayList<>();
                try {
                    for(int i=0;i<obj.length();i++) {
                        PlantInfo pinfo = new PlantInfo();
                        JSONObject jsonObject1 = obj.getJSONObject(i);
                        pinfo.setCommon(jsonObject1.optString("common_name"));
                        pinfo.setName(jsonObject1.optString("scientific_name"));
                        lp.add(new PlantProfileCard(pinfo));
                    } lpadapter.updateList(lp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }
    @Override
    public boolean onQueryTextSubmit(String s) {

        fetchData(s);
        lpadapter.updateList(listPlant);
        setAdapterAndUpdateData();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        /*String userInput = s.toLowerCase();

        List<PlantProfileCard> newList = new ArrayList<PlantProfileCard>();

        for(PlantProfileCard p:listPlant){
            if(p.name.toLowerCase().contains(userInput.toLowerCase())){
                newList.add(p);
            }
        }

        lpadapter.updateList(newList);*/
        return false;
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

        //lpadapter = new SearchPlantAdapter(this, listPlant);
        mrecyclerView.setAdapter(lpadapter);

    }
    private class analyzeTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String plantInfo) {
            super.onPostExecute(plantInfo);

        }
        @Override
        protected String doInBackground(String... params) {
            String r;
            try {
                Client client = ClientBuilder.newClient();
                String s = params[0];
                if (s.length() < 15) {
                    Response response1 = client.target(s).request().get();
                    r = response1.readEntity(String.class);
                } else{
                    r = null;
                }


            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \""  + "\"");

                return null;
            } return r;
        }
    }


}


