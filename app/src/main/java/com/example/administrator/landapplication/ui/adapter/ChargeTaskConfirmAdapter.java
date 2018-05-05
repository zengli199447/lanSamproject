package com.example.administrator.landapplication.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.bean.ChargeTaskConfirmBean;
import com.example.administrator.landapplication.ui.holder.CommonViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2018/4/18.
 */

public class ChargeTaskConfirmAdapter extends BaseAdapter {

    private ArrayList<ChargeTaskConfirmBean> chargeTaskConfirmBeanArrayList;
    private Context context;
    private HashMap<Integer, String> checkStatusHashMap = new HashMap();
    private ChargeTaskConfirmBean chargeTaskConfirmBean;

    public ChargeTaskConfirmAdapter(ArrayList<ChargeTaskConfirmBean> chargeTaskConfirmBeanArrayList, Context context) {
        this.chargeTaskConfirmBeanArrayList = chargeTaskConfirmBeanArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return chargeTaskConfirmBeanArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return chargeTaskConfirmBeanArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        chargeTaskConfirmBean = chargeTaskConfirmBeanArrayList.get(i);
        CommonViewHolder holder = CommonViewHolder.createCVH(view, viewGroup, R.layout.item_task_confirm);
        TextView index = holder.getTv(R.id.index);
        TextView sample_number = holder.getTv(R.id.sample_number);
        index.setText(String.valueOf(chargeTaskConfirmBean.getIndex()));
        sample_number.setText(chargeTaskConfirmBean.getNumber());
        CheckBox cb_select = (CheckBox) holder.getView(R.id.cb_select);
        cb_select.setChecked(chargeTaskConfirmBean.isStatus());
        cb_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    checkStatusHashMap.put(i, chargeTaskConfirmBean.getNumber());
                } else {
                    checkStatusHashMap.remove(i);
                }
            }
        });
        cb_select.setChecked((checkStatusHashMap.get(i) == null ? false : true));
        return holder.convertView;
    }

    public HashMap<Integer, String> getCheckStatusHashMap() {
        return checkStatusHashMap;
    }

}
