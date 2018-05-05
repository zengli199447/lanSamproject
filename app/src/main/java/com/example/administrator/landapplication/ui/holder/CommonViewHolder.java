package com.example.administrator.landapplication.ui.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/19.
 * 通用的ViewHolder
 */

public class CommonViewHolder {
    public final View convertView;
    private Map<Integer, View> views = new HashMap<>();

    private CommonViewHolder(View convertView) {
        this.convertView = convertView;
    }

    public static CommonViewHolder createCVH(View convertView, ViewGroup parent, int layoutRes) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
            CommonViewHolder holder = new CommonViewHolder(convertView);
            convertView.setTag(holder);
        }
        return (CommonViewHolder) convertView.getTag();
    }


    public void putView(int id, View view) {
        views.put(id, view);
    }

    public <T extends View> T getView(int id, Class<T> clazz) {
        return (T) getView(id);
    }

    public View getView(int id) {
        if (views.get(id) == null) {
            views.put(id, convertView.findViewById(id));
        }
        return views.get(id);
    }

    public TextView getTv(int id) {
        return ((TextView) getView(id));
    }

    public ImageView getIv(int id) {
        return ((ImageView) getView(id));
    }

    public Button getBt(int id) {
        return ((Button) getView(id));
    }

    public CheckBox getCb(int id) {
        return ((CheckBox) getView(id));
    }
}
