package com.example.administrator.landapplication.ui.activity.office;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.bean.SubmissionListBean;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.ui.adapter.ChargeTaskAdapter;
import com.example.administrator.landapplication.ui.view.AutoListView;
import com.example.administrator.landapplication.utils.SystemUtil;
import com.example.administrator.landapplication.widget.SpannableBuilder;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/16.
 * 实验室签收列表
 */

public class ChargeTaskActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.laboratory)
    TextView laboratory;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.consignee)
    TextView consignee;
    @BindView(R.id.charge_list)
    AutoListView charge_list;
    private ArrayList<SubmissionListBean> submissionLists = new ArrayList<>();
    private ChargeTaskAdapter chargeTaskAdapter;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_charge;
    }

    @Override
    protected void initClass() {
        chargeTaskAdapter = new ChargeTaskAdapter(submissionLists, this);
    }

    @Override
    protected void initData() {
        initNetWork();

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.the_goods_list));
        img_btn_black.setVisibility(View.VISIBLE);
        time.setText(SystemUtil.initDate(Calendar.getInstance()));
        textMagicTool(time, getString(R.string.time), SystemUtil.initDate(Calendar.getInstance()));
        textMagicTool(laboratory, getString(R.string.laboratory), "叙利亚");
        textMagicTool(consignee, getString(R.string.consignee), "普京");
        charge_list.setAdapter(chargeTaskAdapter);
        chargeTaskAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
        charge_list.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        SubmissionListBean submissionList = submissionLists.get(i);
//        toastUtil.showToast(submissionList.getConsignor());
        Intent intent = new Intent(this, ChargeTaskContentActivity.class);
        intent.putExtra("deliveryNumber",submissionList.getDeliveryNumber());
        startActivity(intent);
    }

    private ArrayList<SubmissionListBean> initNetWork() {
        submissionLists.add(new SubmissionListBean("XW20180401235", "20180414", "王猛"));
        submissionLists.add(new SubmissionListBean("XW20180401235", "20180414", "宋江"));
        return submissionLists;
    }


    private void textMagicTool(TextView view, String firstText, String lastText) {
        if (lastText != null && !lastText.isEmpty())
            view.setText(SpannableBuilder.create(this)
                    .append(firstText, R.dimen.text_content_size, R.color.black).
                            append(new StringBuffer().append(" ").append(lastText).toString(),
                                    R.dimen.text_content_size, R.color.grey).build());
    }

}
