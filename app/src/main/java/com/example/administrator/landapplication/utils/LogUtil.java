package com.example.administrator.landapplication.utils;

import android.util.Log;

/**
 * Created by Administrator on 2017/10/31.
 */

public class LogUtil {
    private static boolean isDebug = true;

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void d(Object object, String msg) {
        if (isDebug) {
            Log.d(object.getClass().getSimpleName(), msg);
        }
    }



    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }


    public static void e(Object object, String msg) {
        if (isDebug) {
            Log.e(object.getClass().getSimpleName(), msg);
        }
    }


    public static void i(String tag, String msg){
        if (isDebug){
            Log.i(tag, msg);
        }
    }

    public static void i(Object object, String msg){
        if (isDebug){
            Log.i(object.getClass().getSimpleName(), msg);
        }
    }


    public static void v(String tag, String msg){
        if (isDebug){
            Log.v(tag, msg);
        }
    }
    public static void v(Object object, String msg){
        if (isDebug){
            Log.v(object.getClass().getSimpleName(), msg);
        }
    }
    public static void w(String tag, String msg){
        if (isDebug){
            Log.w(tag, msg);
        }
    }
    public static void w(Object object, String msg){
        if (isDebug){
            Log.w(object.getClass().getSimpleName(), msg);
        }
    }
}
