package com.example.petplant.camera.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petplant.R;
import com.example.petplant.camera.model.PermissionsModel;
import com.example.petplant.camera.util.BitmapUtil;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiseaseActivity extends AppCompatActivity{

    private ImageView back;
    private ImageView diseaseImg;
    private TextView name_content;
    private TextView condition_content;
    private TextView confidence_content;
    private ProgressDialog progress;
    private String url = "http://35.247.41.9:80/";
    private AsyncTask<String, Void, DiseaseInfo> analyze = new DiseaseActivity.analyzeTask();
    private JsonPlaceHoldeApi jsonPlaceHoldeApi;
    private String path;
    private DiseaseInfo diseaseInfo;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disease_info);

        diseaseImg = (ImageView) findViewById(R.id.disease_img);
        name_content = (TextView) findViewById(R.id.name_content);
        condition_content = (TextView) findViewById(R.id.condition_content);
        confidence_content = (TextView) findViewById(R.id.confidence_content);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setItemIconTintList(null);

        toolbar = (Toolbar) findViewById(R.id.toolbarDisease);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Disease Identification");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        PermissionsModel permissionsModel = new PermissionsModel(this);
        permissionsModel.checkCameraPermission(new PermissionsModel.PermissionListener() {
            @Override
            public void onPermission(boolean isPermission) {
                if (isPermission) {
                    Intent intent = new Intent(DiseaseActivity.this, TakePhotoActivity.class);
                    startActivityForResult(intent, TakePhotoActivity.REQUEST_CAPTRUE_CODE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TakePhotoActivity.REQUEST_CAPTRUE_CODE: {
                    path = data.getStringExtra(TakePhotoActivity.RESULT_PHOTO_PATH);
                    String[] paths = {path};

                    progress = new ProgressDialog(DiseaseActivity.this);
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setIndeterminate(false);
                    progress.setCancelable(true);
                    progress.setTitle("Analyzing, please wait...");
                    progress.setMessage("Want faster speed? Join our membership NOW for only $28.88/month!");
                    analyze.execute(paths);

//                    diseaseImg.setImageBitmap(BitmapUtil.getBitmap(path));
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

    private class analyzeTask extends AsyncTask<String, Void, DiseaseInfo> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }

        @Override
        protected void onPostExecute(DiseaseInfo diseaseInfo) {


            Bitmap bitmap = BitmapUtil.getBitmap(path);
            diseaseImg.setImageBitmap(createCircleBitmap(bitmap));

            String string = diseaseInfo.getData();
            String[] parts = string.split("___");
            String name = parts[0];
            String condition = parts[1];

            name_content.setText(name);
            condition_content.setText(condition);
            confidence_content.setText(diseaseInfo.getProb());
            progress.dismiss();
        }

        @Override
        protected DiseaseInfo doInBackground(String... params) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapUtil.getBitmap(params[0]);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(200, TimeUnit.SECONDS)
                    .readTimeout(200, TimeUnit.SECONDS)
                    .writeTimeout(200, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            jsonPlaceHoldeApi = retrofit.create(JsonPlaceHoldeApi.class);
            //PictureQuery pictureQuery = new PictureQuery("data:image/jpeg;base64," + encoded);
            //String request=new Gson().toJson(pictureQuery);
            RequestBody body = RequestBody.create(MediaType.parse("text/plain"), encoded);
            Map<String, RequestBody> PostParams = new HashMap<>();
            PostParams.put("plant_image", body);

            //System.out.println("================");

            Call<DiseaseInfo> call = jsonPlaceHoldeApi.createPost(PostParams);
            call.enqueue(new Callback<DiseaseInfo>() {
                @Override
                public void onResponse(Call<DiseaseInfo> call, Response<DiseaseInfo> response) {

                    if (!response.isSuccessful()) {
                        System.out.println("404");
                        return;
                    }
                    diseaseInfo = (DiseaseInfo) response.body();
//                    System.out.println("error"+ diseaseInfo.getError());
//                    System.out.println("data"+ diseaseInfo.getData());
//                    System.out.println(diseaseInfo.getMessage());
                }

                @Override
                public void onFailure(Call<DiseaseInfo> call, Throwable t) {
                    System.out.println(t);
                }
            });

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return diseaseInfo;
        }
    }
}
