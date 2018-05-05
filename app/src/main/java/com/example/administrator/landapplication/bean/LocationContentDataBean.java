package com.example.administrator.landapplication.bean;

import com.baidu.location.Poi;

import java.util.List;

/**
 * Created by Administrator on 2018/4/2.
 * poi搜索实体
 */

public class LocationContentDataBean {
    private double latitude;
    private double longitude;
    private float radius;
    private String street;
    private String addrStr;
    private String locationDescribe;
    private List<Poi> poiList;

    public LocationContentDataBean(double latitude, double longitude, float radius, String street, String addrStr, String locationDescribe, List<Poi> poiList) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.street = street;
        this.addrStr = addrStr;
        this.locationDescribe = locationDescribe;
        this.poiList = poiList;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddrStr() {
        return addrStr;
    }

    public void setAddrStr(String addrStr) {
        this.addrStr = addrStr;
    }

    public String getLocationDescribe() {
        return locationDescribe;
    }

    public void setLocationDescribe(String locationDescribe) {
        this.locationDescribe = locationDescribe;
    }

    public List<Poi> getPoiList() {
        return poiList;
    }

    public void setPoiList(List<Poi> poiList) {
        this.poiList = poiList;
    }
}
