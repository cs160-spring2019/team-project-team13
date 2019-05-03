package com.example.petplant.camera.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.petplant.MainActivity;
import com.example.petplant.R;
import com.example.petplant.camera.model.PermissionsModel;
import com.example.petplant.camera.util.BitmapUtil;

public class DiseaseActivity extends Activity{

    private ImageView back;
    private ImageView diseaseImg;
    private ProgressDialog progress;
    private AsyncTask<String, Void, String> analyze = new DiseaseActivity.analyzeTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disease_info);
        init();

        diseaseImg = (ImageView) findViewById(R.id.disease_img);

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
                    String path = data.getStringExtra(TakePhotoActivity.RESULT_PHOTO_PATH);
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

    private void init(){
        back = (ImageView)findViewById(R.id.back2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
            diseaseImg.setImageBitmap(createCircleBitmap(bitmap));
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
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                return null;
            }
            return params[0];
        }
    }
}
