package com.example.administrator.landapplication.ui.activity.tools;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polygon;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.SpatialRelationUtil;
import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.bean.LocationContentDataBean;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.model.rxtools.RxBus;
import com.example.administrator.landapplication.ui.dialog.ShowDialog;
import com.example.administrator.landapplication.utils.LogUtil;
import com.example.administrator.landapplication.utils.SystemUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/12.
 * 围栏  注：当前非次此项目中应用
 */

public class LocationScopeDetectionActivity extends BaseActivity implements View.OnClickListener, BaiduMap.OnMapClickListener, BaiduMap.OnMarkerClickListener, BaiduMap.OnMarkerDragListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.title_about)
    TextView title_about;
    @BindView(R.id.map_view)
    MapView map_view;
    private LocationContentDataBean locationContentDataBean;
    private SensorManager mSensorManager;
    private BaiduMap mBaiduMap;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private Marker marker;
    private List<Marker> markers = new ArrayList<>();
    private List<String> ids = new ArrayList<>();
    private Map<String, LatLng> latlngs = new HashMap<>();
    private InfoWindow mInfoWindow;
    private Polyline mPolyline;
    private Polygon polygon;
    private double latitude, longitude, la, lo;
    private int size;
    private Map<String, Polygon> polygonMap = new HashMap<>();
    private List<String> aliasname = new ArrayList<>();
    private List<String> areas = new ArrayList<>();
    private ShowDialog showDialog;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.LOCATION_NOTICE:
                locationContentDataBean = (LocationContentDataBean) commonevent.getObject();
                initBaiDuLocation();
                break;
            case EventCode.INPUT_AREA:
                drawSurface(commonevent.getTemp_str());
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_location_socpe;
    }

    @Override
    protected void initClass() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);// 获取传感器管理服务
        showDialog = ShowDialog.newInstance();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        img_btn_black.setVisibility(View.VISIBLE);
        title_about.setText(getString(R.string.draw_line));
        title_name.setText(getString(R.string.trajectory_fence));
        initBaiDuConfiguration();
    }

    @Override
    protected void initListener() {
        title_about.setOnClickListener(this);
        img_btn_black.setOnClickListener(this);
        mBaiduMap.setOnMapClickListener(this);
        mBaiduMap.setOnMarkerClickListener(this);
        mBaiduMap.setOnMarkerDragListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_about:
                showDialog.showSuperInputDialog(this, EventCode.INPUT_AREA, getString(R.string.area_name), getString(R.string.area_name));
                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        latitude = latLng.latitude;
        longitude = latLng.longitude;
        LogUtil.e(TAG, "latitude : " + latitude + "   " + "longitude : " + longitude);
        addMarker(latitude, longitude);
        if (ids.size() >= 2)
            drawLine();
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    //地图配置
    private void initBaiDuConfiguration() {
        mBaiduMap = map_view.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.overlook(0);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));
    }

    //实时定位
    private void initBaiDuLocation() {
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(locationContentDataBean.getRadius())
                .direction(mCurrentDirection).latitude(locationContentDataBean.getLatitude())
                .longitude(locationContentDataBean.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
        polygonContainsPoint();
    }

    //罗盘仪
    @Override
    public void onSensorChanged(SensorEvent event) {
        super.onSensorChanged(event);
        double x = event.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0 && locationContentDataBean != null) {
            mCurrentDirection = (int) x;
            // 此处设置开发者获取到的方向信息，顺时针0-360
            MyLocationData build = new MyLocationData.Builder()
                    .accuracy(locationContentDataBean.getRadius())
                    .direction(mCurrentDirection)
                    .latitude(locationContentDataBean.getLatitude())
                    .longitude(locationContentDataBean.getLongitude()).build();
            mBaiduMap.setMyLocationData(build);
        }
        lastX = x;
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        Button button = new Button(getApplicationContext());
        button.setBackgroundResource(R.drawable.corners_edittext);
        button.setText("是否删除 ？");
        button.setTextColor(Color.BLACK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                marker.remove();
                String id = marker.getId();
                ids.remove(id);
                latlngs.remove(id);
                mBaiduMap.hideInfoWindow();
                if (ids.size() < 2 && mPolyline != null) {
                    mPolyline.remove();
                    return;
                }
                drawLine();
            }
        });
        LatLng latLng = marker.getPosition();
        mInfoWindow = new InfoWindow(button, latLng, -50);
        mBaiduMap.showInfoWindow(mInfoWindow);
        return true;
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        latlngs.remove(marker.getId());
        latlngs.put(marker.getId(), new LatLng(marker.getPosition().latitude, marker.getPosition().longitude));
        drawLine();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    //marker 添加
    private void addMarker(double latitude, double longitude) {
        LatLng point = new LatLng(latitude, longitude);
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_tag);
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap).draggable(true);   //.zIndex(9)
        marker = (Marker) mBaiduMap.addOverlay(option);
        markers.add(marker);
        String id = marker.getId();
        latlngs.put(id, new LatLng(latitude, longitude));
        ids.add(id);
    }

    //线
    private void drawLine() {
        List<LatLng> points = new ArrayList<>();
        if (mPolyline != null) {
            mPolyline.remove();
        }
        for (int i = 0; i < ids.size(); i++) {
            LatLng latLng = latlngs.get(ids.get(i));
            points.add(latLng);
        }
        OverlayOptions ooPolyline = new PolylineOptions().width(6).color(getResources().getColor(R.color.green_style)).points(points);
        mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
    }

    //面
    private void drawPolygon() {
        if (polygon != null) {
            polygon.remove();
        }
        List<LatLng> pts = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            LatLng latLng = latlngs.get(ids.get(i));
            pts.add(latLng);
        }
        OverlayOptions ooPolygon = new PolygonOptions().points(pts).stroke(new Stroke(2, getResources().getColor(R.color.red_style))).fillColor(getResources().getColor(R.color.green_min_t));
        polygon = (Polygon) mBaiduMap.addOverlay(ooPolygon);
    }

    //绘制阴影，以及区域名称
    private void drawSurface(final String surfaceName) {
        la = 0;
        lo = 0;
        size = ids.size();
        if (size <= 2 && surfaceName.isEmpty()) {
            toastUtil.showToast("请先选择区域坐标以及输入区域名称");
            return;
        }
        for (int i = 0; i < size; i++) {
            LatLng latLng = latlngs.get(ids.get(i));
            la = la + latLng.latitude;
            lo = lo + latLng.longitude;
        }
        if (surfaceName.isEmpty()) {
            return;
        }
        drawPolygon();
        LatLng llText = new LatLng(la / size, lo / size);
        OverlayOptions ooText = new TextOptions().fontSize(SystemUtil.dp2px(this, 14)).fontColor(getResources().getColor(R.color.white)).text(surfaceName).position(llText);
        mBaiduMap.addOverlay(ooText);
        polygonMap.put(surfaceName, polygon);
        aliasname.add(surfaceName);
        polygon = null;
        for (int j = 0; j < markers.size(); j++) {
            markers.get(j).remove();
        }
        latlngs.clear();
        ids.clear();
    }

    //区域点范围内外
    private void polygonContainsPoint() {
        for (int i = 0; i < aliasname.size(); i++) {
            String name = aliasname.get(aliasname.size() - 1);
            polygon = polygonMap.get(name);
            //判断一个点是否在多边形中
            if (SpatialRelationUtil.isPolygonContainsPoint(polygon.getPoints(), new LatLng(locationContentDataBean.getLatitude(), locationContentDataBean.getLongitude()))) {
                toastUtil.showToast("当前所在区域 ： " + name + "中");
                areas.add(name);
            } else {
                toastUtil.showToast("当前不在区域 :  " + name + "中");
                RxBus.getDefault().post(new CommonEvent(EventCode.OUTSIDE_THE_REGION));
            }
        }
    }

    @Override
    protected void onResume() {
        map_view.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    @Override
    protected void onPause() {
        map_view.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
    }


}
