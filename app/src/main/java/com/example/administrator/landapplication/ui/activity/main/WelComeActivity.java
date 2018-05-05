package com.example.administrator.landapplication.ui.activity.main;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;


import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.global.MyApplication;
import com.example.administrator.landapplication.model.event.CommonEvent;

import butterknife.BindView;


/**
 * Created by Administrator on 2018/3/16.
 */

public class WelComeActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.text_login)
    TextView text_login;
    private int ss = 3;
    private boolean status = true;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (text_login != null)
                text_login.setText(String.valueOf(ss));
        }
    };


    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()){
            case 0:
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initClass() {
        ssOrRun();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        text_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_login:
                status = false;
                startActivity(new Intent(WelComeActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    private void ssOrRun() {
        MyApplication.executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(999);
                    handler.sendEmptyMessage(0);
                    ss--;
                    if (ss > 0) {
                        ssOrRun();
                    } else {
                        if (status) {
                            startActivity(new Intent(WelComeActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
