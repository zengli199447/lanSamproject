package com.example.administrator.landapplication.widget;

import android.content.Context;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polygon;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2018/4/3.
 * 地图扩展
 */

public class LocationGeoHasher {

    private BaiduMap mBaiduMap;
    private Context context;
    private Polyline mPolyline;
    private Polygon polygon;
    private List<Marker> markers;
    private List<String> ids;
    private Map<String, LatLng> latlngs;

    private static String BASE32 = "0123456789bcdefghjkmnpqrstuvwxyz";

    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static String convertToString(char[] a) {
        String s = "";
        for (int i = 0; i < a.length; i++) {
            s += a[i];
        }
        return s;
    }

    public LocationGeoHasher() {
    }

    public LocationGeoHasher(BaiduMap mBaiduMap, Context context, Polyline mPolyline, Polygon polygon, List<Marker> markers, List<String> ids, Map<String, LatLng> latlngs) {
        this.mBaiduMap = mBaiduMap;
        this.context = context;
        this.mPolyline = mPolyline;
        this.markers = markers;
        this.ids = ids;
        this.latlngs = latlngs;
        this.polygon = polygon;
    }

    public double GetDistance(double lat1, double lng1, double lat2,
                              double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        return s;
    }

    public String encode_geohash(double latitude, double longitude,
                                 int precision) {
        char[] geohash = new char[precision + 1];
        boolean is_even = true;
        int i = 0;
        double[] lat = new double[2];
        double[] lon = new double[2];
        double mid;
        char bits[] = {16, 8, 4, 2, 1};
        int bit = 0, ch = 0;
        lat[0] = -90.0;
        lat[1] = 90.0;
        lon[0] = -180.0;
        lon[1] = 180.0;
        while (i < precision) {
            if (is_even) {
                mid = (lon[0] + lon[1]) / 2;
                if (longitude > mid) {
                    ch |= bits[bit];
                    lon[0] = mid;
                } else
                    lon[1] = mid;
            } else {
                mid = (lat[0] + lat[1]) / 2;
                if (latitude > mid) {
                    ch |= bits[bit];
                    lat[0] = mid;
                } else
                    lat[1] = mid;
            }
            is_even = !is_even;
            if (bit < 4)
                bit++;
            else {
                geohash[i++] = BASE32.charAt(ch);
                bit = 0;
                ch = 0;
            }
        }
        geohash[i] = 0;
        String s = "";
        for (i = 0; i < geohash.length; i++)
            s += geohash[i];
        return s;
    }

    public String[] expand(String geoStr) {
        String eastNeighbour = getEastNeighbour(geoStr);
        String westNeighbour = getWestNeighbour(geoStr);
        String northNeighbour = getNorthNeibour(geoStr);
        String southNeighbour = getSouthNeibour(geoStr);
        String[] expandGeoStr = {geoStr, eastNeighbour, westNeighbour,
                northNeighbour, southNeighbour, getNorthNeibour(westNeighbour),
                getNorthNeibour(eastNeighbour), getSouthNeibour(westNeighbour),
                getSouthNeibour(eastNeighbour)};
        return expandGeoStr;
    }

    public String getEastNeighbour(String geoStr) {
        Map<String, Object> map = extractLonLatFromGeoStr(geoStr);
        long lon = (Long) map.get("lon") + 1;
        return getGeoStrFrom(lon, (String) map.get("latBitStr"), true);
    }

    public String getWestNeighbour(String geoStr) {
        Map<String, Object> map = extractLonLatFromGeoStr(geoStr);
        long lon = (Long) map.get("lon") - 1;
        return getGeoStrFrom(lon, (String) map.get("latBitStr"), true);
    }

    public String getNorthNeibour(String geoStr) {
        Map<String, Object> map = extractLonLatFromGeoStr(geoStr);
        long lat = (Long) map.get("lat") + 1;
        return getGeoStrFrom(lat, (String) map.get("lonBitStr"), false);
    }

    public String getSouthNeibour(String geoStr) {
        Map<String, Object> map = extractLonLatFromGeoStr(geoStr);
        long lat = (Long) map.get("lat") - 1;
        return getGeoStrFrom(lat, (String) map.get("lonBitStr"), false);
    }

    public Map<String, Object> extractLonLatFromGeoStr(String geoStr) {
        boolean is_even = true;
        char bits[] = {16, 8, 4, 2, 1};
        int bit = 0, ch = 0;
        int geoIdx;
        String lonBitStr = "";
        String latBitStr = "";
        long lon = 0;
        long lat = 0;
        for (int i = 0; i < geoStr.length(); i++) {
            geoIdx = BASE32.indexOf(geoStr.charAt(i));
            for (bit = 0; bit < 5; bit++) {
                ch = geoIdx & bits[bit];
                if (is_even) {
                    if (ch != 0) {
                        lonBitStr += "1";
                        lon = lon * 2 + 1;
                    } else {
                        lonBitStr += "0";
                        lon = lon * 2;
                    }
                } else {
                    if (ch != 0) {
                        latBitStr += "1";
                        lat = lat * 2 + 1;
                    } else {
                        latBitStr += "0";
                        lat = lat * 2;
                    }
                }
                is_even = !is_even;
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lonBitStr", lonBitStr);
        map.put("latBitStr", latBitStr);
        map.put("lat", lat);
        map.put("lon", lon);
        return map;
    }

    public String getGeoStrFrom(long lonOrLat, String lonOrLatStr, boolean isLon) {
        String lonBitStr = "";
        String latBitStr = "";
        if (isLon) {
            lonBitStr = Long.toBinaryString(lonOrLat);
            latBitStr = lonOrLatStr;
        } else {
            latBitStr = Long.toBinaryString(lonOrLat);
            lonBitStr = lonOrLatStr;
        }
        boolean is_even = true;
        String geoStr = "";
        int ch, bit;
        int geoStrLength = (lonBitStr.length() + latBitStr.length()) / 5;
        for (int i = 0; i < lonBitStr.length(); ) {
            ch = 0;
            for (bit = 0; bit < 5; bit++) {
                if (is_even)
                    ch = ch * 2 + lonBitStr.charAt(i) - '0';
                else {
                    if (i < latBitStr.length())
                        ch = ch * 2 + latBitStr.charAt(i) - '0';
                    else
                        bit--;
                    i++;
                }
                is_even = !is_even;
            }
            geoStr += BASE32.charAt(ch);
            if (geoStr.length() == geoStrLength)
                return geoStr;
        }
        return geoStr;
    }

    //两点距离计算
    public static boolean getDistance(double longitudeRules, double latitudeRules, double longitudeCurrent, double latitudeCurrent, int signArea, ToastUtil toastUtil) {
        // 维度
        double lat1 = (Math.PI / 180) * latitudeRules;
        double lat2 = (Math.PI / 180) * latitudeCurrent;
        // 经度
        double lon1 = (Math.PI / 180) * longitudeRules;
        double lon2 = (Math.PI / 180) * longitudeCurrent;
        // 地球半径
        double R = 6371;
        // 两点间距离 km，如果想要米的话，结果*1000就可以了
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;
        if (d * 1000 < signArea) {
            return true;
        } else {
            toastUtil.showToast("未在规定范围内，无法签到！");
            return false;
        }
    }

    //marker 添加
    public void addMarker(double latitude, double longitude) {
        LatLng point = new LatLng(latitude, longitude);
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_tag);
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap).draggable(true);   //.zIndex(9)
        Marker marker = (Marker) mBaiduMap.addOverlay(option);
        markers.add(marker);
        String id = marker.getId();
        latlngs.put(id, new LatLng(latitude, longitude));
        ids.add(id);
    }

    //线
    public void drawLine() {
        List<LatLng> points = new ArrayList<>();
        if (mPolyline != null) {
            mPolyline.remove();
        }
        for (int i = 0; i < ids.size(); i++) {
            LatLng latLng = latlngs.get(ids.get(i));
            points.add(latLng);
        }
        OverlayOptions ooPolyline = new PolylineOptions().width(6).color(context.getResources().getColor(R.color.green_style)).points(points);
        mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
    }

    //面
    public void drawPolygon() {
        if (polygon != null) {
            polygon.remove();
        }
        List<LatLng> pts = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            LatLng latLng = latlngs.get(ids.get(i));
            pts.add(latLng);
        }
        OverlayOptions ooPolygon = new PolygonOptions().points(pts).stroke(new Stroke(2, context.getResources().getColor(R.color.red_style))).fillColor(context.getResources().getColor(R.color.green_min_t));
        polygon = (Polygon) mBaiduMap.addOverlay(ooPolygon);
    }


}
