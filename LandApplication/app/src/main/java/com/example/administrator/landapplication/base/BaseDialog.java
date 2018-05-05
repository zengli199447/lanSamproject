package com.example.administrator.landapplication.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/21.
 */

public abstract class BaseDialog extends AlertDialog {

    private Unbinder mUnBinder;

    protected String TAG = getClass().getSimpleName();

    protected BaseDialog(Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);//此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = window.getAttributes();
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        params.width = (int) (window.getWindowManager().getDefaultDisplay().getWidth() * 0.82);//设置宽度，填充屏幕
        window.setAttributes(params);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                mUnBinder.unbind();
                onDismissListener();
            }
        });
        initClass();
        initData();
        initView();
        initListener();

    }

    protected abstract int getLayout();

    protected abstract void initClass();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void initListener();

    protected void onDismissListener() {

    }


}
