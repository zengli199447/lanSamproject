package com.example.administrator.landapplication.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.bean.SubmissionContentBean;
import com.example.administrator.landapplication.bean.SubmissionListBean;
import com.example.administrator.landapplication.ui.holder.CommonViewHolder;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/16.
 */

public class ChargeTaskContentAdapter extends BaseAdapter {

    private ArrayList<SubmissionContentBean> submissionContentBeenList;
    private Context context;

    public ChargeTaskContentAdapter(ArrayList<SubmissionContentBean> submissionContentBeenList, Context context) {
        this.submissionContentBeenList = submissionContentBeenList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return submissionContentBeenList.size();
    }

    @Override
    public Object getItem(int i) {
        return submissionContentBeenList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SubmissionContentBean submissionContentBean = submissionContentBeenList.get(i);
        CommonViewHolder holder = CommonViewHolder.createCVH(view, viewGroup, R.layout.item_charge_content);
        TextView index = holder.getTv(R.id.index);
        TextView sample_number = holder.getTv(R.id.sample_number);
        index.setText(submissionContentBean.getIndex());
        sample_number.setText(submissionContentBean.getDeliveryNumber());
        return holder.convertView;
    }
}
