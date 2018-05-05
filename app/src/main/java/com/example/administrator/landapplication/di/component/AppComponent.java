package com.example.administrator.landapplication.di.component;

import android.content.Context;

import com.example.administrator.landapplication.di.moudle.AppModule;
import com.example.administrator.landapplication.di.moudle.HttpModule;
import com.example.administrator.landapplication.model.DataManager;
import com.example.administrator.landapplication.model.db.GreenDaoHelper;
import com.example.administrator.landapplication.model.http.RetrofitHelper;
import com.example.administrator.landapplication.utils.ToastUtil;

import javax.inject.Singleton;

import dagger.Component;



/**
 * Created by Administrator on 2017/10/27.
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    Context getContext();

    ToastUtil getToastUtil();

    DataManager getDataManager(); //数据中心

    GreenDaoHelper greenDaoHelper();    //提供数据库帮助类

    RetrofitHelper getRetrofitHelper();  //提供http的帮助类

}
