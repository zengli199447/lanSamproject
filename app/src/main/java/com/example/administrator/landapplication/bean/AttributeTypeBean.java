package com.example.administrator.landapplication.bean;

/**
 * Created by Administrator on 2018/4/20.
 * 通用类型选择实体
 */

public class AttributeTypeBean {

    private String name;
    private String id;

    public AttributeTypeBean(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
