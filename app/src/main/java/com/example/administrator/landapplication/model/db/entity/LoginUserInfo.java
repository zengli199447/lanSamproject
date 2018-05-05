package com.example.administrator.landapplication.model.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;


/**
 * Created by Administrator on 2018/3/3 0003.
 */

@Entity
public class LoginUserInfo {
    //主键自增长
    @Id(autoincrement = true)
    private Long id;
    //用户名
    @Unique
    private String username;
    //密码
    private String password;
    //头像
    private String userPhoto;
    //最后登陆下标
    private int loginIndex;

    public LoginUserInfo(String username, String password, String userPhoto,int loginIndex) {
        this.username = username;
        this.password = password;
        this.userPhoto = userPhoto;
        this.loginIndex = loginIndex;
    }

    @Generated(hash = 1379475502)
    public LoginUserInfo(Long id, String username, String password, String userPhoto,
            int loginIndex) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userPhoto = userPhoto;
        this.loginIndex = loginIndex;
    }

    @Generated(hash = 436417725)
    public LoginUserInfo() {
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserPhoto() {
        return this.userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public int getLoginIndex() {
        return this.loginIndex;
    }

    public void setLoginIndex(int loginIndex) {
        this.loginIndex = loginIndex;
    }




}
