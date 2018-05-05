package com.example.administrator.landapplication.ui.activity.tools;

import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.bean.AboutDataBean;
import com.example.administrator.landapplication.global.MyApplication;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.ui.adapter.MatchedPrinterAdapter;
import com.example.administrator.landapplication.ui.dialog.ProgressDialog;
import com.example.administrator.landapplication.ui.dialog.ShowDialog;
import com.example.administrator.landapplication.ui.view.AutoListView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/23.
 */

public class BluetoothPairingActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @BindView(R.id.cb_select)
    CheckBox cb_select;
    @BindView(R.id.according_printer)
    TextView according_printer;
    @BindView(R.id.unpaired_equipment_list)
    ListView unpaired_equipment_list;
    @BindView(R.id.matched_printer_list)
    ListView matched_printer_list;
    @BindView(R.id.end_the_use_of_printers)
    TextView end_the_use_of_printers;

    private double exitTime = 0;
    private ArrayList<AboutDataBean> unpairedEquipmentList = new ArrayList<>();
    private ArrayList<AboutDataBean> matchedPrinterList = new ArrayList<>();
    private MatchedPrinterAdapter unpairedAdapter;
    private MatchedPrinterAdapter matchedAdapter;
    private AboutDataBean aboutDataBean;
    private ShowDialog showDialog;
    private ProgressDialog progressDialog;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.OPENBLUETOOTH:
                initBlueToothPairing();
                break;
        }

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_bluetooth_pairing;
    }

    @Override
    protected void initClass() {
        showDialog = ShowDialog.newInstance();
        unpairedAdapter = new MatchedPrinterAdapter(unpairedEquipmentList, this);
        matchedAdapter = new MatchedPrinterAdapter(matchedPrinterList, this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        unpaired_equipment_list.setAdapter(unpairedAdapter);
        matched_printer_list.setAdapter(matchedAdapter);
    }

    @Override
    protected void initListener() {
        unpaired_equipment_list.setOnItemClickListener(this);
        matched_printer_list.setOnItemClickListener(this);
        end_the_use_of_printers.setOnClickListener(this);
        according_printer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.end_the_use_of_printers:
                finish();
                break;
            case R.id.according_printer:
                showDialog.showStatus(this, EventCode.OPENBLUETOOTH, getString(R.string.open_tooth));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        aboutDataBean = unpairedEquipmentList.get(i);
        initPrintersTooth(aboutDataBean);
        progressLogin(matchedAdapter, getString(R.string.in_the_connection));
    }

    private void initBlueToothPairing() {
        unpairedEquipmentList.add(new AboutDataBean("设备蓝牙打印机", 0));
        unpairedEquipmentList.add(new AboutDataBean("设备蓝牙播放器", 0));
        unpairedEquipmentList.add(new AboutDataBean("K2680 - 蓝牙打印机", 0));
        progressLogin(unpairedAdapter, getString(R.string.open_ing).toString());
    }

    private void initPrintersTooth(AboutDataBean aboutDataBean) {
        matchedPrinterList.clear();
        matchedPrinterList.add(aboutDataBean);
    }

    private void progressLogin(final MatchedPrinterAdapter matchedAdapter, String content) {
        progressDialog = showDialog.showProgressStatus(this, content);
        progressDialog.show();
        MyApplication.executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    progressDialog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            matchedAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            toastUtil.showToast(getString(R.string.finish_blue_tooth));
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }


}
