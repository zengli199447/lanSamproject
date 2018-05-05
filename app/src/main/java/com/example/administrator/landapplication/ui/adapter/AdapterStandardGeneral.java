package com.example.administrator.landapplication.ui.adapter;

import android.content.Context;


import com.example.administrator.landapplication.base.BasicAdapter;
import com.example.administrator.landapplication.base.BasicHolder;
import com.example.administrator.landapplication.bean.TaskContentBean;
import com.example.administrator.landapplication.ui.holder.TaskContentHolder;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Objects;


/**
 * Created by Administrator on 2018/4/3.
 * 通用多类Adapter
 */

public class AdapterStandardGeneral extends BasicAdapter<Object> {

    private Context context;
    private ArrayList<Object> mList;
    public static final int TYPE_EXECPTION = 0;
    public static final int TYPE_TASK = 1;  //任务
    public final int TypeCount = 2;

    public AdapterStandardGeneral(ArrayList<Object> list, Context context) {
        super(list);
        this.mList = list;
        this.context = context;
    }

    @Override
    protected BasicHolder<Object> getHolder(int position) {
        switch (getItemViewType(position)) {
            case TYPE_TASK:
                return new TaskContentHolder(context);
            default:
                throw new InvalidParameterException();
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof TaskContentBean) {
            return TYPE_TASK;
        }
        return TYPE_EXECPTION;
    }

    @Override
    public int getViewTypeCount() {
        return TypeCount;
    }
}
