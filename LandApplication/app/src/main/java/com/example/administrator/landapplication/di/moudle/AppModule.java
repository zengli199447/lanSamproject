package com.example.administrator.landapplication.di.moudle;

import android.content.Context;

import com.example.administrator.landapplication.model.DataManager;
import com.example.administrator.landapplication.model.db.DBHelper;
import com.example.administrator.landapplication.model.db.GreenDaoHelper;
import com.example.administrator.landapplication.model.http.HttpHelper;
import com.example.administrator.landapplication.model.http.RetrofitHelper;
import com.example.administrator.landapplication.utils.ToastUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;



/**
 * Created by Administrator on 2017/10/27.
 */
@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return this.context;
    }

    @Singleton
    @Provides
    ToastUtil provideToastUtil() {
        return new ToastUtil(this.context);
    }


    @Singleton
    @Provides
    DataManager provideDataManager(DBHelper DBHelper, HttpHelper httpHelper) {
        return new DataManager(DBHelper,httpHelper);
    }

    @Provides
    @Singleton
    DBHelper provideDBHelper(GreenDaoHelper realmHelper) {
        return realmHelper;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper(RetrofitHelper retrofitHelper) {
        return retrofitHelper;
    }

}
