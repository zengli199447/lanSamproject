package com.example.administrator.landapplication.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.bean.AttributeTypeBean;
import com.example.administrator.landapplication.ui.holder.CommonViewHolder;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/20.
 */

public class AttributeTypeAdapter extends BaseAdapter {

    private ArrayList<AttributeTypeBean> attributeTypeBeanArrayList;
    private Context context;

    public AttributeTypeAdapter(ArrayList<AttributeTypeBean> attributeTypeBeanArrayList, Context context) {
        this.attributeTypeBeanArrayList = attributeTypeBeanArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return attributeTypeBeanArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return attributeTypeBeanArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AttributeTypeBean attributeTypeBean = attributeTypeBeanArrayList.get(i);
        CommonViewHolder holder = CommonViewHolder.createCVH(view, viewGroup, R.layout.item_attribute_type);
        TextView attribute_type = holder.getTv(R.id.attribute_type);
        View layout = holder.getView(R.id.layout);
        attribute_type.setText(attributeTypeBean.getName());
        if (attributeTypeBean.getName().equals("其他")){
            layout.setVisibility(View.GONE);
        }else {
            layout.setVisibility(View.VISIBLE);
        }
        return holder.convertView;
    }

}
