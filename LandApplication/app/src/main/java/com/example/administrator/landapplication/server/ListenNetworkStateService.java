package com.example.administrator.landapplication.server;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.widget.Toast;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.utils.LogUtil;


/**
 * Created by Administrator on 2018/4/2.
 */

public class ListenNetworkStateService extends Service {
    private ConnectivityManager connectivityManager;
    private NetworkInfo info;
    boolean networkUnavailable = false;
    boolean networkChanged = false;
    String lastNetworkName = "";
    long networkDisconnectStart = 0;
    long networkDisconnectEnd = 0;
    private final static String TAG = "ListenNetworkStateService";

    public static final String FORCE_RECONNECT_ACTION = "com.example.administrator.myapplication.USER_RECONNECT";

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                LogUtil.d(TAG, "网络状态已经改变");
                connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                info = connectivityManager.getActiveNetworkInfo();

                if (info != null && info.isAvailable()) {
                    String name = info.getExtraInfo();
                    LogUtil.d(TAG, "当前网络名称：" + name);
                    if (name != null && lastNetworkName != null) {
                        networkChanged = !(lastNetworkName.equals(name));
                        lastNetworkName = name;
                    }
                    networkDisconnectEnd = System.currentTimeMillis();
                } else {
                    LogUtil.d(TAG, "没有可用网络");
                    Toast.makeText(ListenNetworkStateService.this, R.string.network_unavailable, Toast.LENGTH_SHORT).show();
                    networkUnavailable = true;
                    networkDisconnectStart = System.currentTimeMillis();
                }
            } else if (action.equals(FORCE_RECONNECT_ACTION)) {
                LogUtil.e(TAG, "Receive broadcast FORCE_RECONNECT_ACTION");
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mFilter.addAction(FORCE_RECONNECT_ACTION);
        registerReceiver(mReceiver, mFilter);

        LogUtil.e(TAG, "ListenNetworkStateService  onCreate");
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        info = connectivityManager.getActiveNetworkInfo();

        if (info != null && info.isAvailable()) {
            lastNetworkName = info.getExtraInfo();
            LogUtil.e(TAG, "上次网络 ： " + lastNetworkName);
        }
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        LogUtil.e(TAG, "ListenNetworkStateService  onDestroy");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
