package com.yanzhenjie.album.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by Administrator on 2017/1/4.
 * nicezengli@163.com
 * SP工具类
 */
public class AlbumSPUtils {

    public static void Puts(Context context, String key, Object value) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        Editor edit = sp.edit();
        if (value instanceof String) {
            edit.putString(key, (String) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (int) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (boolean) value);
        }
        edit.commit();
    }

    public static String GetString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public static int GetInt(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getInt(key, 0);
    }

    public static boolean GetBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public static boolean GetMengBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getBoolean(key, true);
    }

    public static void ClearData(Context context) {
        context.getSharedPreferences("config", Context.MODE_PRIVATE).edit().clear().commit();
    }

}
