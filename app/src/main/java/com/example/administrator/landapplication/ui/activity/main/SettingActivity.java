package com.example.administrator.landapplication.ui.activity.main;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.bean.UserBean;
import com.example.administrator.landapplication.global.MyApplication;
import com.example.administrator.landapplication.model.db.entity.AppConfigurationInfo;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.model.rxtools.RxBus;
import com.example.administrator.landapplication.ui.dialog.ProgressDialog;
import com.example.administrator.landapplication.ui.dialog.ShowDialog;
import com.example.administrator.landapplication.utils.SystemUtil;

import java.io.File;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/12.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;

    @BindView(R.id.cache_layout)
    RelativeLayout cache_layout;
    @BindView(R.id.version_layout)
    RelativeLayout version_layout;
    @BindView(R.id.push_layout)
    RelativeLayout push_layout;
    @BindView(R.id.button_cancellation)
    TextView button_cancellation;

    @BindView(R.id.tv_setting_cache)
    TextView tv_setting_cache;
    @BindView(R.id.tv_setting_version)
    TextView tv_setting_version;
    @BindView(R.id.tv_setting_push)
    TextView tv_setting_push;
    private ShowDialog showDialog;
    private ProgressDialog progressDialog;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.COMMITE_REFRESH:
                initTextContent();
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initClass() {
        showDialog = ShowDialog.newInstance();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.setting));
        img_btn_black.setVisibility(View.VISIBLE);
        initTextContent();
    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
        button_cancellation.setOnClickListener(this);
        cache_layout.setOnClickListener(this);
        version_layout.setOnClickListener(this);
        push_layout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.button_cancellation:
                progressDialog = showDialog.showProgressStatus(this, getString(R.string.cancellation_progress));
                progressDialog.ShowDiaLog();
                CancellationGoing();
                break;
            case R.id.cache_layout:
                showDialog.showCacheDialog(this, dataManager);
                break;
            case R.id.version_layout:
                showDialog.showVersionDialog(this, SystemUtil.getAppInfo(this));
                break;
            case R.id.push_layout:
                showDialog.showPushDialog(this, dataManager);
                break;
        }

    }

    private void CancellationGoing() {
        MyApplication.executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(999);
                    startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                    RxBus.getDefault().post(new CommonEvent(EventCode.COMMITE_FINISH));
                    progressDialog.dismiss();
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initTextContent() {
        AppConfigurationInfo appConfigurationInfo = dataManager.queryAppConfigurationInfo(UserBean.userName);
        String cacheName = appConfigurationInfo.getCacheName();
        tv_setting_push.setText(new StringBuffer().append(appConfigurationInfo.getStartPush()).append("时").append(" - ").append(appConfigurationInfo.getEndPush()).append("时"));
        tv_setting_cache.setText(SystemUtil.getFormatSize(SystemUtil.getFolderSize(new File(cacheName))));
        tv_setting_version.setText(new StringBuffer().append("当前版本 ").append(SystemUtil.getAppInfo(this)));
    }

}
