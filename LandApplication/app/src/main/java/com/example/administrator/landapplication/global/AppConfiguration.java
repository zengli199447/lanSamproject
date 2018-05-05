package com.example.administrator.landapplication.global;

import com.example.administrator.landapplication.model.DataManager;
import com.example.administrator.landapplication.model.db.entity.AppConfigurationInfo;
import com.example.administrator.landapplication.utils.LogUtil;
import com.example.administrator.landapplication.utils.SystemUtil;

import java.io.File;


/**
 * Created by Administrator on 2018/3/21.
 * 应用基础配置
 */

public class AppConfiguration {

    private String TAG = "AppConfiguration";

    /**
     * 首次创建缓存目录
     *
     * @param mDataManager
     * @param currentUser
     * @return
     */
    public void ACInstance(DataManager mDataManager, String currentUser) {
        AppConfigurationInfo currentAppConfigurationInfo = mDataManager.queryAppConfigurationInfo(currentUser);
        String MonitorTheCache = "";
        if (SystemUtil.isSdCardAvailable()) {
            MonitorTheCache = "/sdcard" + "/" + "LAND_MonitorTheCache" + "/" + currentUser;
            if (currentAppConfigurationInfo != null && currentAppConfigurationInfo.getCacheName() != null) {
                MonitorTheCache = currentAppConfigurationInfo.getCacheName();
            }
            File file = new File(MonitorTheCache);
            if (file.exists()) {
                LogUtil.e(TAG, "文件已存在");
                if (mDataManager.queryAppConfigurationInfo(currentUser) == null)
                    mDataManager.insertAppConfigurationInfo(new AppConfigurationInfo(MonitorTheCache, 0, 24, currentUser));
            } else {
                file.mkdirs();
                mDataManager.insertAppConfigurationInfo(new AppConfigurationInfo(MonitorTheCache, 0, 24, currentUser));
                LogUtil.e(TAG, "创建成功");
            }
            DataClass.CacheFile = MonitorTheCache;
        } else {
            LogUtil.e(TAG, "请挂载sd卡");
        }
    }

    //版本文件
    public static String VersionPackage() {
        String VersionPackageName = "";
        if (SystemUtil.isSdCardAvailable()) {
            VersionPackageName = "/sdcard" + "/" + "LAND_MonitorTheCache" + "/" + "version";
            File file = new File(VersionPackageName);
            if (!file.exists()) {
                file.mkdirs();
            } else {
                LogUtil.e("AppConfiguration", "版本文件夹已存在！");
            }
        }
        return VersionPackageName;
    }


}
