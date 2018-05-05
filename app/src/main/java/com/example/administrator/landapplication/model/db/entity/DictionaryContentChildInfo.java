package com.example.administrator.landapplication.model.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/4/27.
 */

@Entity
public class DictionaryContentChildInfo {
    //主键自增长
    @Id(autoincrement = true)
    private Long id;
    //表名
    private String tableName;
    //类型名称;
    @Unique
    private String TypeName;
    //类型编号;
    private String TypeNumber;

    public DictionaryContentChildInfo(String tableName, String typeName, String typeNumber) {
        this.tableName = tableName;
        TypeName = typeName;
        TypeNumber = typeNumber;
    }

    @Generated(hash = 1569616771)
    public DictionaryContentChildInfo(Long id, String tableName, String TypeName,
            String TypeNumber) {
        this.id = id;
        this.tableName = tableName;
        this.TypeName = TypeName;
        this.TypeNumber = TypeNumber;
    }

    @Generated(hash = 1344497044)
    public DictionaryContentChildInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTypeName() {
        return this.TypeName;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }

    public String getTypeNumber() {
        return this.TypeNumber;
    }

    public void setTypeNumber(String TypeNumber) {
        this.TypeNumber = TypeNumber;
    }



}
