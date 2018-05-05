package com.example.administrator.landapplication.widget;

import android.text.TextUtils;

import com.example.administrator.landapplication.utils.LogUtil;
import com.example.administrator.landapplication.utils.ToastUtil;

import io.reactivex.subscribers.ResourceSubscriber;


/**
 * Created by Administrator on 2017/10/31.
 */

public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private ToastUtil toastUtil;
    private String mErrorMsg;
    private boolean isShowErrorState = true;

    protected CommonSubscriber(ToastUtil toastUtil){
        this.toastUtil = toastUtil;
    }

    protected CommonSubscriber(ToastUtil toastUtil, String errorMsg){
        this.toastUtil = toastUtil;
        this.mErrorMsg = errorMsg;
    }

    protected CommonSubscriber(ToastUtil toastUtil, boolean isShowErrorState){
        this.toastUtil = toastUtil;
        this.isShowErrorState = isShowErrorState;
    }

    protected CommonSubscriber(ToastUtil toastUtil, String errorMsg, boolean isShowErrorState){
        this.toastUtil = toastUtil;
        this.mErrorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        if (toastUtil == null) {
            return;
        }
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            toastUtil.showToast(mErrorMsg);
        } else {
            toastUtil.showToast("数据加载失败ヽ(≧Д≦)ノ");
            LogUtil.e("CommonSubscriber",e.toString());
        }
        if (isShowErrorState) {
//            toastUtil.showToast("未知异常");
        }
    }
}
