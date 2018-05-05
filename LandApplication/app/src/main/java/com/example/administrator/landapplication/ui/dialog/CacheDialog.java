package com.example.administrator.landapplication.ui.dialog;

import android.content.Context;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseDialog;
import com.example.administrator.landapplication.bean.UserBean;
import com.example.administrator.landapplication.model.DataManager;
import com.example.administrator.landapplication.model.db.entity.AppConfigurationInfo;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.model.rxtools.RxBus;
import com.example.administrator.landapplication.utils.SystemUtil;

import java.io.File;


import butterknife.BindView;


/**
 * Created by Administrator on 2018/3/21.
 */

public class CacheDialog extends BaseDialog implements View.OnClickListener {

    private Context context;
    private DataManager mDataManager;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.btn_cancle)
    Button btn_cancle;
    @BindView(R.id.btn_commit)
    Button btn_commit;
    private String cacheName;

    protected CacheDialog(Context context, @StyleRes int themeResId, DataManager mDataManager) {
        super(context, themeResId);
        this.context = context;
        this.mDataManager = mDataManager;
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
        AppConfigurationInfo appConfigurationInfo = mDataManager.queryAppConfigurationInfo(UserBean.userName);
        cacheName = appConfigurationInfo.getCacheName();
        tv_content.setText(new StringBuffer().append("当前缓存 ：").append(SystemUtil.getFormatSize(SystemUtil.getFolderSize(new File(cacheName)))));
        btn_commit.setText("清理");
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
                SystemUtil.deleteFolderFile(cacheName, false);
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
