package com.example.administrator.landapplication.bean;

/**
 * Created by Administrator on 2018/4/17.
 * 通用滚动实体
 */

public class AboutDataBean {
    String content;
    int id;

    public AboutDataBean(String content, int id) {
        this.content = content;
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
