package com.example.administrator.landapplication.widget;

import android.os.Environment;

import com.example.administrator.landapplication.global.MyApplication;

import java.io.File;


/**
 * Created by Administrator on 2017/10/30.
 * 基础配置
 */

public class Constants {
    //================= session ====================
    public static String SESSION_ID = "";


    //================= TYPE ====================


    //================= KEY ====================


    //================= PATH ====================

    public static final String PATH_DATA = MyApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "";
    public static final String PATH_IMAGE = PATH_SDCARD + File.separator + "image";
    public static final String PATH_RECORD = PATH_SDCARD + File.separator + "record";

    //================= PREFERENCE ====================


    //====================DB==========================

    public static final String DATABASE_USER_NAME = MyApplication.getInstance().getPackageName() + "job.db";


    //================= INTENT ====================

}
