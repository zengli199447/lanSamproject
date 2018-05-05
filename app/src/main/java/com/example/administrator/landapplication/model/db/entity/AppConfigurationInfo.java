package com.example.administrator.landapplication.model.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by Administrator on 2018/3/21.
 */
@Entity
public class AppConfigurationInfo {

    //主键自增长
    @Id(autoincrement = true)
    private Long id;
    //缓存文件夹目录地址
    @Unique
    private String CacheName;
    //推送开始时间
    private int StartPush;
    //推送结束时间
    private int EndPush;
    //对应表的账号名
    private String CurrentUser;

    public AppConfigurationInfo(String cacheName, int startPush, int endPush, String currentUser) {
        CacheName = cacheName;
        StartPush = startPush;
        EndPush = endPush;
        CurrentUser = currentUser;
    }

    @Generated(hash = 1741962765)
    public AppConfigurationInfo(Long id, String CacheName, int StartPush, int EndPush,
            String CurrentUser) {
        this.id = id;
        this.CacheName = CacheName;
        this.StartPush = StartPush;
        this.EndPush = EndPush;
        this.CurrentUser = CurrentUser;
    }

    @Generated(hash = 1024411081)
    public AppConfigurationInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCacheName() {
        return this.CacheName;
    }

    public void setCacheName(String CacheName) {
        this.CacheName = CacheName;
    }

    public int getStartPush() {
        return this.StartPush;
    }

    public void setStartPush(int StartPush) {
        this.StartPush = StartPush;
    }

    public int getEndPush() {
        return this.EndPush;
    }

    public void setEndPush(int EndPush) {
        this.EndPush = EndPush;
    }

    public String getCurrentUser() {
        return this.CurrentUser;
    }

    public void setCurrentUser(String CurrentUser) {
        this.CurrentUser = CurrentUser;
    }





}
