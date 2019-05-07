package com.example.petplant.addplant;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import com.example.petplant.R;
import com.example.petplant.camera.util.BitmapUtil;
import com.example.petplant.camera.view.DiseaseActivity;
import com.example.petplant.camera.view.TakePhotoActivity;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class PlantInfoActivity extends AppCompatActivity {
    public View img;
    private ImageView plantImg;
    private TextView plantName;
    private TextView probability;
    private TextView confidence;
    private ProgressDialog progress;
    private AsyncTask<String, Void, PlantInfo> analyze = new PlantInfoActivity.analyzeTask();
    private PlantInfo plantInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_info);
//        if(savedInstanceState == null) {
//            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//            fragmentTransaction.add(R.id.fragment_container, new MainFragment(), "MainFragment");
//            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//            fragmentTransaction.commit();
//        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        plantImg = (ImageView) findViewById(R.id.plant_img);
        plantName = (TextView) findViewById(R.id.plant_name);
        probability = (TextView) findViewById(R.id.probability);
        confidence = (TextView) findViewById(R.id.confidence);

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

    private Bitmap createCircleBitmap(Bitmap resource)
    {
        //获取图片的宽度
        int width = resource.getWidth();
        Paint paint = new Paint();
        //设置抗锯齿
        paint.setAntiAlias(true);

        //创建一个与原bitmap一样宽度的正方形bitmap
        Bitmap circleBitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        //以该bitmap为低创建一块画布
        Canvas canvas = new Canvas(circleBitmap);
        //以（width/2, width/2）为圆心，width/2为半径画一个圆
        canvas.drawCircle(width/2, width/2, width/2, paint);

        //设置画笔为取交集模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //裁剪图片
        canvas.drawBitmap(resource, 0, 0, paint);

        return circleBitmap;
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
            plantImg.setImageBitmap(createCircleBitmap(bitmap));
            plantName.setText(plantInfo.getName());
            probability.setText(plantInfo.getProbability());
            confidence.setText(plantInfo.getConfidence());
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

        @Override
        protected PlantInfo doInBackground(String... params) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapUtil.getBitmap(params[0]);

//            Bitmap bitmap = BitmapFactory.decodeStream(getClass().getResourceAsStream("/res/drawable/plant1.jpg"));

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);

            Client client = ClientBuilder.newClient();
            Entity plantImage = Entity.json("{" +
                    "\"key\": \"Tc2uLB6qixr1IfRQGbkCAmNJjvuHnvAl9agxGpDT1fbkOM74lc\"," +
                    "\"images\": [" + "\"" + encoded + "\"" + "]}");

            Response response = client.target("https://api.plant.id/identify")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(plantImage);

            String entity = response.readEntity(String.class);
            String[] parts = entity.split(",");
            String id_chaos = parts[0];
            String[] parts2 = id_chaos.split(" ");
            String id = parts2[1];

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                return null;
            }

            Entity resultId = Entity.json("{" +
                    "\"key\": \"Tc2uLB6qixr1IfRQGbkCAmNJjvuHnvAl9agxGpDT1fbkOM74lc\", " +
                    "\"ids\": [" + id + "]}");
            Response suggestion = client.target("https://private-anon-79238c3314-plantid.apiary-proxy.com/check_identifications")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(resultId);

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

            plantInfo = new PlantInfo(params[0], name, probability, confidence);
//            Log.d("!!!!!!status", status_str);
//            Log.d("!!!!!!!!!!response", result);
            return plantInfo;
        }
    }
}
