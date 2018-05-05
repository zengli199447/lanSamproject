package com.example.administrator.landapplication.ui.dialog;

import android.content.Context;
import android.support.annotation.StyleRes;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import static com.example.administrator.landapplication.R.id.phone_edit;


/**
 * Created by Administrator on 2018/3/21.
 * 类型选择
 */

public class SelectTypeDialog extends BaseDialog implements View.OnClickListener {

    private Context context;
    private DataManager mDataManager;
    private String status;
    @BindView(R.id.btn_cancle)
    Button btn_cancle;
    @BindView(R.id.btn_commit)
    Button btn_commit;
    @BindView(R.id.about)
    TextView about;
    private ToastUtil toastUtil;

    protected SelectTypeDialog(Context context, @StyleRes int themeResId, DataManager mDataManager, String status) {
        super(context, themeResId);
        this.context = context;
        this.mDataManager = mDataManager;
        this.status = status;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_select_type;
    }

    @Override
    protected void initClass() {
        toastUtil = new ToastUtil(context);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        if (status.equals(R.string.land_use))
            toastUtil.showToast("");

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
                RxBus.getDefault().post(new CommonEvent(EventCode.COMMITE_UPPHONENUMBER));
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
