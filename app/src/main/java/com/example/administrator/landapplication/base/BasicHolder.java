package com.example.administrator.landapplication.base;

import android.view.View;

/**
 * Created by Administrator on 2018/4/3.
 * 通用Holder （建议多类型使用，便于管理）
 */

public abstract class BasicHolder<T> {

    protected String TAG = getClass().getSimpleName();

    public View holderView;

    public BasicHolder() {
        holderView = initHolderView();

        holderView.setTag(this);
    }

    public abstract View initHolderView();


    public abstract void bindData(T data);

    public View getHolderView() {
        return holderView;
    }
}
