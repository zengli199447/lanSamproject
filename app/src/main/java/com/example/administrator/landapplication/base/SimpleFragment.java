package com.example.administrator.landapplication.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2018/3/19.
 */

public abstract class SimpleFragment extends SupportFragment {

    protected String TAG = getClass().getSimpleName();

    private Activity mActivity;
    private Context mContext;
    private View mView;
    private int layoutId;
    private Unbinder mUnBinder;
    protected boolean isInited = false;


    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        isInited = true;
        initClass();
        initData();
        initView();
        initListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
        onUnSubscribe();
        onTheCustom();
    }

    protected abstract int getLayoutId();

    protected abstract void initClass();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void initListener();

    protected void onTheCustom() {

    }

    protected void onUnSubscribe() {

    }

}
