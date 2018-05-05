package com.example.administrator.landapplication.ui.activity.tools;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.global.DataClass;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.ui.activity.office.ChargeTaskConfirmActivity;
import com.example.administrator.landapplication.ui.activity.office.MonitoringCenterListActivity;
import com.example.administrator.landapplication.widget.zxing.CaptureManagerTools;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;


import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/16.
 * 扫描
 */

public class QrCodeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.qr_tools)
    DecoratedBarcodeView qr_tools;
    private CaptureManagerTools captureManager;     //捕获管理器

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        captureManager = new CaptureManagerTools(this, qr_tools);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_qr;
    }

    @Override
    protected void initClass() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.qr_code));
        img_btn_black.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                switch (DataClass.QrAction){
                    case 0:
                        startActivity(new Intent(this, MonitoringCenterListActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(this, ChargeTaskConfirmActivity.class));
                        break;
                    case 2:
                        break;
                }
                finish();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return qr_tools.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

}
