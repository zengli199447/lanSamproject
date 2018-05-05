package com.example.administrator.landapplication.model.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/4/28.
 * 任务列表
 */

@Entity
public class TaskListInfo {

    //主键自增长
    @Id(autoincrement = true)
    private Long id;
    //任务类型（新建/在线）
    private String taskType;
    //任务Id;
    @Unique
    private String taskId;
    //任务状态(多种);
    private String taskStatus;
    //行政区县级;
    private String countyLevel;
    //采样点编号;
    private String samplePointNumber;

    public TaskListInfo(String taskType, String taskId, String taskStatus, String countyLevel, String samplePointNumber) {
        this.taskType = taskType;
        this.taskId = taskId;
        this.taskStatus = taskStatus;
        this.countyLevel = countyLevel;
        this.samplePointNumber = samplePointNumber;
    }

    @Generated(hash = 1410937763)
    public TaskListInfo(Long id, String taskType, String taskId, String taskStatus, String countyLevel,
            String samplePointNumber) {
        this.id = id;
        this.taskType = taskType;
        this.taskId = taskId;
        this.taskStatus = taskStatus;
        this.countyLevel = countyLevel;
        this.samplePointNumber = samplePointNumber;
    }

    @Generated(hash = 116292825)
    public TaskListInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskType() {
        return this.taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskStatus() {
        return this.taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getCountyLevel() {
        return this.countyLevel;
    }

    public void setCountyLevel(String countyLevel) {
        this.countyLevel = countyLevel;
    }

    public String getSamplePointNumber() {
        return this.samplePointNumber;
    }

    public void setSamplePointNumber(String samplePointNumber) {
        this.samplePointNumber = samplePointNumber;
    }



}
