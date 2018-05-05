package com.example.administrator.landapplication.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.bean.ElementsTableBean;
import com.example.administrator.landapplication.ui.holder.CommonViewHolder;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/16.
 */

public class ElementsTableAdapter extends BaseAdapter {

    private ArrayList<ElementsTableBean> elementsTableBeenList;
    private Context context;

    public ElementsTableAdapter(ArrayList<ElementsTableBean> elementsTableBeenList, Context context) {
        this.elementsTableBeenList = elementsTableBeenList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return elementsTableBeenList.size();
    }

    @Override
    public Object getItem(int i) {
        return elementsTableBeenList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ElementsTableBean elementsTableBean = elementsTableBeenList.get(i);
        CommonViewHolder holder = CommonViewHolder.createCVH(view, viewGroup, R.layout.item_elements_table);
        TextView name_view = holder.getTv(R.id.name_view);
        TextView day_view = holder.getTv(R.id.day_view);
        TextView status_view = holder.getTv(R.id.status_view);
        name_view.setText(elementsTableBean.getName());
        day_view.setText(String.valueOf(elementsTableBean.getDay()));
        if (elementsTableBean.getStatus() == 0) {
            status_view.setTextColor(context.getResources().getColor(R.color.green_style));
            status_view.setText(new StringBuffer().append("√").append(context.getString(R.string.qualified)));
        } else {
            status_view.setText(new StringBuffer().append("╳").append(context.getString(R.string.no_qualified)));
            status_view.setTextColor(context.getResources().getColor(R.color.red_style));
        }
        return holder.convertView;
    }
}
