package com.example.administrator.landapplication.network;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.administrator.landapplication.model.DataManager;
import com.example.administrator.landapplication.model.db.entity.TaskContentInfo;
import com.example.administrator.landapplication.model.db.entity.TaskListInfo;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.NetEventCode;
import com.example.administrator.landapplication.model.rxtools.RxBus;
import com.example.administrator.landapplication.soaptools.Request;
import com.example.administrator.landapplication.soaptools.RequestQueue;
import com.example.administrator.landapplication.soaptools.SoapRequest;
import com.example.administrator.landapplication.soaptools.UrlList;
import com.example.administrator.landapplication.utils.LogUtil;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/28.
 * 当前任务列表
 */

public class TaskListNerWork {

    private String TAG = "TaskListNerWork";
    private Context context;
    public DataManager dataManager;
    private int type = 0;
    private String BaseUrl = "";


    public TaskListNerWork(Context context, DataManager dataManager) {
        this.context = context;
        this.dataManager = dataManager;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    public void getCurrentTaskListData(String BaseUrl, int type) {
        this.type = type;
        this.BaseUrl = BaseUrl;
        initNetWorkData(null, UrlList.queryMyMonitorTaskList, BaseUrl);
    }

    //任务列表
    public void initNetWorkData(Map<String, Object> requestBody, String httpName, String Url) {
        RequestQueue.getQueue().add(new SoapRequest(context, Url, httpName, requestBody, new Request.SoapRequestListener<SoapObject>() {
            @Override
            public void onSuccess(SoapObject result) {
                Log.e(TAG, "onSuccess : " + result.toString());
                try {
                    DeleteTaskContent();
                    DeleteTaskList();
                    SoapObject property = (SoapObject) result.getProperty(0);
                    SoapObject soapObject = (SoapObject) property.getProperty(1);
                    if (soapObject.getPropertyCount() > 0) {
                        SoapObject soapObjectInfo = (SoapObject) soapObject.getProperty(0);
                        int propertyCount = soapObjectInfo.getPropertyCount();
                        LogUtil.e(TAG, "propertyCount : " + propertyCount);
                        for (int i = 0; i < propertyCount; i++) {
                            TaskInfoFarmat((SoapObject) soapObjectInfo.getProperty(i));
                        }
                    }
                    RxBus.getDefault().post(new CommonEvent(NetEventCode.NETWORK_TASK_LIST));
                } catch (Exception e) {
                    LogUtil.e(TAG, "Exception : " + e.toString());
                }
            }

            @Override
            public void onError(String error) {
                RxBus.getDefault().post(new CommonEvent(NetEventCode.NETWORK_ERROR, error));
                LogUtil.e(TAG, "NetWorkData : " + error);
            }
        }));
    }

    //任务列表数据
    private void TaskInfoFarmat(SoapObject property) {
        try {
            property.getProperty("planID");
            property.getProperty("TUID");
            property.getProperty("TUName");
            property.getProperty("distributerID");
            property.getProperty("distributer");
            property.getProperty("distributeTime");
            property.getProperty("taskerID");
            property.getProperty("tasker");
            property.getProperty("senderID");
            property.getProperty("sender");
            property.getProperty("sendTime");
            property.getProperty("createrID");
            property.getProperty("creater");
            property.getProperty("XZQHDM");
            property.getProperty("samplingID");
            String CJMC = property.getProperty("CJMC").toString();
            String CPointID = property.getProperty("CPointID").toString();
            String taskID = (property.getProperty("taskID")).toString();
            String taskType = (property.getProperty("taskStatus")).toString();
            String taskStatus = (property.getProperty("taskSession")).toString();
            LogUtil.e(TAG, "taskType : " + taskType);
            LogUtil.e(TAG, "taskID : " + taskID);
            LogUtil.e(TAG, "taskStatus : " + taskStatus);
            LogUtil.e(TAG, "CJMC : " + CJMC);
            LogUtil.e(TAG, "CPointID : " + CPointID);
            dataManager.insertTaskListInfo(new TaskListInfo(
                    OrValue(taskType),
                    OrValue(taskID),
                    OrValue(taskStatus),
                    OrValue(CJMC),
                    OrValue(CPointID)));
            initTaskContentInfo(taskID);
        } catch (Exception e) {
            LogUtil.e(TAG, "数据解析失败 : " + e.toString());
        }
    }

    //任务详情
    private void initTaskContentInfo(final String taskId) {
        Map<String, Object> param = new HashMap<>();
        param.put("taskID", taskId);
        RequestQueue.getQueue().add(new SoapRequest(context, BaseUrl, UrlList.getMonitorTaskByID, param, new Request.SoapRequestListener<SoapObject>() {
            @Override
            public void onSuccess(SoapObject result) {
                Log.e(TAG, "onSuccess - TaskContentInfo : " + result.toString());
                SoapObject property = (SoapObject) result.getProperty(0);
                LogUtil.e(TAG, "initTaskContentInfo - property.getPropertyCount() : " + property.getPropertyCount());
                if (property != null && property.getPropertyCount() > 0)
                    TaskContentInfoFarmat((SoapObject) property.getProperty("CPoint"), taskId);
            }

            @Override
            public void onError(String error) {
                LogUtil.e(TAG, "TaskContentInfo : " + error);
            }
        }));
    }

    //任务详情
    private void TaskContentInfoFarmat(SoapObject property, String taskId) {
        dataManager.insertTaskContentInfo(
                new TaskContentInfo(taskId,
                        OrValue(property.getProperty("CPointID").toString()),
                        OrValue(property.getProperty("planID").toString()),
                        OrValue(property.getProperty("CPointType").toString()),
                        OrValue(property.getProperty("CPointTypeDesc").toString()),
                        OrValue(property.getProperty("CPointName").toString()),
                        OrValue(property.getProperty("isQCPoint").toString()),
                        OrValue(property.getProperty("YY").toString()),
                        OrValue(property.getProperty("JD").toString()),
                        OrValue(property.getProperty("WD").toString()),
                        OrValue(property.getProperty("HB").toString()),
                        OrValue(property.getProperty("XZQHDM").toString()),
                        OrValue(property.getProperty("SJMC").toString()),
                        OrValue(property.getProperty("DJMC").toString()),
                        OrValue(property.getProperty("XQMC").toString()),
                        OrValue(property.getProperty("XZMC").toString()),
                        OrValue(property.getProperty("CJMC").toString()),
                        OrValue(property.getProperty("XCDYDM").toString()),
                        OrValue(property.getProperty("DZ").toString()),
                        OrValue(property.getProperty("FWMS").toString()),
                        OrValue(property.getProperty("TDLY").toString()),
                        OrValue(property.getProperty("TDLYDesc").toString()),
                        OrValue(property.getProperty("TRLX").toString()),
                        OrValue(property.getProperty("TRLXDesc").toString()),
                        OrValue(property.getProperty("TRYL").toString()),
                        OrValue(property.getProperty("TRYLDesc").toString())));
    }

    //删除任务详情
    private void DeleteTaskContent() {
        List<TaskContentInfo> taskContentInfos = dataManager.loadTaskContentAllInfo();
        LogUtil.e(TAG, "taskContentInfos.size() : " + taskContentInfos.size());
        if (taskContentInfos != null) {
            for (int i = 0; i < taskContentInfos.size(); i++) {
                dataManager.deleteTaskContentInfo(taskContentInfos.get(i).getTaskId());
            }
        }
    }

    //删除任务列表
    private void DeleteTaskList() {
        List<TaskListInfo> taskListInfos = dataManager.queryTaskTypeListInfo(String.valueOf(type));
        LogUtil.e(TAG, "taskListInfos.size() : " + taskListInfos.size());
        if (taskListInfos != null) {
            for (int i = 0; i < taskListInfos.size(); i++) {
                dataManager.deleteTaskListInfo(taskListInfos.get(i).getTaskId());
            }
        }
    }

    private String OrValue(String dbValue) {
        if ("anyType{}".equals(dbValue)) {
            return "";
        } else {
            return dbValue;
        }
    }

}
