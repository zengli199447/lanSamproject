package com.example.administrator.landapplication.ui.activity.office;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.bean.ElementsTableBean;
import com.example.administrator.landapplication.global.GlobalNetWorkManager;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.NetEventCode;
import com.example.administrator.landapplication.soaptools.UrlList;
import com.example.administrator.landapplication.ui.adapter.ElementsTableAdapter;
import com.example.administrator.landapplication.ui.dialog.ProgressDialog;
import com.example.administrator.landapplication.ui.dialog.ShowDialog;
import com.example.administrator.landapplication.ui.view.AutoListView;
import com.example.administrator.landapplication.utils.LogUtil;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/16.
 * 样品详情
 */

public class DetailsSampleActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.details_list)
    AutoListView details_list;
    @BindView(R.id.commit)
    TextView commit;
    @BindView(R.id.refuse_to_accept)
    TextView refuse_to_accept;
    @BindView(R.id.the_sample_type)
    EditText the_sample_type;
    @BindView(R.id.empty_input_view)
    View empty_input_view;
    private ArrayList<ElementsTableBean> elementsTableBeenList = new ArrayList<>();
    private ElementsTableAdapter elementsTableAdapter;
    private String beforeText;
    private ShowDialog showDialog;
    private ProgressDialog progressDialog;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case NetEventCode.NETWORK_GET_SAMPLERECEIVEINFO:
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
        return R.layout.activity_details_sample;
    }

    @Override
    protected void initClass() {
        showDialog = ShowDialog.newInstance();
        progressDialog = showDialog.showProgressStatus(this, getString(R.string.progress));
        elementsTableAdapter = new ElementsTableAdapter(elementsTableBeenList, this);
    }

    @Override
    protected void initData() {
        String qrCode = getIntent().getStringExtra("qrCode");
        initNetData(qrCode);
        initNetData();
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.sample_details));
        img_btn_black.setVisibility(View.VISIBLE);
        details_list.setFocusable(false);
        details_list.setAdapter(elementsTableAdapter);
        elementsTableAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
        commit.setOnClickListener(this);
        refuse_to_accept.setOnClickListener(this);
        empty_input_view.setOnClickListener(this);
        the_sample_type.addTextChangedListener(TextChangedListener);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.commit:

                finish();
                break;
            case R.id.refuse_to_accept:

                finish();
                break;
            case R.id.empty_input_view:
                the_sample_type.setText("");
                break;
        }
    }

    private TextWatcher TextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            beforeText = charSequence.toString();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.toString().isEmpty()) {
                empty_input_view.setVisibility(View.INVISIBLE);
            } else {
                empty_input_view.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void initNetData() {
        elementsTableBeenList.add(new ElementsTableBean("银", 6, 0));
        elementsTableBeenList.add(new ElementsTableBean("二氧化硅", 6, 0));
        elementsTableBeenList.add(new ElementsTableBean("有机物含量", 2, 1));
    }

    private void initNetData(String qrCode) {
        progressDialog.show();
        Map<String, Object> param = new HashMap<>();
        param.put("receiveID", "");
        GlobalNetWorkManager.getNetWorkData(param, "getSampleReceiveInfoByID", UrlList.MONITORING_CENTER_LIST_RECEPTION, NetEventCode.NETWORK_GET_SAMPLERECEIVEINFO, this);

    }


}
