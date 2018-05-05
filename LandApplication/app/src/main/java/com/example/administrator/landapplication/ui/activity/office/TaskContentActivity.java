package com.example.administrator.landapplication.ui.activity.office;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.model.db.entity.TaskContentInfo;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.utils.LogUtil;
import com.example.administrator.landapplication.utils.SystemUtil;
import com.example.administrator.landapplication.widget.SpannableBuilder;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/13.
 * 任务详情
 */

public class TaskContentActivity extends BaseActivity implements View.OnClickListener, PopupWindow.OnDismissListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.setting_view)
    ImageButton setting_view;
    @BindView(R.id.the_name_of_the_point)
    TextView the_name_of_the_point;
    @BindView(R.id.sample_point_number)
    TextView sample_point_number;
    @BindView(R.id.quality_control_point)
    CheckBox quality_control_point;
    @BindView(R.id.point_type)
    TextView point_type;
    @BindView(R.id.setting_the_reason)
    TextView setting_the_reason;
    @BindView(R.id.administrative_division_code)
    TextView administrative_division_code;
    @BindView(R.id.provincial)
    TextView provincial;
    @BindView(R.id.any)
    TextView any;
    @BindView(R.id.county)
    TextView county;
    @BindView(R.id.villages_and_towns)
    TextView villages_and_towns;
    @BindView(R.id.village)
    TextView village;
    @BindView(R.id.distance)
    TextView distance;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.position_description)
    TextView position_description;
    @BindView(R.id.longitude)
    TextView longitude;
    @BindView(R.id.latitude)
    TextView latitude;
    @BindView(R.id.altitude)
    TextView altitude;
    @BindView(R.id.land_use_type)
    TextView land_use_type;
    @BindView(R.id.soil_type)
    TextView soil_type;
    @BindView(R.id.soil_and_the_class)
    TextView soil_and_the_class;
    @BindView(R.id.monitoring_details)
    TextView monitoring_details;
    @BindView(R.id.relative_layout)
    RelativeLayout relative_layout;
    @BindView(R.id.start_task_status)
    TextView start_task_status;
    @BindView(R.id.task_status_bottom)
    TextView task_status_bottom;
    @BindView(R.id.begin_navigation)
    TextView begin_navigation;
    @BindView(R.id.scene_sampling)
    TextView scene_sampling;
    private PopupWindow popupWindow;
    private int taskType; //进行中/非进行中
    private String taskId; //任务id
    private String samplePointNumber, qualityControlPoint, pointType, theNameOfThePoint, settingTheReason, administrativeDivisionCode,
            mProvincial, mAny, mCounty, villagesAndTowns, mVillage, mDistance, mAddress, positionDescription, mLongitude, mLatitude,
            mAltitude, landUseType, soilType, soilAndTheClass, monitoringDetails;
    private TaskContentInfo taskContentInfo;
    private boolean[] CPointType = {true, false};

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_task_content;
    }

    @Override
    protected void initClass() {

    }

    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        taskType = Integer.valueOf(extras.get("taskType").toString());
        taskId = extras.get("taskId").toString();
        taskContentInfo = dataManager.queryTaskContentIdOnlyInfo(taskId);
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.task_content));
        img_btn_black.setVisibility(View.VISIBLE);
        setting_view.setImageResource(R.drawable.more);
        quality_control_point.setEnabled(false);
        initViewContent();
        switch (taskType) {
            case 0:
                setingTaskChangeStatus(taskType);
                break;
            case 1:
                break;
            case 4:
                scene_sampling.setText(getString(R.string.resampling));
                break;
        }
    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
        setting_view.setOnClickListener(this);
        start_task_status.setOnClickListener(this);
        begin_navigation.setOnClickListener(this);
        scene_sampling.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.setting_view:
                showPopWindows();
                break;
            case R.id.layout_status:
                setingTaskChangeStatus(0);
                popupWindow.dismiss();
                break;
            case R.id.layout_change:
                startActivity(new Intent(this, ChangeRequestActivity.class));
                popupWindow.dismiss();
                break;
            case R.id.start_task_status:
                setingTaskChangeStatus(1);
                break;
            case R.id.begin_navigation:

                break;
            case R.id.scene_sampling:
                Intent intent = new Intent(this, SceneSamplingActivity.class);
                intent.putExtra("taskId",taskId);
                startActivity(intent);
                break;
        }
    }

    private void setingTaskChangeStatus(int status) {
        switch (status) {
            case 0:
                setting_view.setVisibility(View.GONE);
                relative_layout.setVisibility(View.GONE);
                task_status_bottom.setText(getString(R.string.task_or_online));
                break;
            case 1:
                setting_view.setVisibility(View.VISIBLE);
                relative_layout.setVisibility(View.VISIBLE);
                task_status_bottom.setText(getString(R.string.task_online));
                break;
        }
    }

    public void showPopWindows() {
        LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup menuView = (ViewGroup) mLayoutInflater.inflate(R.layout.custom_popwindow_title, null, true);
        View layout_status = menuView.findViewById(R.id.layout_status);
        View layout_change = menuView.findViewById(R.id.layout_change);
        layout_status.setOnClickListener(this);
        layout_change.setOnClickListener(this);
        popupWindow = new PopupWindow(menuView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(this);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        popupWindow.showAtLocation(menuView, Gravity.TOP, width - popupWindow.getWidth(), findViewById(R.id.title_view_layout).getHeight());
        SystemUtil.windowToDark(this);
        popupWindow.update();
    }

    private void textMagicTool(TextView view, String firstText, String lastText) {
        if (lastText != null && !lastText.isEmpty())
            view.setText(SpannableBuilder.create(this)
                    .append(firstText, R.dimen.text_content_size, R.color.black).
                            append(new StringBuffer().append(" ").append(lastText).toString(), R.dimen.text_content_size, R.color.grey).build());
    }


    private void initViewContent() {
        try {
            textMagicTool(sample_point_number, getString(R.string.sample_point_number_t), taskContentInfo.getCPointID());
            quality_control_point.setChecked(CPointType[Integer.valueOf(taskContentInfo.getIsQCPoint())]);
            textMagicTool(point_type, getString(R.string.point_type), taskContentInfo.getCPointType());
            textMagicTool(the_name_of_the_point, getString(R.string.the_name_of_the_point), taskContentInfo.getCPointName());
            textMagicTool(setting_the_reason, getString(R.string.setting_the_reason), taskContentInfo.getYY());
            textMagicTool(administrative_division_code, getString(R.string.administrative_division_code), taskContentInfo.getXZQHDM());
            textMagicTool(provincial, getString(R.string.provincial), taskContentInfo.getSJMC());
            textMagicTool(any, getString(R.string.any), taskContentInfo.getDJMC());
            textMagicTool(county, getString(R.string.county), taskContentInfo.getXQMC());
            textMagicTool(villages_and_towns, getString(R.string.villages_and_towns), taskContentInfo.getXZMC());
            textMagicTool(village, getString(R.string.village), taskContentInfo.getCJMC());
            textMagicTool(distance, getString(R.string.distance), mDistance);
            textMagicTool(address, getString(R.string.address), taskContentInfo.getDZ());
            textMagicTool(position_description, getString(R.string.position_description), taskContentInfo.getFWMS());
            textMagicTool(longitude, getString(R.string.longitude), taskContentInfo.getJD());
            textMagicTool(latitude, getString(R.string.latitude), taskContentInfo.getWD());
            textMagicTool(altitude, getString(R.string.altitude), taskContentInfo.getHB());
            textMagicTool(land_use_type, getString(R.string.land_use_type), taskContentInfo.getTDLYDesc());
            textMagicTool(soil_type, getString(R.string.soil_type), taskContentInfo.getTRLXDesc());
            textMagicTool(soil_and_the_class, getString(R.string.soil_and_the_class), taskContentInfo.getTRYLDesc());
            textMagicTool(monitoring_details, getString(R.string.monitoring_details), monitoringDetails);
        } catch (Exception e) {
            LogUtil.e(TAG,"");
        }
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
    }

}
