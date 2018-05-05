package com.example.administrator.landapplication.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.bean.UserAboutBean;
import com.example.administrator.landapplication.bean.UserBean;
import com.example.administrator.landapplication.bean.UserToolsTypeBean;
import com.example.administrator.landapplication.global.DataClass;
import com.example.administrator.landapplication.global.GlobalNetWorkManager;
import com.example.administrator.landapplication.global.PermissionsClass;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.model.event.NetEventCode;
import com.example.administrator.landapplication.network.DictionaryNetWork;
import com.example.administrator.landapplication.server.LoopRequestService;
import com.example.administrator.landapplication.server.SingBaiDuService;
import com.example.administrator.landapplication.server.VersionUpService;
import com.example.administrator.landapplication.soaptools.UrlList;
import com.example.administrator.landapplication.ui.activity.office.ChargeTaskActivity;
import com.example.administrator.landapplication.ui.activity.office.ChargeTaskConfirmActivity;
import com.example.administrator.landapplication.ui.activity.office.CurrentTaskActivity;
import com.example.administrator.landapplication.ui.activity.office.MonitoringCenterActivity;
import com.example.administrator.landapplication.ui.activity.office.MonitoringCenterListActivity;
import com.example.administrator.landapplication.ui.activity.tools.InspectionRoutePlanningActivity;
import com.example.administrator.landapplication.ui.activity.tools.LocatorSettingActivity;
import com.example.administrator.landapplication.ui.adapter.ToolsAdapter;
import com.example.administrator.landapplication.ui.dialog.ProgressDialog;
import com.example.administrator.landapplication.ui.dialog.ShowDialog;
import com.example.administrator.landapplication.utils.LogUtil;
import com.example.administrator.landapplication.utils.SystemUtil;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/11.
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.setting_view)
    ImageButton setting_view;
    @BindView(R.id.home_grid_view)
    GridView home_grid_view;
    private DataClass dataClass;
    private ToolsAdapter toolsAdapter;
    private ArrayList<UserToolsTypeBean> userToolsTypeBeen;
    private Intent singBaiDuService;
    private double exitTime = 0;
    private ShowDialog showDialog;
    private int Max = 12;
    private int current = 0;
    private ProgressDialog progressDialog;
    private Intent versionUpIntent;
    private DictionaryNetWork dictionaryNetWork;
    private Object appUrl;
    private String appVersion;
    private Intent loopRequestServiceIntent;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.COMMITE_REFRESH:
                if (appUrl != null && appUrl.toString().isEmpty()) {
                    progressDialog.ShowDiaLog();
                    BeginVersionService();
                } else {
                    toastUtil.showToast("下载地址有误！");
                }
                break;
            case EventCode.VERSION_UP_SUCCESSFUL:
                progressDialog.dismiss();
                break;
            case EventCode.VERSION_UP_FAILED:
                toastUtil.showToast("下载失败");
                progressDialog.dismiss();
                break;
            case EventCode.PROGRESS_UPDATA:
                initDictionaryData();
                break;
            case NetEventCode.NETWORK_VERSION:
                SoapObject result = (SoapObject) ((SoapObject) commonevent.getObject()).getProperty(0);
                try {
                    appVersion = result.getProperty("appVersion").toString();
                    appUrl = result.getProperty("appUrl");
                } catch (Exception e) {
                }
                if (!SystemUtil.getAppInfo(this).equals(appVersion)) {
                    progressDialog = showDialog.showProgressStatus(this, "下载安装包 ...");
                    showDialog.showVersionDialog(this, appVersion);
                }
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initClass() {
        dataClass = new DataClass();
        initUserType();
        showDialog = ShowDialog.newInstance();
        progressDialog = showDialog.showProgressStatus(this, new StringBuffer().append("载入基础数据").append(current).append("/").append(Max).toString());
        progressDialog.setCanceledOnTouchOutside(false);
        singBaiDuService = new Intent(this, SingBaiDuService.class);
        loopRequestServiceIntent = new Intent(this, LoopRequestService.class);
        dictionaryNetWork = new DictionaryNetWork(this, dataManager);
        startService(singBaiDuService);
        startService(loopRequestServiceIntent);
    }

    @Override
    protected void initData() {
        userToolsTypeBeen = dataClass.initPression();
        toolsAdapter = new ToolsAdapter(userToolsTypeBeen, this);
//        getDictionaryInfo();
//        getVersionInfo();
    }

    @Override
    protected void initView() {
        img_btn_black.setVisibility(View.INVISIBLE);
        setting_view.setImageResource(R.drawable.mine_white);
        title_name.setText(getString(R.string.sys));
        home_grid_view.setAdapter(toolsAdapter);

    }

    @Override
    protected void initListener() {
        setting_view.setOnClickListener(this);
        home_grid_view.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_view:
                startActivity(new Intent(this, PersonalActivity.class));
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        UserToolsTypeBean userToolsTypeBean = userToolsTypeBeen.get(i);
        toastUtil.showToast(userToolsTypeBean.getToolId() + " - " + userToolsTypeBean.getToolName());
        switch (userToolsTypeBean.getToolId()) {
            case 0: //新建任务
                Intent intent = new Intent(this, CurrentTaskActivity.class);
                intent.putExtra("taskType", 0);
                startActivity(intent);
                break;
            case 1: //在线任务
                Intent intent_online = new Intent(this, CurrentTaskActivity.class);
                intent_online.putExtra("taskType", 1);
                startActivity(intent_online);
                break;
            case 2: //巡检
                startActivity(new Intent(this, InspectionRoutePlanningActivity.class));
                break;
            case 3: //退出
                finish();
                break;
            case 4: //收货
                startActivity(new Intent(this, ChargeTaskActivity.class));
                break;
            case 5: //定位仪设置
                startActivity(new Intent(this, LocatorSettingActivity.class));
                break;
            case 6: //监测中心收货
                startActivity(new Intent(this, MonitoringCenterActivity.class));
                break;
            case 7: //监测中心确认收货列表
                startActivity(new Intent(this, MonitoringCenterListActivity.class));
                break;
            case 8: //实验室收货样品确认
                startActivity(new Intent(this, ChargeTaskConfirmActivity.class));
                break;
        }
    }

    //字典表更新进度
    private void initDictionaryData() {
        current = current + 1;
        progressDialog.setProgress(new StringBuffer().append("载入基础数据").append(current).append("/").append(Max).toString());
        LogUtil.e(TAG, "current : " + current);
        if (current == Max) {
            progressDialog.dismiss();
            getVersionInfo();
        }
    }

    //下载服务
    private void BeginVersionService() {
        versionUpIntent = new Intent(this, VersionUpService.class);
        versionUpIntent.putExtra("LoadUrl", appUrl.toString());
        startService(versionUpIntent);
    }

    //字典 NetWork :
    private void getDictionaryInfo() {
        dictionaryNetWork.getDictionaryUpDataStatus(0);
        progressDialog.ShowDiaLog();
    }

    //版本 NetWork :
    private void getVersionInfo() {
        GlobalNetWorkManager.getNetWorkData(null, UrlList.LastVersionApp, UrlList.SYSTEMINFO, NetEventCode.NETWORK_VERSION, this);
    }

    //用户类型
    private void initUserType() {
        HashMap<String, Integer> userTypeHashMap = new HashMap<>();
        userTypeHashMap.put(getString(R.string.sampling_member_),0);
        userTypeHashMap.put(getString(R.string.monitoring_center_member),1);
        userTypeHashMap.put(getString(R.string.laboratory_consignee),2);
        PermissionsClass.UserType = userTypeHashMap.get(UserAboutBean.userRolesDesc);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (singBaiDuService != null)
            stopService(singBaiDuService);
        if (versionUpIntent != null)
            stopService(versionUpIntent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            toastUtil.showToast(getString(R.string.finish));
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}
