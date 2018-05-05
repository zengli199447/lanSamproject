package com.example.administrator.landapplication.server;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.example.administrator.landapplication.bean.LocationContentDataBean;
import com.example.administrator.landapplication.global.AppConfiguration;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.model.rxtools.RxBus;
import com.example.administrator.landapplication.ui.dialog.ShowDialog;
import com.example.administrator.landapplication.utils.LogUtil;
import com.example.administrator.landapplication.utils.SystemUtil;
import com.example.administrator.landapplication.utils.ToastUtil;

import java.io.File;
import java.util.List;


/**
 * Created by Administrator on 2018/4/2.
 */

public class VersionUpService extends Service {

    private String TAG = "VersionUpService";
    private ShowDialog showDialog;
    private String loadUrl;
    private long upLoadId;
    private DownloadManager downloadManager;
    private String file;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        loadUrl = intent.getStringExtra("LoadUrl");
        initUpload();
        return super.onStartCommand(intent, flags, startId);
    }

    private void initUpload() {
        file = AppConfiguration.VersionPackage();
        //创建下载任务,downloadUrl就是下载链接
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(loadUrl));
        //指定下载路径和下载文件名
        request.setDestinationInExternalPublicDir(file, "土地资源管理");
        //获取下载管理器
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        //将下载任务加入下载队列，否则不会进行下载
        upLoadId = downloadManager.enqueue(request);
        //注册广播接收者，监听下载状态
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkDownloadStatus();//检查下载状态
        }
    };

    //检查下载状态
    private void checkDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(upLoadId);//筛选下载任务，传入任务ID，可变参数
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED: //下载暂停

                case DownloadManager.STATUS_PENDING: //下载延迟

                case DownloadManager.STATUS_RUNNING: //正在下载

                    break;
                case DownloadManager.STATUS_SUCCESSFUL: //下载完成
                    RxBus.getDefault().post(new CommonEvent(EventCode.VERSION_UP_SUCCESSFUL));
                    installAPK(new File(file));
                    LogUtil.e(TAG,"STATUS_SUCCESSFUL");
                    break;
                case DownloadManager.STATUS_FAILED: //下载失败
                    RxBus.getDefault().post(new CommonEvent(EventCode.VERSION_UP_FAILED));
                    LogUtil.e(TAG,"STATUS_FAILED");
                    break;
            }
        }
    }

    protected void installAPK(File file) {
        if (!file.exists()) return;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("file://" + file.toString());
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
