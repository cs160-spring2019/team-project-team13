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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.os.AsyncTask;
import android.widget.ImageView;

//import javax.ws.rs.client.Client;

import com.example.petplant.R;
import com.example.petplant.camera.util.BitmapUtil;
import com.example.petplant.camera.view.DiseaseActivity;
import com.example.petplant.camera.view.TakePhotoActivity;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.app.Activity.RESULT_OK;

public class PlantInfoActivity extends AppCompatActivity {
    public View img;
    private ImageView plantImg;
    private ProgressDialog progress;
    private AsyncTask<String, Void, String> analyze = new PlantInfoActivity.analyzeTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_info);
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

        //plantImg = (ImageView) findViewById(R.id.plant_img);

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

    private class analyzeTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }

        @Override
        protected void onPostExecute(String path) {
            super.onPostExecute(path);
            Bitmap bitmap = BitmapUtil.getBitmap(path);
            plantImg.setImageBitmap(createCircleBitmap(bitmap));
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
        protected String doInBackground(String... params) {
//            Log.i("Analyzing", "Analyzing..");
            OutputStream out = null;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                return null;
            }
//            try {
//                URL url = new URL("https://api.plant.id/identify");
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                out = new BufferedOutputStream(urlConnection.getOutputStream());
//
//                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
////                writer.write(data);
//                writer.flush();
//                writer.close();
//                out.close();
//            } catch (MalformedURLException male) {
//                return null;
//            } catch (IOException ioe){
//                return null;
//            }
            return params[0];
        }
    }
}
