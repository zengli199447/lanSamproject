package com.example.administrator.landapplication.ui.activity.office;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.bean.AboutDataBean;
import com.example.administrator.landapplication.bean.LocationContentDataBean;
import com.example.administrator.landapplication.bean.UserBean;
import com.example.administrator.landapplication.global.DataClass;
import com.example.administrator.landapplication.model.db.entity.TaskContentInfo;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.ui.activity.tools.BluetoothPairingActivity;
import com.example.administrator.landapplication.ui.activity.tools.QrCodeActivity;
import com.example.administrator.landapplication.ui.dialog.ShowDialog;
import com.example.administrator.landapplication.utils.LogUtil;
import com.example.administrator.landapplication.utils.SystemUtil;
import com.example.administrator.landapplication.widget.AboutPopupWindow;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/18.
 * 现场采样
 */

public class SceneSamplingActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.setting_view)
    ImageButton setting_view;
    @BindView(R.id.about)
    TextView about;
    @BindView(R.id.soil_type)
    TextView soil_type;
    @BindView(R.id.sampling_member)
    TextView sampling_member;
    @BindView(R.id.time_sampling)
    TextView time_sampling;
    @BindView(R.id.longitude)
    TextView longitude;
    @BindView(R.id.latitude)
    TextView latitude;
    @BindView(R.id.sample_point_number_t)
    TextView sample_point_number_t;
    @BindView(R.id.altitude)
    TextView altitude;

    private Integer[] textViews = {0, R.id.land_use, R.id.crop_types, R.id.irrigation_water_type, R.id.topography, R.id.sampling_equipment, R.id.organic_sample,
            R.id.inorganic_sample, R.id.soil_type, R.id.soil_and_the_class, R.id.soil_color, R.id.soil_texture, R.id.soil_moisture, R.id.weather};
    private Integer[] relativeLayouts = {0, R.id.layout_land_use, R.id.layout_crop_types, R.id.layout_irrigation_water_type
            , R.id.layout_topography, R.id.layout_sampling_equipment, R.id.layout_organic_sample, R.id.layout_inorganic_sample
            , R.id.layout_soil_type, R.id.layout_soil_and_the_class, R.id.layout_soil_color, R.id.layout_soil_texture, R.id.layout_soil_moisture
            , R.id.layout_weather, R.id.sampling_depth, R.id.locator_number, R.id.locator_model, R.id.sample_weight};
    private Integer[] code = {0, EventCode.DICTIONARY_LAND_USE, EventCode.DICTIONARY_CROP_TYPES, EventCode.DICTIONARY_IRRIGATION_WATER_TYPE
            , EventCode.DICTIONARY_TOPOGRAPHY, EventCode.DICTIONARY_SAMPLING_EQUIPMENT, EventCode.DICTIONARY_ORGANIC_SAMPLE
            , EventCode.DICTIONARY_INORGANIC_SAMPLE, EventCode.DICTIONARY_SOIL_TYPE, EventCode.DICTIONARY_SOIL_AND_THE_CLASS
            , EventCode.DICTIONARY_SOIL_COLOR, EventCode.DICTIONARY_SOIL_TEXTURE, EventCode.DICTIONARY_SOIL_MOISTURE, EventCode.DICTIONARY_WEATHER};
    private Integer[] editText = {R.id.sampling_depth, R.id.locator_number, R.id.locator_model, R.id.sample_weight};
    private String[] titles = {"采样深度", "定位仪编号", "定位仪型号", "样品重量"};
    private Integer[] Strings = {R.string.sampling_depth, R.string.locator_number, R.string.locator_model, R.string.sample_weight};

    private ShowDialog showDialog;
    private HashMap<Integer, Integer> viewHashMap;
    private int takeSelectInsertTag = 0;
    private String selectContent;
    private String taskId;
    private TaskContentInfo taskContentInfo;
    private LocationContentDataBean locationContentDataBean;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.SELECT_SAMPLE_WEIGHT:
                selectContent = commonevent.getTemp_str();
                SystemUtil.textMagicTool(this, (TextView) findViewById(textViews[takeSelectInsertTag]), getString(Strings[takeSelectInsertTag - 14]), selectContent);
                break;
            case EventCode.LOCATION_NOTICE:
                locationContentDataBean = (LocationContentDataBean) commonevent.getObject();
                SystemUtil.textMagicTool(this, longitude, getString(R.string.longitude), String.valueOf(locationContentDataBean.getLongitude()));
                SystemUtil.textMagicTool(this, latitude, getString(R.string.latitude), String.valueOf(locationContentDataBean.getLatitude()));
                break;

        }
        if (takeSelectInsertTag < code.length && code[takeSelectInsertTag] == commonevent.getCode()) {
            ((TextView) findViewById(textViews[takeSelectInsertTag])).setText(commonevent.getTemp_str());
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_scene_sampling;
    }

    @Override
    protected void initClass() {
        viewHashMap = new HashMap<>();
        showDialog = ShowDialog.newInstance();

    }

    @Override
    protected void initData() {
        taskId = getIntent().getStringExtra("taskId");
        taskContentInfo = dataManager.queryTaskContentIdOnlyInfo(taskId);
        for (int i = 1; i < relativeLayouts.length; i++) {
            viewHashMap.put(i, relativeLayouts[i]);
        }
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.scene_sampling));
        setting_view.setImageDrawable(getResources().getDrawable(R.drawable.qr_code));
        SystemUtil.textMagicTool(this, sampling_member, getString(R.string.sampling_member), UserBean.userCName);
        SystemUtil.textMagicTool(this, time_sampling, getString(R.string.time_sampling), SystemUtil.getCurrentTime());
        SystemUtil.textMagicTool(this, sample_point_number_t, getString(R.string.sample_point_number_t), taskContentInfo.getCPointID());
        SystemUtil.textMagicTool(this, altitude, getString(R.string.altitude), taskContentInfo.getHB());
    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
        setting_view.setOnClickListener(this);
        about.setOnClickListener(this);
        for (int i = 1; i < relativeLayouts.length; i++) {
            findViewById(relativeLayouts[i]).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.setting_view:
                startActivity(new Intent(this, BluetoothPairingActivity.class));
                break;
            case R.id.about:
                startActivity(new Intent(this, SamplingDetailsActivity.class));
                break;
        }
        int key = getKey(view.getId());
        takeSelectInsertTag = key;
        if (key > 0 && key != 9 && key < 14) {
            showDialog.showAttributeTypeDialog(this, dataManager, code[key], selectContent);
        } else if (key == 9) {
            if (soil_type.getText().toString().isEmpty()) {
                toastUtil.showToast("请先选取主类");
            } else {
                showDialog.showAttributeTypeDialog(this, dataManager, code[key], soil_type.getText().toString());
            }
        } else if (key > 13 && key < relativeLayouts.length + 1) {
            showDialog.showSuperInputDialog(this, EventCode.SELECT_SAMPLE_WEIGHT, titles[key - 14], getString(R.string.content_of_the_input));
        }
    }

    private Integer getKey(Integer value) {
        Integer key = 0;
        for (Integer getKey : viewHashMap.keySet()) {
            if (getKey != null && viewHashMap.get(getKey) != null && viewHashMap.get(getKey).equals(value)) {
                key = getKey;
            }
        }
        return key;
    }

    private void NetWork() {

    }


}
