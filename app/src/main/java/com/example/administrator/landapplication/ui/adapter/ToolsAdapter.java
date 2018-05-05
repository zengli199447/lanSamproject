package com.example.administrator.landapplication.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.bean.UserToolsTypeBean;
import com.example.administrator.landapplication.ui.holder.CommonViewHolder;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/11.
 */

public class ToolsAdapter extends BaseAdapter {

    private ArrayList<UserToolsTypeBean> userToolsTypeBeanArrayList;
    private Context context;

    public ToolsAdapter(ArrayList<UserToolsTypeBean> userToolsTypeBeanArrayList, Context context) {
        this.userToolsTypeBeanArrayList = userToolsTypeBeanArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userToolsTypeBeanArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return userToolsTypeBeanArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        UserToolsTypeBean userToolsTypeBean = userToolsTypeBeanArrayList.get(i);
        CommonViewHolder holder = CommonViewHolder.createCVH(view, viewGroup, R.layout.item_tools);
        ImageView tools_img = holder.getIv(R.id.tools_img);
        TextView tools_name = holder.getTv(R.id.tools_name);
        tools_img.setBackground(userToolsTypeBean.getDrawable());
        tools_name.setText(userToolsTypeBean.getToolName());

        return holder.convertView;
    }
}
