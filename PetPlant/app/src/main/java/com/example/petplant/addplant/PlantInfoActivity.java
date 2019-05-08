package com.example.petplant.addplant;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.petplant.R;
import com.example.petplant.camera.util.BitmapUtil;
import com.example.petplant.camera.view.TakePhotoActivity;
import com.example.petplant.reminders.CreateEditReminder;
import com.example.petplant.reminders.reminders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlantInfoActivity extends AppCompatActivity {
    public View img;
    private ImageView plantImg;
    private TextView plantName;
    private TextView probability;
    private TextView confidence;
    private WebView wikiInfo;
    private Button info;
    private Button remindersb;
    private LinearLayout layout;
    private ProgressDialog progress;
    private AsyncTask<String, Void, PlantInfo> analyze = new PlantInfoActivity.analyzeTask();
    private PlantInfo plantInfo;
    private static final String identifyURL = "https://api.plant.id/identify";
    private static final String suggestionURL = "https://private-anon-79238c3314-plantid.apiary-proxy.com/check_identifications";
    private static final String plantURL = "https://trefle.io/api/plants?token=aUVNSXhKdTZFbGZ2cGprbzRFRkZSZz09&q=";
    private String wikiURL = "https://en.wikipedia.org/wiki/";
    private Dialog myDialog;
    private Context mContext;
    private PlantInfo pinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_info);
        myDialog = new Dialog(this);
        mContext = getApplicationContext();
        layout = findViewById(R.id.la);
        wikiInfo = findViewById(R.id.wikiView);
        if(savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, new MainFragment(), "MainFragment");
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //plantImg =  findViewById(R.id.civProfilePic);
        //plantName =  findViewById(R.id.tvAddress);
        //probability = (TextView) findViewById(R.id.probability);
        //confidence = (TextView) findViewById(R.id.confidence);

        if(getInfo() == 1) {
            Intent intent = new Intent(PlantInfoActivity.this, TakePhotoActivity.class);
            startActivityForResult(intent, TakePhotoActivity.REQUEST_CAPTRUE_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TakePhotoActivity.REQUEST_CAPTRUE_CODE: {
                    String path = data.getStringExtra(TakePhotoActivity.RESULT_PHOTO_PATH);
                    String[] paths = {path};

                    progress = new ProgressDialog(PlantInfoActivity.this);
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setIndeterminate(false);
                    progress.setCancelable(true);
                    progress.setTitle("Analyzing, please wait...");
                    progress.setMessage("Want faster speed? Join our membership NOW for only $28.88/month!");

                    analyze.execute(paths);

//                    plantImg.setImageBitmap(BitmapUtil.getBitmap(path));
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_confirm, menu);

        return true;
    }

    private int getInfo() {
        return getIntent().getIntExtra("ifScan", 0);
    }

    private class analyzeTask extends AsyncTask<String, Void, PlantInfo> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }

        @Override
        protected void onPostExecute(PlantInfo plantInfo) {
            super.onPostExecute(plantInfo);
            Bitmap bitmap = BitmapUtil.getBitmap(plantInfo.getPath());
            RelativeLayout f = findViewById(R.id.relativeLayout);
            FrameLayout fr = findViewById(R.id.photoHeader);
            TextView comm_name = f.findViewById(R.id.common_name);
            TextView scientific_name = f.findViewById(R.id.sci_name);
            ImageView plantPic = fr.findViewById(R.id.plantProfPic);
            scientific_name.setText("Scientific Name: " + plantInfo.getName());
            comm_name.setText("Common Name: " + plantInfo.getCommon());
            info = f.findViewById(R.id.infob);
            info.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    ShowPopup(v, plantInfo);
                }
            });
            remindersb = f.findViewById(R.id.remindersb);
            remindersb.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PlantInfoActivity.this, CreateEditReminder.class);

                    startActivity(intent);
                }
            });



            remindersb = f.findViewById(R.id.remindersb);
            plantPic.setImageBitmap(bitmap);


            //plantName.setText("Common Name: " + plantInfo.getName());

            //plantImg.setImageBitmap(bitmap);
            //probability.setText(plantInfo.getProbability());
            //confidence.setText(plantInfo.getConfidence());

            layout.invalidate();
            progress.dismiss();

//            if (!DiseaseActivity.this.isFinishing() && progress != null) {
//                progress.dismiss();
//            }
        }

//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//            progress.dismiss();
//        }
        public void ShowPopup(View v, PlantInfo plantInfo) {
            ImageButton txtclose;
            myDialog.setContentView(R.layout.custom_layout);
            txtclose = myDialog.findViewById(R.id.ib_close);
            wikiInfo = myDialog.findViewById(R.id.wikiView);
            wikiInfo.setWebViewClient(new WebViewClient());
            wikiInfo.loadUrl(wikiURL);
            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }
        @Override
        protected PlantInfo doInBackground(String... params) {

            String path = params[0];
            PlantPostJson plantPostJson = new PlantPostJson();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapUtil.getBitmap(path);
            //Bitmap bitmap = BitmapFactory.decodeStream(getClass().getResourceAsStream("/res/drawable/plant1.jpg"));

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);

            Client client = ClientBuilder.newClient();

            // Put the encoded image into post Json
            plantPostJson.setIdentJson(encoded);
            Entity plantImage = Entity.json(plantPostJson.getIdentJson());

            Response response = client.target(identifyURL)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(plantImage);

            // Get the reference ID for requesting suggestions
            String entity = response.readEntity(String.class);
            String[] parts = entity.split(",");
            String id_chaos = parts[0];
            String[] parts2 = id_chaos.split(" ");
            String id = parts2[1];

            // Pause for a while to wait for the analyzing result (suggestions)
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                return null;
            }

            // Put the reference ID into post Json
            plantPostJson.setSuggeJson(id);
            Entity resultId = Entity.json(plantPostJson.getSuggeJson());
            Response suggestion = client.target(suggestionURL)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(resultId);

            // Get all the results
            String result = suggestion.readEntity(String.class);
            int suggestionStart = result.indexOf("suggestion");
            int nameStart = result.indexOf("name", suggestionStart + 1);
            int nameEnd = result.indexOf(",", nameStart + 1);
            String name = result.substring(nameStart + 8, nameEnd - 1);
//            Log.d("!!!id", id);

            int probabilityStart = result.indexOf("probability");
            int probabilityEnd = result.indexOf(",", probabilityStart + 1);
            String probability = result.substring(probabilityStart + 14, probabilityEnd);

            int confidenceStart = result.indexOf("confidence");
            int confidenceEnd = result.indexOf(",", confidenceStart + 1);
            String confidence = result.substring(confidenceStart + 13, confidenceEnd);

            String name_wiki = name.replaceAll(" ", "_");

            wikiURL += name_wiki;
            Log.d("wiki", wikiURL);
            try {
                Document doc = Jsoup.connect(wikiURL).get();
                plantInfo = new PlantInfo(parseHtml(doc));
            } catch (IOException ioe){
                return null;
            }

            plantInfo.setPath(path);
            plantInfo.setName(name);
            plantInfo.setProbability(probability);
            plantInfo.setConfidence(confidence);

            //plantInfo = new PlantInfo(path, name, probability, confidence);
//            Log.d("!!!!!!status", status_str);
//            Log.d("!!!!!!!!!!response", result);




            Response response1 = client.target(plantURL + name).request().get();
            String r = response1.readEntity(String.class);
            String comm = null;
            try {

                JSONArray obj = new JSONArray(r);
                for(int i=0;i<obj.length();i++)
                {
                    JSONObject jsonObject1 = obj.getJSONObject(i);
                    if(comm == null) {

                        comm = jsonObject1.optString("common_name");
                        comm.replace("_", " ");
                    }
                    else {
                        String holder = jsonObject1.optString("common_name");
                        if (comm.length() > holder.length()){
                            comm = holder;
                            comm.replace("_", " ");
                        }
                    }

                }
                Log.d("My App", obj.toString());

            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + r + "\"");
            }
            plantInfo.setCommon(comm);
            return plantInfo;
        }
    }

    private PlantInfo parseHtml(Document document) {
        Elements elements = document.select("table[class=infobox biota] > tbody > tr");
        PlantInfo plantInfo = new PlantInfo();
        for (Element element : elements) {
            if (element.text().contains("Family:")){
                int start = element.text().indexOf(":");
                String family = element.text().substring(start + 2);
                plantInfo.setFamily(family);
//                Log.d("!!!", family);
            }
            if (element.text().contains("Genus:")){
                int start = element.text().indexOf(":");
                String genus = element.text().substring(start + 2);
                plantInfo.setGenus(genus);
//                Log.d("!!!", genus);
            }
            if (element.text().contains("Species:")){
                int start = element.text().indexOf(":");
                String species = element.text().substring(start + 2);
                plantInfo.setSpecies(species);
//                Log.d("!!!", species);
            }
        }

        return plantInfo;
    }
}
