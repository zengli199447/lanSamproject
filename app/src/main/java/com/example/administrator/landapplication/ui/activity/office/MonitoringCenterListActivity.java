package com.example.administrator.landapplication.ui.activity.office;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.bean.SubmissionContentBean;
import com.example.administrator.landapplication.bean.UserBean;
import com.example.administrator.landapplication.global.DataClass;
import com.example.administrator.landapplication.global.GlobalNetWorkManager;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.model.event.NetEventCode;
import com.example.administrator.landapplication.model.rxtools.RxBus;
import com.example.administrator.landapplication.soaptools.UrlList;
import com.example.administrator.landapplication.ui.adapter.ChargeTaskContentAdapter;
import com.example.administrator.landapplication.ui.dialog.ProgressDialog;
import com.example.administrator.landapplication.ui.dialog.ShowDialog;
import com.example.administrator.landapplication.ui.view.AutoListView;
import com.example.administrator.landapplication.utils.LogUtil;
import com.example.administrator.landapplication.utils.SystemUtil;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/18.
 * 监测中心确认收货列表
 */

public class MonitoringCenterListActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.monitoring_content_list)
    AutoListView monitoring_content_list;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.cancle)
    TextView cancle;
    @BindView(R.id.consignee)
    TextView consignee;
    @BindView(R.id.delivery_unit)
    TextView delivery_unit;
    @BindView(R.id.deliveryman)
    TextView deliveryman;

    private ArrayList<SubmissionContentBean> submissionContentBeenList = new ArrayList<>();
    private ChargeTaskContentAdapter chargeTaskContentAdapter;
    private ShowDialog showDialog;
    private ProgressDialog progressDialog;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case NetEventCode.NETWORK_MONITORING_CENTER_LIST:
                SoapObject result = (SoapObject) commonevent.getObject();
                LogUtil.e(TAG, "SoapObject : " + result.getProperty(0));
                progressDialog.dismiss();
                break;
            case NetEventCode.NETWORK_ERROR:
                resultSoapData();
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
        return R.layout.activity_monitoring_list;
    }

    @Override
    protected void initClass() {
        showDialog = ShowDialog.newInstance();
        progressDialog = showDialog.showProgressStatus(this, getString(R.string.progress));
        chargeTaskContentAdapter = new ChargeTaskContentAdapter(submissionContentBeenList, this);
    }

    @Override
    protected void initData() {
        initNetWork();
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.monitoring_center_list_reception));
        img_btn_black.setVisibility(View.VISIBLE);
        SystemUtil.textMagicTool(this, time, getString(R.string.time), SystemUtil.initDate(Calendar.getInstance()));
        SystemUtil.textMagicTool(this, consignee, getString(R.string.consignee), UserBean.userCName);
        SystemUtil.textMagicTool(this, delivery_unit, getString(R.string.delivery_unit), DataClass.DeliveryUnit);
        SystemUtil.textMagicTool(this, deliveryman, getString(R.string.deliveryman), DataClass.DeliveryMan);
        monitoring_content_list.setAdapter(chargeTaskContentAdapter);
        chargeTaskContentAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
        submit.setOnClickListener(this);
        cancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.submit:
                finish();
                RxBus.getDefault().post(new CommonEvent(EventCode.COMMITE_FINISH));
                break;
            case R.id.cancle:
                finish();
                break;
        }
    }

    private void initNetWork() {
        progressDialog.ShowDiaLog();
        Map<String, Object> param = new HashMap<>();
        param.put("TUID", "");
        param.put("commiterID", "");
        param.put("remark", "");
        GlobalNetWorkManager.getNetWorkData(param, "addSampleReceiveInfo", UrlList.MONITORING_CENTER_LIST_RECEPTION, NetEventCode.NETWORK_MONITORING_CENTER_LIST, this);
    }

    public void resultSoapData() {
        submissionContentBeenList.add(new SubmissionContentBean("1", "XW20180414"));
        submissionContentBeenList.add(new SubmissionContentBean("2", "XW20180414"));
        chargeTaskContentAdapter.notifyDataSetChanged();
    }

}
