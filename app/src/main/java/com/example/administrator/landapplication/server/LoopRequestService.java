package com.example.administrator.landapplication.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.administrator.landapplication.model.DataManager;
import com.example.administrator.landapplication.model.db.entity.LoginUserInfo;
import com.example.administrator.landapplication.utils.LogUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/4/28.
 * 离线提交请求
 */

public class LoopRequestService extends Service {

    private String TAG = "LoopRequestService";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
