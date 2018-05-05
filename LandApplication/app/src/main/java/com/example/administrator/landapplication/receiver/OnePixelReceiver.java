package com.example.administrator.landapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.landapplication.ui.activity.tools.OnePiexlActivity;
import com.example.administrator.landapplication.utils.LogUtil;


/**
 * Created by Administrator on 2018/4/8.
 */

public class OnePixelReceiver extends BroadcastReceiver {
    String TAG = "OnePixelReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {    //屏幕关闭启动1像素Activity
            Intent it = new Intent(context, OnePiexlActivity.class);
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(it);
            LogUtil.i(TAG, "屏幕关闭 启动1像素Activity");
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {   //屏幕打开 结束1像素
            context.sendBroadcast(new Intent("finish"));
            Intent main = new Intent(Intent.ACTION_MAIN);
            main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            main.addCategory(Intent.CATEGORY_HOME);
            context.startActivity(main);
            LogUtil.i(TAG, "屏幕打开 结束1像素");
        }
    }

}