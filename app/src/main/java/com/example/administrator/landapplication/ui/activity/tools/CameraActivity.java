package com.example.administrator.landapplication.ui.activity.tools;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.ErrorListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.cjt2325.cameralibrary.util.DeviceUtil;
import com.cjt2325.cameralibrary.util.FileUtil;
import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.model.rxtools.RxBus;
import com.example.administrator.landapplication.utils.LogUtil;
import com.yanzhenjie.mediascanner.MediaScanner;

import java.io.File;


/**
 * Created by Administrator on 2018/3/22.
 */

public class CameraActivity extends AppCompatActivity {
    private String TAG = "CameraActivity";
    private JCameraView jCameraView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_camera);
        jCameraView = (JCameraView) findViewById(R.id.jcameraview);
        //设置视频保存路径 Environment.getExternalStorageDirectory().getPath() + File.separator + "JCamera"
        String CacheFile = Environment.getExternalStorageDirectory().getPath() + File.separator + "JCamera";
        jCameraView.setSaveVideoPath(CacheFile);
        jCameraView.setFeatures(JCameraView.BUTTON_STATE_ONLY_CAPTURE);
        jCameraView.setTip("");
        jCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);
        jCameraView.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                //错误监听
                Log.e(TAG, "camera error");
                Intent intent = new Intent();
                setResult(103, intent);
                finish();
            }

            @Override
            public void AudioPermissionError() {
                Toast.makeText(CameraActivity.this, "给点录音权限可以?", Toast.LENGTH_SHORT).show();
            }
        });

        jCameraView.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                String path = FileUtil.saveBitmap("JCamera", bitmap);
                MediaScanner mediaScanner = new MediaScanner(CameraActivity.this); //每次拍照完必须扫描一次
                mediaScanner.scan(path);
                Intent intent = new Intent();
                intent.putExtra("picturePath", path);
                LogUtil.e(TAG,"picturePath : " + path);
                setResult(RESULT_OK, intent);
                ToRecevier();
                finish();
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                //获取视频路径
                String path = FileUtil.saveBitmap("JCamera", firstFrame);
                Log.i(TAG, "url = " + url + ", Bitmap = " + path);
                Intent intent = new Intent();
                intent.putExtra("videoPath", path);
                setResult(RESULT_OK, intent);
                ToRecevier();
                finish();
            }
        });

        jCameraView.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                CameraActivity.this.finish();
            }
        });

        jCameraView.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
                RxBus.getDefault().post(new CommonEvent(EventCode.OPEN_PICTURE));
                CameraActivity.this.finish();
            }
        });
        Log.i(TAG, DeviceUtil.getDeviceModel());
    }

    @Override
    protected void onStart() {
        super.onStart();
        //全屏显示
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        jCameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        jCameraView.onPause();
    }

    private void ToRecevier() {
        Intent intent = new Intent();
        intent.setAction("com.broadcast.camera");
        intent.putExtra("CameraStatus", "finish");
        //发送广播
        sendBroadcast(intent);
    }

}
