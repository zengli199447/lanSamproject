package com.example.administrator.landapplication.bean;

/**
 * Created by Administrator on 2018/4/18.
 * 实验室确认实体
 */

public class ChargeTaskConfirmBean {

    boolean status;
    int index;
    String Number;

    public ChargeTaskConfirmBean(boolean status, int index, String number) {
        this.status = status;
        this.index = index;
        Number = number;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }
}
