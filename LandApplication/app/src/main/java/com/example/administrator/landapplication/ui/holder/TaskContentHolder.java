package com.example.administrator.landapplication.ui.holder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.base.BasicHolder;
import com.example.administrator.landapplication.bean.TaskContentBean;
import com.example.administrator.landapplication.global.MyApplication;
import com.example.administrator.landapplication.utils.LogUtil;

/**
 * Created by Administrator on 2018/4/13.
 */

public class TaskContentHolder extends BasicHolder<Object> {

    private Context context;
    private TextView county_level, sample_point_number;
    private View task_status;

    public TaskContentHolder(Context context) {
        this.context = context;
    }

    @Override
    public View initHolderView() {
        View view = View.inflate(MyApplication.getInstance(), R.layout.item_task_content, null);
        county_level = (TextView) view.findViewById(R.id.county_level);
        sample_point_number = (TextView) view.findViewById(R.id.sample_point_number);
        task_status = view.findViewById(R.id.task_status);

        return view;
    }

    @Override
    public void bindData(Object data) {
        TaskContentBean taskContentData = (TaskContentBean) data;
        county_level.setText(taskContentData.getCountyLevel());
        sample_point_number.setText(taskContentData.getSamplePointNnumber());
        Drawable drawable = null;
        switch (taskContentData.getTaskStatus()) {
            case 0: //新建
            case 1:
            case 2:
                drawable = context.getResources().getDrawable(R.drawable.task_add);
                break;
            case 3: //进行中
            case 4: //采样完成
                drawable = context.getResources().getDrawable(R.drawable.task_ongoing);
                break;
            case 5: //监测中心收货
                drawable = context.getResources().getDrawable(R.drawable.task_monitoring_center);
                break;
            case 6: //实验室收货
                drawable = context.getResources().getDrawable(R.drawable.task_laboratory);
                break;
            case 9: //已完成
                drawable = context.getResources().getDrawable(R.drawable.task_complete);
                break;
        }
        task_status.setBackground(drawable);
    }

}
