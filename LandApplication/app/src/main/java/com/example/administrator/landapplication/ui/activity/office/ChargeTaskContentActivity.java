package com.example.administrator.landapplication.ui.activity.office;


import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.bean.SubmissionContentBean;
import com.example.administrator.landapplication.global.DataClass;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.ui.activity.tools.QrCodeActivity;
import com.example.administrator.landapplication.ui.adapter.ChargeTaskContentAdapter;
import com.example.administrator.landapplication.ui.view.AutoListView;
import com.example.administrator.landapplication.utils.SystemUtil;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/16.
 * 实验室签收列表详情
 */

public class ChargeTaskContentActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.delivery_number)
    TextView delivery_number;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.charge_content_list)
    AutoListView charge_content_list;
    @BindView(R.id.start_consignee)
    TextView start_consignee;

    private ArrayList<SubmissionContentBean> submissionContentBeenList = new ArrayList<>();
    private ChargeTaskContentAdapter chargeTaskContentAdapter;
    private String deliveryNumber;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.COMMITE_FINISH:
                finish();
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_charge_content;
    }

    @Override
    protected void initClass() {
        chargeTaskContentAdapter = new ChargeTaskContentAdapter(submissionContentBeenList, this);
    }

    @Override
    protected void initData() {
        deliveryNumber = getIntent().getStringExtra("deliveryNumber");
        initNetWork();

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.the_goods_content));
        img_btn_black.setVisibility(View.VISIBLE);
        SystemUtil.textMagicTool(this, time, getString(R.string.time), SystemUtil.initDate(Calendar.getInstance()));
        SystemUtil.textMagicTool(this, delivery_number, getString(R.string.delivery_number_to), deliveryNumber);
        charge_content_list.setAdapter(chargeTaskContentAdapter);
        chargeTaskContentAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
        start_consignee.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.start_consignee:
                DataClass.QrAction = 1;
                new IntentIntegrator(this).setOrientationLocked(false).setCaptureActivity(QrCodeActivity.class).initiateScan();
                break;

        }
    }

    private ArrayList<SubmissionContentBean> initNetWork() {
        submissionContentBeenList.add(new SubmissionContentBean("1", "XW20180414"));
        submissionContentBeenList.add(new SubmissionContentBean("2", "XW20180414"));
        return submissionContentBeenList;
    }


}
