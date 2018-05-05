package com.example.administrator.landapplication.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.example.administrator.landapplication.bean.LocationContentDataBean;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.model.rxtools.RxBus;
import com.example.administrator.landapplication.utils.LogUtil;

import java.util.List;


/**
 * Created by Administrator on 2018/4/2.
 */

public class SingBaiDuService extends Service implements BDLocationListener {

    private LocationClient mLocClient;
    private String TAG = "SingBaiDuService";
    private double latitude;
    private double longitude;
    private float radius;
    private String street;
    private String addrStr;
    private String locationDescribe;
    private List<Poi> poiList;
    private LocationContentDataBean locationContentDataBean;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initLocation();
        return super.onStartCommand(intent, flags, startId);
    }

    private void initLocation() {
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(this);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedLocationPoiList(true);  //可选，是否需要周边POI信息，默认为不需要，即参数为false
        option.setOpenGps(true); // 打开gps
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(2000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }


    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation == null) {
            return;
        }
        latitude = bdLocation.getLatitude();
        longitude = bdLocation.getLongitude();
        radius = bdLocation.getRadius();
        street = bdLocation.getStreet();
        addrStr = bdLocation.getAddrStr();
        locationDescribe = bdLocation.getLocationDescribe();
        poiList = bdLocation.getPoiList();
        LocationContentDataListener();
//        LogUtil.e(TAG, "street : " + street);
//        LogUtil.e(TAG, "addrStr : " + addrStr);
//        LogUtil.e(TAG, "locationDescribe : " + locationDescribe);
//        LogUtil.e(TAG, "latitude : " + latitude + "   " + "longitude : " + longitude);
    }

    public void LocationContentDataListener() {
        locationContentDataBean = new LocationContentDataBean(latitude, longitude, radius, street, addrStr, locationDescribe, poiList);
        RxBus.getDefault().post(new CommonEvent(EventCode.LOCATION_NOTICE, locationContentDataBean));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (mLocClient != null)
            mLocClient.unRegisterLocationListener(this);
        mLocClient.stop();
        super.onDestroy();

    }


}
