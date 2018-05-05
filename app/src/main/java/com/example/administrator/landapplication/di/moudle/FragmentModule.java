package com.example.administrator.landapplication.di.moudle;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.example.administrator.landapplication.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2018/2/11.
 */
@Module
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}
