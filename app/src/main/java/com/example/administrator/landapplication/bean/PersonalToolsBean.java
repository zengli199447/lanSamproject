package com.example.administrator.landapplication.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2018/4/12.
 * 个人中心功能实体
 */

public class PersonalToolsBean {
    String toolsName;
    int type;
    String about;
    Drawable drawable;

    public PersonalToolsBean(String toolsName, int type, String about, Drawable drawable) {
        this.toolsName = toolsName;
        this.type = type;
        this.about = about;
        this.drawable = drawable;
    }

    public String getToolsName() {
        return toolsName;
    }

    public void setToolsName(String toolsName) {
        this.toolsName = toolsName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
