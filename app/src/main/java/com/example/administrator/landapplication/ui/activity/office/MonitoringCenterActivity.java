package com.example.administrator.landapplication.ui.activity.office;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.bean.AboutDataBean;
import com.example.administrator.landapplication.bean.UserBean;
import com.example.administrator.landapplication.global.DataClass;
import com.example.administrator.landapplication.global.GlobalNetWorkManager;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.model.event.NetEventCode;
import com.example.administrator.landapplication.soaptools.UrlList;
import com.example.administrator.landapplication.ui.activity.tools.QrCodeActivity;
import com.example.administrator.landapplication.ui.dialog.ProgressDialog;
import com.example.administrator.landapplication.ui.dialog.ShowDialog;
import com.example.administrator.landapplication.utils.LogUtil;
import com.example.administrator.landapplication.utils.SystemUtil;
import com.example.administrator.landapplication.widget.AboutPopupWindow;
import com.google.zxing.integration.android.IntentIntegrator;

import org.ksoap2.serialization.SoapObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/18.
 * 监测中心收货
 */

public class MonitoringCenterActivity extends BaseActivity implements View.OnClickListener, AboutPopupWindow.OnAboutItemClickListener, PopupWindow.OnDismissListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.start_consignee)
    TextView start_consignee;
    @BindView(R.id.consignee)
    TextView consignee;
    @BindView(R.id.layout_delivery_unit)
    RelativeLayout layout_delivery_unit;
    @BindView(R.id.delivery_unit)
    TextView delivery_unit;
    @BindView(R.id.layout_deliveryman)
    RelativeLayout layout_deliveryman;
    @BindView(R.id.deliveryman)
    TextView deliveryman;

    private HashMap<Integer, AboutDataBean> aboutDataBeenMap = new HashMap<>();
    private AboutPopupWindow aboutPopupWindow;
    private int type = 0;
    private ShowDialog showDialog;
    private ProgressDialog progressDialog;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.COMMITE_FINISH:
                finish();
                break;
            case NetEventCode.NETWORK_GETCOMPANY:
                SoapObject result = (SoapObject) commonevent.getObject();
                LogUtil.e(TAG, "SoapObject : " + result.getProperty(0));
                progressDialog.dismiss();
                break;
            case NetEventCode.NETWORK_ERROR:
                toastUtil.showToast(commonevent.getTemp_str());
                progressDialog.dismiss();
                break;

        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_monitoring;
    }

    @Override
    protected void initClass() {
        showDialog = ShowDialog.newInstance();
        progressDialog = showDialog.showProgressStatus(this, getString(R.string.progress));
        aboutPopupWindow = new AboutPopupWindow(this, aboutDataBeenMap);
    }

    @Override
    protected void initData() {
        initNetWork();
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.monitoring_center_reception));
        img_btn_black.setVisibility(View.VISIBLE);
        SystemUtil.textMagicTool(this, time, getString(R.string.time), SystemUtil.initDate(Calendar.getInstance()));
        SystemUtil.textMagicTool(this, consignee, getString(R.string.consignee), UserBean.userCName);
    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
        start_consignee.setOnClickListener(this);
        layout_delivery_unit.setOnClickListener(this);
        layout_deliveryman.setOnClickListener(this);
        aboutPopupWindow.setOnAboutItemClickListener(this);
        aboutPopupWindow.setOnDismissListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.start_consignee:
                if (!delivery_unit.getText().toString().isEmpty() && !deliveryman.getText().toString().isEmpty()) {
                    DataClass.QrAction = 0;
                    new IntentIntegrator(this).setOrientationLocked(false).setCaptureActivity(QrCodeActivity.class).initiateScan();
                } else {
                    toastUtil.showToast("信息未完善 ...");
                }
                break;
            case R.id.layout_delivery_unit:
                initNetWork(0);
                break;
            case R.id.layout_deliveryman:
                initNetWork(1);
                break;
        }
    }

    @Override
    public void setOnAboutItemClick(View v, AboutDataBean aboutDataBean) {
        switch (v.getId()) {
            case R.id.cancle:
                aboutPopupWindow.dismiss();
                break;
            case R.id.commit:
                if (aboutDataBean != null) {
                    String content = aboutDataBean.getContent();
                    if (type == 0) {
                        delivery_unit.setText(content);
                        DataClass.DeliveryUnit = content;
                    } else {
                        deliveryman.setText(content);
                        DataClass.DeliveryMan = content;
                    }
                }
                aboutPopupWindow.dismiss();
                break;
        }
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
    }

    //模拟数据
    private void initNetWork(int type) {
        this.type = type;
        aboutDataBeenMap.clear();
        switch (type) {
            case 0:
                aboutDataBeenMap.put(0, new AboutDataBean("大连市地质勘测局", 0));
                aboutDataBeenMap.put(1, new AboutDataBean("大连市土壤监测管理局", 0));
                aboutDataBeenMap.put(2, new AboutDataBean("大连市环境保护协会", 0));
                break;
            case 1:
                aboutDataBeenMap.put(0, new AboutDataBean("陈良俊", 0));
                aboutDataBeenMap.put(1, new AboutDataBean("娜娜", 0));
                aboutDataBeenMap.put(2, new AboutDataBean("程协", 0));
                aboutDataBeenMap.put(3, new AboutDataBean("李娜", 0));
                break;
        }
        aboutPopupWindow = new AboutPopupWindow(this, aboutDataBeenMap);
        aboutPopupWindow.showAtLocation(findViewById(R.id.monitoring), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
        initListener();
        SystemUtil.windowToDark(this);
    }

    private void initNetWork() {
//        progressDialog.ShowDiaLog();
        Map<String, Object> param = new HashMap<>();
        GlobalNetWorkManager.getNetWorkData(param, "getCompany", UrlList.MONITORING_CENTER_LIST_RECEPTION, NetEventCode.NETWORK_GETCOMPANY, this);
    }


}
