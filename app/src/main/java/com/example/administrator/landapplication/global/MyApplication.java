package com.example.administrator.landapplication.global;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.di.component.AppComponent;
import com.example.administrator.landapplication.di.component.DaggerAppComponent;
import com.example.administrator.landapplication.di.moudle.AppModule;
import com.example.administrator.landapplication.server.InitializeService;
import com.luoxudong.app.threadpool.ThreadPoolHelp;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;


/**
 * Created by Administrator on 2018/3/16.
 */

public class MyApplication extends Application {

    public static MyApplication instance;

    public static AppComponent appComponent;
    private static Set<Activity> allActivities;

    public String TAG = "MyApplication";
    public static ExecutorService executorService;

    public static synchronized MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initThreadTools();
        InitializeService.start(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);

    }

    private void initThreadTools() {
        executorService = ThreadPoolHelp.Builder.fixed(6)
                .name("globalTask")
                .builder();
    }


    public static AppComponent getAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();
        return appComponent;
    }

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    //自杀
    public static void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
