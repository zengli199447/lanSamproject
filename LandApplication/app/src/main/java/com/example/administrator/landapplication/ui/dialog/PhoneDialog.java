package com.example.administrator.landapplication.ui.dialog;

import android.content.Context;
import android.support.annotation.StyleRes;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class PhoneDialog extends BaseDialog implements View.OnClickListener {

    private Context context;
    private DataManager mDataManager;
    @BindView(R.id.phone_edit)
    EditText phone_edit;
    @BindView(R.id.btn_cancle)
    Button btn_cancle;
    @BindView(R.id.btn_commit)
    Button btn_commit;
    @BindView(R.id.empty_phone_view)
    View empty_phone_view;
    private ToastUtil toastUtil;

    protected PhoneDialog(Context context, @StyleRes int themeResId, DataManager mDataManager) {
        super(context, themeResId);
        this.context = context;
        this.mDataManager = mDataManager;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_phone;
    }

    @Override
    protected void initClass() {
        toastUtil = new ToastUtil(context);
    }

    @Override
    protected void initData() {
        btn_commit.setText("更新");
    }

    @Override
    protected void initView() {
        phone_edit.setText("15628874187");
        phone_edit.addTextChangedListener(TextChangedListener);
    }

    @Override
    protected void initListener() {
        btn_cancle.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
        empty_phone_view.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancle:
                dismiss();
                break;
            case R.id.btn_commit:
                if (UpPhoneNumber(phone_edit.getText().toString().trim())) {
                    toastUtil.showToast("暂未开放");
                    RxBus.getDefault().post(new CommonEvent(EventCode.COMMITE_UPPHONENUMBER));
                    dismiss();
                }
                break;
            case R.id.empty_phone_view:
                phone_edit.setText("");
                break;
        }

    }

    private String beforeText;
    private TextWatcher TextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            beforeText = charSequence.toString();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.toString().isEmpty()) {
                empty_phone_view.setVisibility(View.INVISIBLE);
            } else {
                empty_phone_view.setVisibility(View.VISIBLE);
            }
            String afterText = charSequence.toString();
            boolean isValid = true;
            if (!TextUtils.isEmpty(afterText)) {
                isValid = SystemUtil.isPasswordLegal(afterText);
                if (!isValid) {
                    int differ = afterText.length() - beforeText.length();
                    phone_edit.setText(beforeText);
                    phone_edit.setSelection(afterText.length() - differ);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public boolean UpPhoneNumber(String newPhoneNumber) {
        boolean status = false;
        if (!SystemUtil.isPhotoNumberLegal(newPhoneNumber)) {
            toastUtil.showToast("电话号码输入格式有误");
        } else {
            status = true;
        }
        return status;
    }

    @Override
    protected void onDismissListener() {
        super.onDismissListener();
        SystemUtil.windowToLight(context);
    }

}
