package com.example.administrator.landapplication.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.bean.AboutDataBean;
import com.example.administrator.landapplication.ui.holder.CommonViewHolder;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/23.
 * 已连接蓝牙设备
 */

public class MatchedPrinterAdapter extends BaseAdapter {

    private ArrayList<AboutDataBean> aboutDataBeanArrayList;
    private Context context;

    public MatchedPrinterAdapter(ArrayList<AboutDataBean> aboutDataBeanArrayList, Context context) {
        this.aboutDataBeanArrayList = aboutDataBeanArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return aboutDataBeanArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return aboutDataBeanArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CommonViewHolder holder = CommonViewHolder.createCVH(view, viewGroup, R.layout.item_matched_printer);
        TextView tv_signal = holder.getTv(R.id.tv_signal);
        tv_signal.setText(aboutDataBeanArrayList.get(i).getContent());
        return holder.convertView;
    }

}
