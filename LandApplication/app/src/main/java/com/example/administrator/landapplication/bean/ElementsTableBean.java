package com.example.administrator.landapplication.bean;

/**
 * Created by Administrator on 2018/4/16.
 * 样品元素列表
 */

public class ElementsTableBean {
    String name;
    int day;
    int status;

    public ElementsTableBean(String name, int day, int status) {
        this.name = name;
        this.day = day;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
