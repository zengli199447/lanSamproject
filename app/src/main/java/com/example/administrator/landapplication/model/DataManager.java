package com.example.administrator.landapplication.model;

import com.example.administrator.landapplication.bean.OuterLayerEntity;
import com.example.administrator.landapplication.model.db.DBHelper;
import com.example.administrator.landapplication.model.db.entity.AppConfigurationInfo;
import com.example.administrator.landapplication.model.db.entity.DictionaryContentChildInfo;
import com.example.administrator.landapplication.model.db.entity.DictionaryContentInfo;
import com.example.administrator.landapplication.model.db.entity.DictionaryUpDateStatus;
import com.example.administrator.landapplication.model.db.entity.LoginUserInfo;
import com.example.administrator.landapplication.model.db.entity.ProcessDataConfigurationInfo;
import com.example.administrator.landapplication.model.db.entity.TaskContentInfo;
import com.example.administrator.landapplication.model.db.entity.TaskListInfo;
import com.example.administrator.landapplication.model.http.HttpHelper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;


/**
 * Created by Administrator on 2017/10/27.
 * 数据管理
 */

public class DataManager implements DBHelper, HttpHelper {
    String TAG = "DataManager";

    private DBHelper mDbHelper;
    private HttpHelper mHttpHelper;

    public DataManager(DBHelper mDbHelper, HttpHelper mHttpHelper) {
        this.mDbHelper = mDbHelper;
        this.mHttpHelper = mHttpHelper;
    }

    //---------------------------数据库查询---------------------------------------

    @Override
    public LoginUserInfo queryLoginUserInfo(String mUserName) {
        return mDbHelper.queryLoginUserInfo(mUserName);
    }

    @Override
    public LoginUserInfo queryLoginUserInfoToRecentlyTime(int recentlyTime) {
        return mDbHelper.queryLoginUserInfoToRecentlyTime(recentlyTime);
    }

    @Override
    public AppConfigurationInfo queryAppConfigurationInfo(String currentUser) {
        return mDbHelper.queryAppConfigurationInfo(currentUser);
    }

    @Override
    public DictionaryUpDateStatus queryDictionaryUpDateStatusInfo(String type) {
        return mDbHelper.queryDictionaryUpDateStatusInfo(type);
    }

    @Override
    public List<DictionaryContentInfo> queryDictionaryContentInfo(String type) {
        return mDbHelper.queryDictionaryContentInfo(type);
    }

    @Override
    public DictionaryContentInfo queryDictionaryContentTypeInfo(String typeName) {
        return mDbHelper.queryDictionaryContentTypeInfo(typeName);
    }

    @Override
    public List<DictionaryContentChildInfo> queryDictionaryContentChildInfoInfo(String tableName) {
        return mDbHelper.queryDictionaryContentChildInfoInfo(tableName);
    }

    @Override
    public List<DictionaryContentChildInfo> queryDictionaryContentChildTypeInfo(String typeName) {
        return mDbHelper.queryDictionaryContentChildTypeInfo(typeName);
    }

    @Override
    public ProcessDataConfigurationInfo queryProcessDataConfigurationInfo(String currentUser) {
        return mDbHelper.queryProcessDataConfigurationInfo(currentUser);
    }

    @Override
    public List<TaskListInfo> queryTaskTypeListInfo(String taskType) {
        return mDbHelper.queryTaskTypeListInfo(taskType);
    }

    @Override
    public TaskListInfo queryTaskIdOnlyInfo(String taskId) {
        return mDbHelper.queryTaskIdOnlyInfo(taskId);
    }

    @Override
    public TaskContentInfo queryTaskContentIdOnlyInfo(String taskId) {
        return mDbHelper.queryTaskContentIdOnlyInfo(taskId);
    }

    @Override
    public List<LoginUserInfo> loadLoginUserInfo() {
        return mDbHelper.loadLoginUserInfo();
    }

    @Override
    public List<AppConfigurationInfo> loadAppConfigurationInfo() {
        return mDbHelper.loadAppConfigurationInfo();
    }

    @Override
    public List<DictionaryUpDateStatus> loadDictionaryUpDateStatusInfo() {
        return mDbHelper.loadDictionaryUpDateStatusInfo();
    }

    @Override
    public List<DictionaryContentInfo> loadDictionaryContentInfo() {
        return mDbHelper.loadDictionaryContentInfo();
    }

    @Override
    public List<DictionaryContentChildInfo> loadDictionaryContentChildInfo() {
        return mDbHelper.loadDictionaryContentChildInfo();
    }

    @Override
    public List<ProcessDataConfigurationInfo> loadProcessDataConfigurationInfo() {
        return mDbHelper.loadProcessDataConfigurationInfo();
    }

    @Override
    public List<TaskListInfo> loadTaskListAllInfo() {
        return mDbHelper.loadTaskListAllInfo();
    }

    @Override
    public List<TaskContentInfo> loadTaskContentAllInfo() {
        return mDbHelper.loadTaskContentAllInfo();
    }

    @Override
    public void insertLoginUserInfo(LoginUserInfo mLoginUserInfo) {
        mDbHelper.insertLoginUserInfo(mLoginUserInfo);
    }

    @Override
    public void insertAppConfigurationInfo(AppConfigurationInfo appConfigurationInfo) {
        mDbHelper.insertAppConfigurationInfo(appConfigurationInfo);
    }

    @Override
    public void insertDictionaryUpDateStatusInfo(DictionaryUpDateStatus dictionaryUpDateStatus) {
        mDbHelper.insertDictionaryUpDateStatusInfo(dictionaryUpDateStatus);
    }

    @Override
    public void insertDictionaryContentInfo(DictionaryContentInfo dictionaryLandUse) {
        mDbHelper.insertDictionaryContentInfo(dictionaryLandUse);
    }

    @Override
    public void insertDictionaryContentChildInfo(DictionaryContentChildInfo dictionaryContentChildInfo) {
        mDbHelper.insertDictionaryContentChildInfo(dictionaryContentChildInfo);
    }

    @Override
    public void insertProcessDataConfigurationInfo(ProcessDataConfigurationInfo processDataConfigurationInfo) {
        mDbHelper.insertProcessDataConfigurationInfo(processDataConfigurationInfo);
    }

    @Override
    public void insertTaskListInfo(TaskListInfo taskListInfo) {
        mDbHelper.insertTaskListInfo(taskListInfo);
    }

    @Override
    public void insertTaskContentInfo(TaskContentInfo taskContentInfo) {
        mDbHelper.insertTaskContentInfo(taskContentInfo);
    }

    @Override
    public void deleteLoginUserInfo(String mUserName) {
        mDbHelper.deleteLoginUserInfo(mUserName);
    }


    @Override
    public void deleteDictionaryContentInfo(String type) {
        mDbHelper.deleteDictionaryContentInfo(type);
    }

    @Override
    public void deleteDictionaryContentChildInfo(String typeName) {
        mDbHelper.deleteDictionaryContentChildInfo(typeName);
    }

    @Override
    public void deleteTaskListInfo(String taskId) {
        mDbHelper.deleteTaskListInfo(taskId);
    }

    @Override
    public void deleteTaskContentInfo(String taskId) {
        mDbHelper.deleteTaskContentInfo(taskId);
    }

    @Override
    public void UpDataLoginUserInfo(LoginUserInfo mLoginUserInfo) {
        mDbHelper.UpDataLoginUserInfo(mLoginUserInfo);
    }

    @Override
    public void UpDataAppConfigurationInfo(AppConfigurationInfo appConfigurationInfo) {
        mDbHelper.UpDataAppConfigurationInfo(appConfigurationInfo);
    }

    @Override
    public void UpDataDictionaryUpDateStatusInfo(DictionaryUpDateStatus dictionaryUpDateStatus) {
        mDbHelper.UpDataDictionaryUpDateStatusInfo(dictionaryUpDateStatus);
    }

    @Override
    public void UpDataDictionaryContentInfo(DictionaryContentInfo dictionaryLandUse) {
        mDbHelper.UpDataDictionaryContentInfo(dictionaryLandUse);
    }

    @Override
    public void UpDataProcessDataConfigurationInfo(ProcessDataConfigurationInfo processDataConfigurationInfo) {
        mDbHelper.UpDataProcessDataConfigurationInfo(processDataConfigurationInfo);
    }

    //---------------------------网络请求---------------------------------------

    @Override
    public Flowable<OuterLayerEntity> UpLoadAbout(Map map) {
        return mHttpHelper.UpLoadAbout(map);
    }

    @Override
    public Flowable<OuterLayerEntity> UpLoadFile(MultipartBody multipartBody) {
        return mHttpHelper.UpLoadFile(multipartBody);
    }
}
