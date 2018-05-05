package com.example.administrator.landapplication.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.bean.SubmissionListBean;
import com.example.administrator.landapplication.ui.holder.CommonViewHolder;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/16.
 */

public class ChargeTaskAdapter extends BaseAdapter {

    private ArrayList<SubmissionListBean> submissionLists;
    private Context context;

    public ChargeTaskAdapter(ArrayList<SubmissionListBean> submissionLists, Context context) {
        this.submissionLists = submissionLists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return submissionLists.size();
    }

    @Override
    public Object getItem(int i) {
        return submissionLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SubmissionListBean submissionList = submissionLists.get(i);
        CommonViewHolder holder = CommonViewHolder.createCVH(view, viewGroup, R.layout.item_charge);
        TextView delivery_number = holder.getTv(R.id.delivery_number);
        TextView time = holder.getTv(R.id.time);
        TextView consignor = holder.getTv(R.id.consignor);
        delivery_number.setText(submissionList.getDeliveryNumber());
        time.setText(submissionList.getTime());
        consignor.setText(submissionList.getConsignor());
        return holder.convertView;
    }
}
