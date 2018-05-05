package com.example.administrator.landapplication.ui.activity.tools;

import android.Manifest;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.navisdk.adapter.BNCommonSettingParam;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.bean.LocationContentDataBean;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.ui.activity.tools.location.BNEventHandler;
import com.example.administrator.landapplication.ui.activity.tools.location.DrivingRouteOverlay;
import com.example.administrator.landapplication.ui.activity.tools.location.NavigationActivity;
import com.example.administrator.landapplication.utils.Calculation;
import com.example.administrator.landapplication.utils.LogUtil;
import com.example.administrator.landapplication.utils.SystemUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/12.
 * 导航
 */

public class LocationNavigationActivity extends BaseActivity implements View.OnClickListener, OnGetRoutePlanResultListener, BaiduNaviManager.NavEventListener, BaiduNaviManager.TTSPlayStateListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.title_about)
    TextView title_about;
    @BindView(R.id.map_view)
    MapView map_view;
    @BindView(R.id.navigation)
    AppCompatCheckBox navigation;
    @BindView(R.id.route)
    AppCompatCheckBox route;
    @BindView(R.id.longitude_view)
    TextView longitude_view;
    @BindView(R.id.latitude_view)
    TextView latitude_view;
    @BindView(R.id.distance_view)
    TextView distance_view;
    @BindView(R.id.icon_current)
    View icon_current;
    private LocationContentDataBean locationContentDataBean;
    private BaiduMap mBaiduMap;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private LatLng latLng;
    private String mSDCardPath;
    private static final String APP_FOLDER_NAME = "landapplication";
    private boolean status = true;
    private String authinfo = null;
    private double currentLatitude;
    private double currentLongitude;
    private Double longitude;
    private Double latitude;
    private String distance;
    private final static String authComArr[] = {Manifest.permission.READ_PHONE_STATE};
    private BNRoutePlanNode.CoordinateType mCoordinateType = null;
    private boolean hasInitSuccess = false;
    private boolean hasRequestComAuth = false;
    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    private final static int authComRequestCode = 2;


    private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.LOCATION_NOTICE:
                locationContentDataBean = (LocationContentDataBean) commonevent.getObject();
                initBaiDuLocation();
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_location_navigation;
    }

    @Override
    protected void initClass() {
        if (initDirs()) {
            initNavi();
        }
    }

    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        latitude = 40.76850845379074;
        longitude = 123.76287382022976;
        try {
            longitude = Double.valueOf(extras.get("Longitude").toString());
            latitude = Double.valueOf(extras.get("Latitude").toString());
        } catch (Exception e) {
            LogUtil.e(TAG, "Exception : " + e.toString());
        }
        latLng = new LatLng(latitude, longitude);
    }

    @Override
    protected void initView() {
        img_btn_black.setVisibility(View.VISIBLE);
        title_name.setText(getString(R.string.navigation));
        SystemUtil.textMagicTool(this, longitude_view, getString(R.string.longitude), SystemUtil.textDecimalFormat(longitude));
        SystemUtil.textMagicTool(this, latitude_view, getString(R.string.latitude), SystemUtil.textDecimalFormat(latitude));
        initBaiDuConfiguration();
        initMark();

    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
        navigation.setOnClickListener(this);
        route.setOnClickListener(this);
        icon_current.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.navigation:
                InitListener();
                break;
            case R.id.route:
                setRoute();
                break;
            case R.id.icon_current:
                MapStatus mMapStatus = new MapStatus.Builder().target(new LatLng(currentLatitude, currentLatitude)).zoom(20).build();
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                mBaiduMap.setMapStatus(mMapStatusUpdate);
                break;
        }
    }

    //地图配置
    private void initBaiDuConfiguration() {
        mBaiduMap = map_view.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        View child = map_view.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls))
            child.setVisibility(View.INVISIBLE);
        map_view.showScaleControl(false);
        map_view.showZoomControls(false);
    }

    //目标Mark
    private void initMark() {
        MarkSetting(latLng, R.drawable.ic_map_red_tag, true);
        MapStatus mMapStatus = new MapStatus.Builder().target(latLng).zoom(13).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }

    //实时定位
    private void initBaiDuLocation() {
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(locationContentDataBean.getRadius())
                .direction(mCurrentDirection).latitude(locationContentDataBean.getLatitude())
                .longitude(locationContentDataBean.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
        currentLatitude = locationContentDataBean.getLatitude();
        currentLongitude = locationContentDataBean.getLongitude();
        LatLng latLng = new LatLng(locationContentDataBean.getLatitude(), locationContentDataBean.getLongitude());
        MarkSetting(latLng, R.drawable.ic_map_tag, status);
        status = false;
        distance = Calculation.getLatLngDistance(this.latLng, latLng);
        SystemUtil.textMagicToolOrColor(this, distance_view, getString(R.string.distance), new StringBuffer().
                append(distance).append(" km").toString(), R.color.green_style, R.color.green_style);
    }

    private void MarkSetting(LatLng latLng, int id, boolean status) {
        if (status) {
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(id);
            OverlayOptions option = new MarkerOptions().position(latLng).icon(bitmap).draggable(true);
            mBaiduMap.addOverlay(option);
        }
    }

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    //初始化导航
    private void initNavi() {
        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {
                if (0 == status) {
                    authinfo = "key校验成功!";
                } else {
                    authinfo = "key校验失败, " + msg;
                }
                Log.e("TEMMAP", authinfo.toString());
            }

            public void initSuccess() {
                Toast.makeText(LocationNavigationActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                hasInitSuccess = true;
                initSetting();
            }

            public void initStart() {
                Toast.makeText(LocationNavigationActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
            }

            public void initFailed() {
                Toast.makeText(LocationNavigationActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
            }

        }, null, ttsHandler, this);

    }

    //导航配置
    private void initSetting() {
        BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
        BNaviSettingManager.setIsAutoQuitWhenArrived(true);
        Bundle bundle = new Bundle();
        bundle.putString(BNCommonSettingParam.TTS_APP_ID, "11103291");
        BNaviSettingManager.setNaviSdkParam(bundle);
    }

    //设置路径规划
    private void setRoute() {
        RoutePlanSearch mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
        LatLng point = new LatLng(currentLatitude, currentLongitude);
        LatLng point1 = new LatLng(latitude, longitude);
        PlanNode stNode = PlanNode.withLocation(point);
        PlanNode enNode = PlanNode.withLocation(point1);
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode)
                .to(enNode));
        mSearch.destroy();
    }

    private void InitListener() {
        if (BaiduNaviManager.isNaviInited()) {
            routeplanToNavi(BNRoutePlanNode.CoordinateType.BD09_MC);
        }
    }

    private void routeplanToNavi(BNRoutePlanNode.CoordinateType coType) {
        mCoordinateType = coType;
        if (!hasInitSuccess) {
            toastUtil.showToast("还未初始化");
        }
        BNRoutePlanNode sNode = null;
        BNRoutePlanNode eNode = null;
        BDLocation bd = new BDLocation();
        bd.setLongitude(currentLongitude);
        bd.setLatitude(currentLatitude);
        double sX = LocationClient.getBDLocationInCoorType(bd, BDLocation.BDLOCATION_BD09LL_TO_GCJ02).getLongitude();
        double sY = LocationClient.getBDLocationInCoorType(bd, BDLocation.BDLOCATION_BD09LL_TO_GCJ02).getLatitude();
        sNode = new BNRoutePlanNode(sX, sY, "", null, BNRoutePlanNode.CoordinateType.GCJ02);
        bd.setLongitude(longitude);
        bd.setLatitude(latitude);
        sX = LocationClient.getBDLocationInCoorType(bd, BDLocation.BDLOCATION_BD09LL_TO_GCJ02).getLongitude();
        sY = LocationClient.getBDLocationInCoorType(bd, BDLocation.BDLOCATION_BD09LL_TO_GCJ02).getLatitude();
        eNode = new BNRoutePlanNode(sX, sY, "", null, BNRoutePlanNode.CoordinateType.GCJ02);
        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<>();
            list.add(sNode);
            list.add(eNode);
            // 开发者可以使用旧的算路接口，也可以使用新的算路接口,可以接收诱导信息等
            BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(sNode), this);
        }
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
        mBaiduMap.clear();
        DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap) {
        };
        mBaiduMap.setOnMarkerClickListener(overlay);
        try {
            overlay.setData(drivingRouteResult.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        } catch (Exception e) {
            toastUtil.showToast("路线规划失败！");
        }
    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }

    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
            Intent intent = new Intent(LocationNavigationActivity.this, NavigationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        @Override
        public void onRoutePlanFailed() {
            Toast.makeText(LocationNavigationActivity.this, "算路失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCommonEventCall(int what, int arg1, int arg2, Bundle bundle) {
        BNEventHandler.getInstance().handleNaviEvent(what, arg1, arg2, bundle);
    }

    @Override
    public void playStart() {

    }

    @Override
    public void playEnd() {

    }

    @Override
    protected void onResume() {
        map_view.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        map_view.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BNEventHandler.getInstance().disposeDialog();
        mBaiduMap.setMyLocationEnabled(false);
    }

}
