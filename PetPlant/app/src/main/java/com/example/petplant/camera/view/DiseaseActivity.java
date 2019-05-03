package com.example.petplant.camera.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
                    progress.setTitle("Analyzing");
                    progress.setMessage("Please wait");

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

    private class analyzeTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }

        @Override
        protected void onPostExecute(String path) {
            super.onPostExecute(path);
            diseaseImg.setImageBitmap(BitmapUtil.getBitmap(path));
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
