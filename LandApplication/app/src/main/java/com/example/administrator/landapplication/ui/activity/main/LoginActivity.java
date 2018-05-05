package com.example.administrator.landapplication.ui.activity.main;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BaseActivity;
import com.example.administrator.landapplication.bean.OuterLayerEntity;
import com.example.administrator.landapplication.bean.UserAboutBean;
import com.example.administrator.landapplication.bean.UserBean;
import com.example.administrator.landapplication.global.AppConfiguration;
import com.example.administrator.landapplication.global.DataClass;
import com.example.administrator.landapplication.global.GlobalNetWorkManager;
import com.example.administrator.landapplication.model.db.entity.LoginUserInfo;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.NetEventCode;
import com.example.administrator.landapplication.model.rxtools.RxUtil;
import com.example.administrator.landapplication.soaptools.UrlList;
import com.example.administrator.landapplication.ui.activity.office.MonitoringCenterActivity;
import com.example.administrator.landapplication.ui.dialog.ProgressDialog;
import com.example.administrator.landapplication.ui.dialog.ShowDialog;
import com.example.administrator.landapplication.utils.SystemUtil;
import com.example.administrator.landapplication.widget.CommonSubscriber;
import com.oridway.oamessager.AESEncryption;


import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;


/**
 * Created by Administrator on 2018/3/16.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.edit_user)
    EditText edit_user;
    @BindView(R.id.edit_password)
    EditText edit_password;
    @BindView(R.id.delet_username_view)
    View delet_username_view;
    @BindView(R.id.delet_password_view)
    View delet_password_view;
    @BindView(R.id.button_login)
    TextView button_login;
    @BindView(R.id.version)
    TextView version;
    private String beforeText;
    private String userName;
    private String passWord;
    private String privKey = "this is a key!lw";
    private DataClass dataClass;
    private ShowDialog showDialog;
    private ProgressDialog progressDialog;
    private int currentTime;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case NetEventCode.NETWORK_LOGIN:
                SoapObject result = (SoapObject) commonevent.getObject();
                SuccessResult((SoapObject) result.getProperty(0));
                break;
            case NetEventCode.NETWORK_ERROR:
                toastUtil.showToast(commonevent.getTemp_str());
                progressDialog.dismiss();
                break;
            case NetEventCode.NETWORK_USERABOUTINFO:
                SoapObject soapObject = (SoapObject)((SoapObject) commonevent.getObject()).getProperty(0);
                new GlobalNetWorkManager<UserAboutBean>().StaticNetBean(soapObject, new UserAboutBean());
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initClass() {
        dataClass = new DataClass(dataManager);
        showDialog = ShowDialog.newInstance();
        progressDialog = showDialog.showProgressStatus(this, getString(R.string.login_progress));
        currentTime = (int) System.currentTimeMillis();
    }

    @Override
    protected void initData() {
        if (dataClass.GetCurrentUser() != null) {
            LoginUserInfo loginUserInfo = dataClass.GetCurrentUser();
            edit_user.setText(loginUserInfo.getUsername());
            edit_password.setText(loginUserInfo.getPassword());
            delet_username_view.setVisibility(View.VISIBLE);
            delet_password_view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initView() {
        AllChageListener(edit_user, delet_username_view);
        AllChageListener(edit_password, delet_password_view);
        version.setText(new StringBuffer().append("当前版本 ：").append(SystemUtil.getAppInfo(this)));
    }

    @Override
    protected void initListener() {
        delet_username_view.setOnClickListener(this);
        delet_password_view.setOnClickListener(this);
        button_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delet_username_view:
                edit_user.setText("");
                break;
            case R.id.delet_password_view:
                edit_password.setText("");
                break;
            case R.id.button_login:
                userName = edit_user.getText().toString().trim();
                passWord = edit_password.getText().toString().trim();
                if (!SystemUtil.isPwdLegal(passWord)) {
                    toastUtil.showToast("密码输入格式不正确");
                } else {
                    progressDialog.ShowDiaLog();
                    LoginSetting();
                }
                break;
        }
    }

    //网络请求
    private void LoginSetting() {
        String ip = SystemUtil.getLocalIpAddress();
        String pwdEncry = AESEncryption.toHexString(AESEncryption.AESEncrypt(passWord, privKey));
        Map<String, Object> param = new HashMap<>();
        param.put("userName", userName);
        param.put("passWord", pwdEncry);
        param.put("myIP", ip);
        param.put("loginMode", 3);
        GlobalNetWorkManager.getNetWorkData(param, "login", UrlList.LOGIN, NetEventCode.NETWORK_LOGIN, this);
    }

    //请求返回体
    private void SuccessResult(SoapObject result) {
        new GlobalNetWorkManager<UserBean>().StaticNetBean(result, new UserBean());
        if (UserBean.loginMessage.length() > 0) { //成功登录。
            if (UserBean.loginMessage.equals("成功登录。")) {
                dataClass.DbCurrentUser(userName, passWord, currentTime);
                toastUtil.showToast("登录成功");            // HomeActivity
                new AppConfiguration().ACInstance(dataManager, UserBean.userName);
                getUserAboutInfo();
            } else if (UserBean.loginMessage.equals("该用户还没有激活！")) {
                toastUtil.showToast("该用户还没有激活");
            } else {
                toastUtil.showToast("登录失败,帐号或密码错误");
            }
        } else {
            toastUtil.showToast("服务器异常");
        }
        progressDialog.dismiss();
    }

    //输入监听
    private void AllChageListener(final EditText editText, final View view) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                beforeText = charSequence.toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    view.setVisibility(View.INVISIBLE);
                } else {
                    view.setVisibility(View.VISIBLE);
                } // 输入后的字符
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
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    //个人其他信息
    private void getUserAboutInfo() {
        Map<String, Object> param = new HashMap<>();
        param.put("isLoadRight", "No");
        param.put("subSystemID", "");
        GlobalNetWorkManager.getNetWorkData(null, UrlList.UserSelfInfo, UrlList.USERMANAGER, NetEventCode.NETWORK_USERABOUTINFO, this);
    }

    public void httpLoad() {
        Map map = new HashMap();
        map.put("cmd", "CLIENT_USER_LOGIN");
        map.put("userid", "admin");
        map.put("pwd", "admin123456");
        map.put("deviceType", "mobile");
        addSubscribe(dataManager.UpLoadAbout(map)
                .compose(RxUtil.<OuterLayerEntity>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<OuterLayerEntity>(toastUtil) {
                    @Override
                    public void onNext(OuterLayerEntity outerLayerEntity) {
                        Log.e(TAG, "OuterLayerEntity : " + outerLayerEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);

                    }
                }));

    }


}
