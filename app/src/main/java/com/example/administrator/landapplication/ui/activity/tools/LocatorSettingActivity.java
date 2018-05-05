package com.example.administrator.landapplication.ui.activity.tools;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.bean.UserBean;
import com.example.administrator.landapplication.global.DataClass;
import com.example.administrator.landapplication.model.db.entity.ProcessDataConfigurationInfo;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.ui.dialog.ShowDialog;
import com.example.administrator.landapplication.utils.SystemUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/23.
 * 定位仪设置
 */

public class LocatorSettingActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.setting_view)
    ImageButton setting_view;
    @BindView(R.id.locator_number)
    TextView locator_number;
    @BindView(R.id.locator_model)
    TextView locator_model;
    private ShowDialog showDialog;
    private DataClass dataClass;
    private String locatorNumber, locatorModel;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.LOCATOR_NUMBER:
                if (!commonevent.getTemp_str().isEmpty())
                    locatorNumber = commonevent.getTemp_str();
                SystemUtil.textMagicTool(this, locator_number, getString(R.string.locator_number), locatorNumber);
                break;
            case EventCode.LOCATOR_MODEL:
                if (!commonevent.getTemp_str().isEmpty())
                    locatorModel = commonevent.getTemp_str();
                SystemUtil.textMagicTool(this, locator_model, getString(R.string.locator_model), locatorModel);
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_locator;
    }

    @Override
    protected void initClass() {
        showDialog = ShowDialog.newInstance();
        dataClass = new DataClass(dataManager);
    }

    @Override
    protected void initData() {
        List<ProcessDataConfigurationInfo> processDataConfigurationInfos = dataManager.loadProcessDataConfigurationInfo();
        if (processDataConfigurationInfos != null && processDataConfigurationInfos.size() > 0) {
            ProcessDataConfigurationInfo processDataConfigurationInfo = dataManager.queryProcessDataConfigurationInfo(UserBean.userCName);
            locatorNumber = processDataConfigurationInfo.getLocator_number();
            locatorModel = processDataConfigurationInfo.getLocator_model();
        } else {
            locatorNumber = getString(R.string.content_of_the_input);
            locatorModel = getString(R.string.content_of_the_input);
        }
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.locator_setting));
        setting_view.setImageDrawable(getResources().getDrawable(R.drawable.done_white));
        SystemUtil.textMagicTool(this, locator_number, getString(R.string.locator_number), locatorNumber);
        SystemUtil.textMagicTool(this, locator_model, getString(R.string.locator_model), locatorModel);
    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
        setting_view.setOnClickListener(this);
        locator_number.setOnClickListener(this);
        locator_model.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.setting_view:
                if (!locatorNumber.isEmpty() && !locatorModel.isEmpty()) {
                    dataClass.DbProcessDataConfigurationInfo(UserBean.userCName, locatorNumber, locatorModel);
                    toastUtil.showToast("已保存！");
                } else {
                    toastUtil.showToast("请输入完整！");
                }
                break;
            case R.id.locator_number:
                showDialog.showSuperInputDialog(this, EventCode.LOCATOR_NUMBER, getString(R.string.locator_number_), locatorNumber);
                break;
            case R.id.locator_model:
                showDialog.showSuperInputDialog(this, EventCode.LOCATOR_MODEL, getString(R.string.locator_model_), locatorModel);
                break;
        }
    }


}
