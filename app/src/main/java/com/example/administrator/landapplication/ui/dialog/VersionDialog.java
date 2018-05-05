package com.example.administrator.landapplication.ui.dialog;

import android.content.Context;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseDialog;
import com.example.administrator.landapplication.model.DataManager;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.model.rxtools.RxBus;
import com.example.administrator.landapplication.utils.SystemUtil;
import com.example.administrator.landapplication.utils.ToastUtil;

import butterknife.BindView;


/**
 * Created by Administrator on 2018/3/21.
 */

public class VersionDialog extends BaseDialog implements View.OnClickListener {

    private Context context;
    private String content;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.btn_cancle)
    Button btn_cancle;
    @BindView(R.id.btn_commit)
    Button btn_commit;
    @BindView(R.id.line)
    View line;
    private String status;

    protected VersionDialog(Context context, @StyleRes int themeResId, String content) {
        super(context, themeResId);
        this.context = context;
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
        if (content.equals(SystemUtil.getAppInfo(context))) {
            tv_content.setText("当前已是最新版本！");
            btn_commit.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        } else {
            tv_content.setText(new StringBuffer().append("当前最新版本 ：").append(content));
            if (SystemUtil.isWifiConnected()) {
                status = "Wifi";
            } else if (SystemUtil.isMobileNetworkConnected()) {
                status = "移动网络";
            }
            btn_commit.setText(new StringBuffer().append(status).append("更新"));
        }
    }

    @Override
    protected void initView() {

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
//                new ToastUtil(context).showToast("当前已是最新版本！");
                RxBus.getDefault().post(new CommonEvent(EventCode.COMMITE_REFRESH));
                dismiss();
                break;
        }

    }

    @Override
    protected void onDismissListener() {
        super.onDismissListener();
        SystemUtil.windowToLight(context);
    }

}
