package com.example.administrator.landapplication.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.bean.AboutDataBean;
import com.example.administrator.landapplication.utils.SystemUtil;
import com.example.administrator.landapplication.utils.ToastUtil;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/10/17.
 */

public class AboutPopupWindow extends PopupWindow implements View.OnClickListener {
    private View mPopView;
    private OnAboutItemClickListener mListener;
    private final ToastUtil toastUtil;
    private Context context;
    HashMap<Integer, AboutDataBean> aboutDataBeenMap;
    private LoopView loopView_m;
    private TextView cancle, commit;
    private ArrayList<String> arrayList = new ArrayList<>();
    private AboutDataBean aboutDataBean;

    public AboutPopupWindow(Context context, HashMap<Integer, AboutDataBean> aboutDataBeenMap) {
        super(context);
        this.context = context;
        this.aboutDataBeenMap = aboutDataBeenMap;
        init(context);
        setPopupWindow();
        toastUtil = new ToastUtil(context);
        cancle.setOnClickListener(this);
        commit.setOnClickListener(this);
        loopView_m.setListener(loopViewMListener);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mPopView = inflater.inflate(R.layout.item_about_layout, null);
        cancle = (TextView) mPopView.findViewById(R.id.cancle);
        commit = (TextView) mPopView.findViewById(R.id.commit);
        loopView_m = (LoopView) mPopView.findViewById(R.id.loopview_m);
        for (int i = 0; i < aboutDataBeenMap.size(); i++) {
            arrayList.add(aboutDataBeenMap.get(i).getContent());
        }
        if (aboutDataBeenMap.size() > 0) {
            aboutDataBean = aboutDataBeenMap.get(1);
            loopView_m.setItems(arrayList);
            loopView_m.setInitPosition(1);
            loopView_m.setNotLoop();
        }
    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setAnimationStyle(R.style.popwindow_anim_style);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mPopView.findViewById(R.id.id_pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        SystemUtil.windowToLight(context);
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    private final OnItemSelectedListener loopViewMListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(int index) {
            aboutDataBean = aboutDataBeenMap.get(index);
        }
    };

    public interface OnAboutItemClickListener {
        void setOnAboutItemClick(View v, AboutDataBean aboutDataBean);
    }

    public void setOnAboutItemClickListener(OnAboutItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            SystemUtil.windowToLight(context);
            mListener.setOnAboutItemClick(v, aboutDataBean);
        }
    }


}
