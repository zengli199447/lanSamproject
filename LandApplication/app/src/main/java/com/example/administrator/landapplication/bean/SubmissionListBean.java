package com.example.administrator.landapplication.bean;

/**
 * Created by Administrator on 2018/4/16.
 * 收货单列表
 */

public class SubmissionListBean {
    String deliveryNumber;
    String time;
    String consignor;

    public SubmissionListBean(String deliveryNumber, String time, String consignor) {
        this.deliveryNumber = deliveryNumber;
        this.time = time;
        this.consignor = consignor;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getConsignor() {
        return consignor;
    }

    public void setConsignor(String consignor) {
        this.consignor = consignor;
    }

}
