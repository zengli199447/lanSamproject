package com.example.administrator.landapplication.model.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by Administrator on 2018/4/20.
 * 字典表版本
 */

@Entity
public class DictionaryUpDateStatus {
    //主键自增长
    @Id(autoincrement = true)
    private Long id;
    //字典表版本
    @Unique
    private String dictionaryVersion;
    //字典表版本编号
    private String dictionaryVersionNamber;

    public DictionaryUpDateStatus(String dictionaryVersion, String dictionaryVersionNamber) {
        this.dictionaryVersion = dictionaryVersion;
        this.dictionaryVersionNamber = dictionaryVersionNamber;
    }

    @Generated(hash = 1103013902)
    public DictionaryUpDateStatus(Long id, String dictionaryVersion, String dictionaryVersionNamber) {
        this.id = id;
        this.dictionaryVersion = dictionaryVersion;
        this.dictionaryVersionNamber = dictionaryVersionNamber;
    }

    @Generated(hash = 628658520)
    public DictionaryUpDateStatus() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDictionaryVersion() {
        return this.dictionaryVersion;
    }

    public void setDictionaryVersion(String dictionaryVersion) {
        this.dictionaryVersion = dictionaryVersion;
    }

    public String getDictionaryVersionNamber() {
        return this.dictionaryVersionNamber;
    }

    public void setDictionaryVersionNamber(String dictionaryVersionNamber) {
        this.dictionaryVersionNamber = dictionaryVersionNamber;
    }

}
