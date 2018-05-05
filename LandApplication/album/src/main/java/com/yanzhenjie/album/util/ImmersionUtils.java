package com.yanzhenjie.album.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/12/4.
 */

public class ImmersionUtils {

    public ImmersionUtils() {

    }

    public void setImmersionUtils(Activity activity, boolean tag) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            ViewGroup decorViewGroup = (ViewGroup) activity.getWindow().getDecorView();
            View statusBarView = new View(activity.getWindow().getContext());
            int statusBarHeight = getStatusBarHeight(activity.getWindow().getContext());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
            params.gravity = Gravity.TOP;
            statusBarView.setLayoutParams(params);
            if (tag) {
                statusBarView.setBackgroundColor(Color.GRAY);
                statusBarView.getBackground().mutate().setAlpha(123);
            } else {
                statusBarView.setBackgroundColor(Color.GRAY);
                statusBarView.getBackground().mutate().setAlpha(123);
            }
            decorViewGroup.addView(statusBarView);
        }
    }

    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }


    public static void setStatusBarHeight(Context context, View view, int layoutType) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = 0;
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight = res.getDimensionPixelSize(resourceId);
            }
            switch (layoutType) {
                case 0:
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
                    view.setLayoutParams(params);
                    break;
                case 1:
                    ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
                    view.setLayoutParams(params1);
                    break;
                case 2:
                    RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
                    view.setLayoutParams(params2);
                    break;

            }
        } else {
            view.setVisibility(View.GONE);
        }
    }


}
