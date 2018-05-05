package com.example.administrator.landapplication.model.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/4/23.
 */

@Entity
public class ProcessDataConfigurationInfo {

    //主键自增长
    @Id(autoincrement = true)
    private Long id;
    //用户名
    @Unique
    private String username;
    //定位仪编号
    private String locator_number;
    //定位仪型号
    private String locator_model;

    public ProcessDataConfigurationInfo(String username, String locator_number, String locator_model) {
        this.username = username;
        this.locator_number = locator_number;
        this.locator_model = locator_model;
    }

    @Generated(hash = 718586850)
    public ProcessDataConfigurationInfo(Long id, String username, String locator_number,
            String locator_model) {
        this.id = id;
        this.username = username;
        this.locator_number = locator_number;
        this.locator_model = locator_model;
    }

    @Generated(hash = 490934449)
    public ProcessDataConfigurationInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocator_number() {
        return this.locator_number;
    }

    public void setLocator_number(String locator_number) {
        this.locator_number = locator_number;
    }

    public String getLocator_model() {
        return this.locator_model;
    }

    public void setLocator_model(String locator_model) {
        this.locator_model = locator_model;
    }




}
