package com.example.administrator.landapplication.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2018/4/11.
 * 主页功能类型实体
 */

public class UserToolsTypeBean {

    String toolName;
    int toolId;
    Drawable drawable;

    public UserToolsTypeBean(String toolName, int toolId, Drawable drawable) {
        this.toolName = toolName;
        this.toolId = toolId;
        this.drawable = drawable;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public int getToolId() {
        return toolId;
    }

    public void setToolId(int toolId) {
        this.toolId = toolId;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
