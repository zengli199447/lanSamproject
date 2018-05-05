package com.example.administrator.landapplication.di.component;

import android.app.Activity;


import com.example.administrator.landapplication.di.moudle.FragmentModule;
import com.example.administrator.landapplication.di.scope.FragmentScope;

import dagger.Component;



@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

//    void inject(MessageFragment messageFragment);




}
