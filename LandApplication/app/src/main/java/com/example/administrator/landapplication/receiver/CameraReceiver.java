package com.example.administrator.landapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.landapplication.ui.activity.tools.CameraActivity;


/**
 * Created by Administrator on 2018/3/22.
 */

public class CameraReceiver extends BroadcastReceiver {
    //不适用与裁剪二次调联
    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("CameraStatus");
        if (msg != null && msg.contains("open")) {
            Intent i = new Intent(context, CameraActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
        }
    }


}
