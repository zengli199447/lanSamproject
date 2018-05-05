package com.example.administrator.landapplication.ui.activity.office;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.bean.ChargeTaskConfirmBean;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.ui.adapter.ChargeTaskConfirmAdapter;
import com.example.administrator.landapplication.ui.view.AutoListView;
import com.example.administrator.landapplication.utils.LogUtil;
import com.example.administrator.landapplication.utils.SystemUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/18.
 * 实验室收货样品确认
 */

public class ChargeTaskConfirmActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.laboratory_content_list)
    AutoListView laboratory_content_list;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.cancle)
    TextView cancle;
    private ArrayList<ChargeTaskConfirmBean> chargeTaskConfirmBeanArrayList = new ArrayList<>();
    private ChargeTaskConfirmAdapter chargeTaskConfirmAdapter;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.actvity_task_confirm;
    }

    @Override
    protected void initClass() {
        chargeTaskConfirmAdapter = new ChargeTaskConfirmAdapter(chargeTaskConfirmBeanArrayList, this);
    }

    @Override
    protected void initData() {
        initNetWork();
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.laboratory_confirm));
        img_btn_black.setVisibility(View.VISIBLE);
        SystemUtil.textMagicTool(this, time, getString(R.string.time), SystemUtil.initDate(Calendar.getInstance()));
        laboratory_content_list.setAdapter(chargeTaskConfirmAdapter);
        chargeTaskConfirmAdapter.notifyDataSetChanged();
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
                HashMap<Integer, String> checkStatusHashMap = chargeTaskConfirmAdapter.getCheckStatusHashMap();
                for (int i = 0; i < checkStatusHashMap.size(); i++) {
                    String s = checkStatusHashMap.get(i);
                    LogUtil.e(TAG, "s : " + s);
                    toastUtil.showToast(s);
                }
                break;
            case R.id.cancle:
                finish();
                break;
        }
    }

    private ArrayList<ChargeTaskConfirmBean> initNetWork() {
        chargeTaskConfirmBeanArrayList.add(new ChargeTaskConfirmBean(true, 1, "XW20180414"));
        chargeTaskConfirmBeanArrayList.add(new ChargeTaskConfirmBean(true, 2, "XW20180418"));
        return chargeTaskConfirmBeanArrayList;
    }


}
