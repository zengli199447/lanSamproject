package com.example.administrator.landapplication.bean;

/**
 * Created by Administrator on 2018/4/13.
 * 任务列表
 */

public class TaskContentBean {

    String countyLevel;
    String samplePointNnumber;
    int taskStatus;
    String taskId;

    public TaskContentBean(String countyLevel, String samplePointNnumber, int taskStatus, String taskId) {
        this.countyLevel = countyLevel;
        this.samplePointNnumber = samplePointNnumber;
        this.taskStatus = taskStatus;
        this.taskId = taskId;
    }

    public String getCountyLevel() {
        return countyLevel;
    }

    public void setCountyLevel(String countyLevel) {
        this.countyLevel = countyLevel;
    }

    public String getSamplePointNnumber() {
        return samplePointNnumber;
    }

    public void setSamplePointNnumber(String samplePointNnumber) {
        this.samplePointNnumber = samplePointNnumber;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
