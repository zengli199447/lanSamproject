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
import com.example.administrator.landapplication.ui.view.RangeSeekBar;
import com.example.administrator.landapplication.utils.SystemUtil;

import butterknife.BindView;




/**
 * Created by Administrator on 2018/3/21.
 */

public class PushDialog extends BaseDialog implements View.OnClickListener, RangeSeekBar.OnRangeChangedListener {

    private Context context;
    private DataManager mDataManager;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.btn_cancle)
    Button btn_cancle;
    @BindView(R.id.btn_commit)
    Button btn_commit;
    @BindView(R.id.range_seekbar)
    RangeSeekBar range_seekbar;
    private int start;
    private int end;
    private AppConfigurationInfo appConfigurationInfo;

    protected PushDialog(Context context, @StyleRes int themeResId, DataManager mDataManager) {
        super(context, themeResId);
        this.context = context;
        this.mDataManager = mDataManager;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_push_setting;
    }

    @Override
    protected void initClass() {

    }

    @Override
    protected void initData() {
        appConfigurationInfo = mDataManager.queryAppConfigurationInfo(UserBean.userName);
        start = appConfigurationInfo.getStartPush();
        end = appConfigurationInfo.getEndPush();


    }

    @Override
    protected void initView() {
        tv_content.setText("推送时间 (默认24小时开启)");
        range_seekbar.setValue(start, end); //参数根据默认或此前修改的时间段取，数据库中存
        range_seekbar.setRules(0, 24, 1, 1);
    }

    @Override
    protected void initListener() {
        btn_cancle.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
        range_seekbar.setOnRangeChangedListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancle:
                dismiss();
                break;
            case R.id.btn_commit:
                mDataManager.UpDataAppConfigurationInfo(new AppConfigurationInfo(appConfigurationInfo.getId(), appConfigurationInfo.getCacheName(), start, end, appConfigurationInfo.getCurrentUser()));
                RxBus.getDefault().post(new CommonEvent(EventCode.COMMITE_REFRESH));
                dismiss();
                break;
        }
    }

    @Override
    public void onRangeChanged(RangeSeekBar view, float min, float max) {
        //采用round方式转换为整型
        start = Math.round(min);
        //采用round方式转换为整型
        end = Math.round(max);
        tv_content.setText(new StringBuffer().append(start).append("时").append(" - ").append(end).append("时"));
    }


    @Override
    protected void onDismissListener() {
        super.onDismissListener();
        SystemUtil.windowToLight(context);
    }

}
