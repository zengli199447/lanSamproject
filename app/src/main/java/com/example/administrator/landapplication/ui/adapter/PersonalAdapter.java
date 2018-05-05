package com.example.administrator.landapplication.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.bean.PersonalToolsBean;
import com.example.administrator.landapplication.ui.holder.CommonViewHolder;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/11.
 */

public class PersonalAdapter extends BaseAdapter {

    private ArrayList<PersonalToolsBean> personalToolsBeanArrayList;
    private Context context;

    public PersonalAdapter(ArrayList<PersonalToolsBean> personalToolsBeanArrayList, Context context) {
        this.personalToolsBeanArrayList = personalToolsBeanArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return personalToolsBeanArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return personalToolsBeanArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PersonalToolsBean personalToolsBean = personalToolsBeanArrayList.get(i);
        CommonViewHolder holder = CommonViewHolder.createCVH(view, viewGroup, R.layout.item_personal);
        ImageView img_announcement = holder.getIv(R.id.img_announcement);
        TextView tv_announcement = holder.getTv(R.id.tv_announcement);
        TextView tv_about = holder.getTv(R.id.tv_about);
        tv_about.setText(personalToolsBean.getAbout());
        tv_announcement.setText(personalToolsBean.getToolsName());
        img_announcement.setImageDrawable(personalToolsBean.getDrawable());

        return holder.convertView;
    }
}
