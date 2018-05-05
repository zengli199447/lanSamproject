package com.example.administrator.landapplication.ui.activity.main;


import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.global.MyApplication;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.ui.dialog.ProgressDialog;
import com.example.administrator.landapplication.ui.dialog.ShowDialog;

/**
 * 错误上报
 */

public class MainActivity extends BaseActivity {

    private ShowDialog showDialog;
    private ProgressDialog progressDialog;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initClass() {
        showDialog = ShowDialog.newInstance();
        progressDialog = showDialog.showProgressStatus(this, "正在关闭应用 ...");
        progressDialog.show();
        MyApplication.executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    progressDialog.dismiss();
                    MyApplication.exitApp();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

}
