package com.example.administrator.landapplication.ui.dialog;

import android.content.Context;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseDialog;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.model.rxtools.RxBus;
import com.example.administrator.landapplication.utils.SystemUtil;

import butterknife.BindView;


/**
 * Created by Administrator on 2018/3/20.
 */

public class StatusDialog extends BaseDialog implements View.OnClickListener {

    @BindView(R.id.btn_cancle)
    Button btn_cancle;
    @BindView(R.id.btn_commit)
    Button btn_commit;
    @BindView(R.id.tv_content)
    TextView tv_content;
    private Context context;
    private int status;
    private String content;

    protected StatusDialog(Context context, @StyleRes int themeResId, int status, String content) {
        super(context, themeResId);
        this.context = context;
        this.status = status;
        this.content = content;
    }


    @Override
    protected int getLayout() {
        return R.layout.dialog_status;
    }

    @Override
    protected void initClass() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        btn_cancle.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancle:
                dismiss();
                break;
            case R.id.btn_commit:
                RxBus.getDefault().post(new CommonEvent(status));
                dismiss();
                break;
        }
    }

    @Override
    protected void initView() {
        tv_content.setText(content);
    }

    @Override
    protected void onDismissListener() {
        super.onDismissListener();
        SystemUtil.windowToLight(context);
    }


}
