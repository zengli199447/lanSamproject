package com.example.administrator.landapplication.ui.dialog;

import android.content.Context;
import android.support.annotation.StyleRes;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseDialog;
import com.example.administrator.landapplication.bean.UserBean;
import com.example.administrator.landapplication.model.DataManager;
import com.example.administrator.landapplication.model.db.entity.LoginUserInfo;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.model.rxtools.RxBus;
import com.example.administrator.landapplication.utils.LogUtil;
import com.example.administrator.landapplication.utils.SystemUtil;
import com.example.administrator.landapplication.utils.ToastUtil;

import butterknife.BindView;


/**
 * Created by Administrator on 2018/3/21.
 */

public class PasswordDialog extends BaseDialog implements View.OnClickListener {

    private Context context;
    private DataManager mDataManager;
    @BindView(R.id.btn_cancle)
    Button btn_cancle;
    @BindView(R.id.btn_commit)
    Button btn_commit;
    @BindView(R.id.old_password)
    EditText old_password;
    @BindView(R.id.new_password)
    EditText new_password;
    @BindView(R.id.confirm_password)
    EditText confirm_password;
    @BindView(R.id.empty_old_view)
    View empty_old_view;
    @BindView(R.id.empty_new_view)
    View empty_new_view;
    @BindView(R.id.empty_confirm_view)
    View empty_confirm_view;
    @BindView(R.id.password_check)
    AppCompatCheckBox password_check;
    private LoginUserInfo loginUserInfos;
    private String oldPassWord;
    private String newPassWord;
    private String beforeText;
    private String confirmPassWord;
    private ToastUtil toastUtil;


    protected PasswordDialog(Context context, @StyleRes int themeResId, DataManager mDataManager) {
        super(context, themeResId);
        this.context = context;
        this.mDataManager = mDataManager;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_password;
    }

    @Override
    protected void initClass() {
        loginUserInfos = mDataManager.queryLoginUserInfo(UserBean.userName);
        toastUtil = new ToastUtil(context);
    }

    @Override
    protected void initData() {
        btn_commit.setText("修改");

    }

    @Override
    protected void initView() {
        AllchageListener(old_password, empty_old_view);
        AllchageListener(new_password, empty_new_view);
        AllchageListener(confirm_password, empty_confirm_view);

    }

    @Override
    protected void initListener() {
        btn_cancle.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
        empty_old_view.setOnClickListener(this);
        empty_new_view.setOnClickListener(this);
        empty_confirm_view.setOnClickListener(this);
        password_check.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancle:
                dismiss();
                break;
            case R.id.btn_commit:
                if (EditVerdict()) {
                    RxBus.getDefault().post(new CommonEvent(EventCode.COMMITE_UPPASSWORD));
                    dismiss();
                }
                break;
            case R.id.empty_old_view:
                old_password.setText("");
                break;
            case R.id.empty_new_view:
                new_password.setText("");
                break;
            case R.id.empty_confirm_view:
                confirm_password.setText("");
                break;
            case R.id.password_check:
                int type = InputType.TYPE_CLASS_TEXT;
                if (!password_check.isChecked()) {
                    type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                }
                old_password.setInputType(type);
                new_password.setInputType(type);
                confirm_password.setInputType(type);
                break;

        }

    }

    private boolean EditVerdict() {
        boolean status = false;
        String currentPassWord = loginUserInfos.getPassword();
        LogUtil.e(TAG, "currentPassWord : " + currentPassWord);
        oldPassWord = old_password.getText().toString().trim();
        newPassWord = new_password.getText().toString().trim();
        confirmPassWord = confirm_password.getText().toString().trim();
        if (!SystemUtil.isPwdLegal(oldPassWord) || !SystemUtil.isPwdLegal(newPassWord) || !SystemUtil.isPwdLegal(confirmPassWord)) {
            toastUtil.showToast("密码输入格式不正确");
            status = false;
        } else {
            if (!currentPassWord.equals(oldPassWord)) {
                toastUtil.showToast("原密码错误，请重新输入");
            } else if (oldPassWord.equals(newPassWord)) {
                toastUtil.showToast("新旧密码不可相同");
            } else if (!newPassWord.equals(confirmPassWord)) {
                toastUtil.showToast("两次输入不同，请重新输入");
            } else {
                status = true;
            }
        }
        return status;
    }


    private void AllchageListener(final EditText editText, final View view) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                beforeText = charSequence.toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.toString().isEmpty()) {
                    view.setVisibility(View.INVISIBLE);
                } else {
                    view.setVisibility(View.VISIBLE);
                }
                // 输入后的字符
                String afterText = charSequence.toString();
                boolean isValid = true;
                if (!TextUtils.isEmpty(afterText)) {
                    isValid = SystemUtil.isPasswordLegal(afterText);
                    if (!isValid) {
                        // 用户现在输入的字符数减去之前输入的字符数，等于新增的字符数
                        int differ = afterText.length() - beforeText.length();
                        // 如果用户的输入不符合规范，则显示之前输入的文本
                        editText.setText(beforeText);
                        // 光标移动到文本末尾
                        editText.setSelection(afterText.length() - differ);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void onDismissListener() {
        super.onDismissListener();
        SystemUtil.windowToLight(context);
    }

}
