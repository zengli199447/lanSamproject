package com.example.administrator.landapplication.bean;

/**
 * Created by Administrator on 2018/4/16.
 * 收货单详情
 */

public class SubmissionContentBean {

    String index;
    String deliveryNumber;

    public SubmissionContentBean(String index, String deliveryNumber) {
        this.index = index;
        this.deliveryNumber = deliveryNumber;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }
}
