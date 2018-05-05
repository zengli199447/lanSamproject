package com.example.administrator.landapplication.ui.activity.tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.ui.dialog.ShowDialog;
import com.example.administrator.landapplication.utils.LogUtil;
import com.example.administrator.landapplication.utils.SystemUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/24.
 * 巡检路线规划
 */

public class InspectionRoutePlanningActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.longitude)
    TextView longitude;
    @BindView(R.id.latitude)
    TextView latitude;
    @BindView(R.id.mission_statement)
    TextView mission_statement;
    @BindView(R.id.begin_inspection)
    TextView begin_inspection;
    private ShowDialog showDialog;
    private String longitudeContent = "";
    private String latitudeContent = "";
    private String missionStatementContent = "";

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.INPUT_LONGITUDE:
                if (!commonevent.getTemp_str().isEmpty()) {
                    longitudeContent = commonevent.getTemp_str();
                    SystemUtil.textMagicTool(this, longitude, getString(R.string.longitude), longitudeContent);
                }
                break;
            case EventCode.INPUT_LATITUDE:
                if (!commonevent.getTemp_str().isEmpty()) {
                    latitudeContent = commonevent.getTemp_str();
                    SystemUtil.textMagicTool(this, latitude, getString(R.string.latitude), latitudeContent);
                }
                break;
            case EventCode.INPUT_MISSION_STATEMENT:
                if (!commonevent.getTemp_str().isEmpty()) {
                    missionStatementContent = commonevent.getTemp_str();
                    SystemUtil.textMagicTool(this, mission_statement, getString(R.string.mission_statement), missionStatementContent);
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
        return R.layout.activity_inspection_route_planning;
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
        title_name.setText(getString(R.string.inspection));
        img_btn_black.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
        longitude.setOnClickListener(this);
        latitude.setOnClickListener(this);
        mission_statement.setOnClickListener(this);
        begin_inspection.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.longitude:
                showDialog.showSuperInputDialog(this, EventCode.INPUT_LONGITUDE, getString(R.string.longitude_), getString(R.string.content_of_the_input));
                break;
            case R.id.latitude:
                showDialog.showSuperInputDialog(this, EventCode.INPUT_LATITUDE, getString(R.string.latitude_), getString(R.string.content_of_the_input));
                break;
            case R.id.mission_statement:
                showDialog.showSuperInputDialog(this, EventCode.INPUT_MISSION_STATEMENT, getString(R.string.mission_statement_), getString(R.string.content_of_the_input));
                break;
            case R.id.begin_inspection:
                boolean status = true;
                if (!longitudeContent.isEmpty() && !latitudeContent.isEmpty() && !missionStatementContent.isEmpty()) {
                    try {
                        Double.valueOf(longitudeContent);
                        Double.valueOf(latitudeContent);
                    } catch (Exception e) {
                        status = false;
                        toastUtil.showToast("请输入正确坐标值！");
                    }
                    if (status) {
                        Intent intent = new Intent(this, LocationNavigationActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Longitude", longitudeContent);
                        bundle.putString("Latitude", latitudeContent);
                        bundle.putString("MissionStatement", missionStatementContent);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                } else {
                    toastUtil.showToast("请输入完整！");
                }
                break;
        }
    }


}
