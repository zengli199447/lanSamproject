package com.example.administrator.landapplication.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/3.
 * 通用Adapter （建议多类型使用，便于管理）
 */

public abstract class BasicAdapter<T> extends BaseAdapter {

    protected String TAG = getClass().getSimpleName();

    protected ArrayList<T> list;

    public BasicAdapter(ArrayList<T> list) {
        super();
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BasicHolder<T> holder = null;
        if (convertView == null) {
            holder = getHolder(position);
        } else {
            holder = (BasicHolder) convertView.getTag();
        }
        holder.bindData(list.get(position));
        return holder.getHolderView();
    }

    protected abstract BasicHolder<T> getHolder(int position);

}
