package com.example.administrator.landapplication.model.db;

import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.landapplication.global.MyApplication;
import com.example.administrator.landapplication.model.db.entity.AppConfigurationInfo;
import com.example.administrator.landapplication.model.db.entity.AppConfigurationInfoDao;
import com.example.administrator.landapplication.model.db.entity.DaoMaster;
import com.example.administrator.landapplication.model.db.entity.DaoSession;
import com.example.administrator.landapplication.model.db.entity.DictionaryContentChildInfo;
import com.example.administrator.landapplication.model.db.entity.DictionaryContentChildInfoDao;
import com.example.administrator.landapplication.model.db.entity.DictionaryContentInfo;
import com.example.administrator.landapplication.model.db.entity.DictionaryContentInfoDao;
import com.example.administrator.landapplication.model.db.entity.DictionaryUpDateStatus;
import com.example.administrator.landapplication.model.db.entity.DictionaryUpDateStatusDao;
import com.example.administrator.landapplication.model.db.entity.LoginUserInfo;
import com.example.administrator.landapplication.model.db.entity.LoginUserInfoDao;
import com.example.administrator.landapplication.model.db.entity.ProcessDataConfigurationInfo;
import com.example.administrator.landapplication.model.db.entity.ProcessDataConfigurationInfoDao;
import com.example.administrator.landapplication.model.db.entity.TaskContentInfo;
import com.example.administrator.landapplication.model.db.entity.TaskContentInfoDao;
import com.example.administrator.landapplication.model.db.entity.TaskListInfo;
import com.example.administrator.landapplication.model.db.entity.TaskListInfoDao;
import com.example.administrator.landapplication.widget.Constants;

import java.util.List;

import javax.inject.Inject;


/**
 * Created by Administrator on 2018/2/11.
 */

public class GreenDaoHelper implements DBHelper {

    private final LoginUserInfoDao loginUserInfoDao;
    private final DictionaryContentInfoDao dictionaryContentInfoDao;
    private DaoSession mDaoSession;
    private final AppConfigurationInfoDao appConfigurationBeanDao;
    private final DictionaryUpDateStatusDao dictionaryUpDateStatusDao;
    private final ProcessDataConfigurationInfoDao processDataConfigurationInfoDao;
    private final DictionaryContentChildInfoDao dictionaryContentChildInfoDao;
    private final TaskListInfoDao taskListInfoDao;
    private final TaskContentInfoDao taskContentInfoDao;


    @Inject
    public GreenDaoHelper() {
        //初始化数据库
        setupDatabase();
        loginUserInfoDao = mDaoSession.getLoginUserInfoDao();
        appConfigurationBeanDao = mDaoSession.getAppConfigurationInfoDao();
        dictionaryUpDateStatusDao = mDaoSession.getDictionaryUpDateStatusDao();
        dictionaryContentInfoDao = mDaoSession.getDictionaryContentInfoDao();
        processDataConfigurationInfoDao = mDaoSession.getProcessDataConfigurationInfoDao();
        dictionaryContentChildInfoDao = mDaoSession.getDictionaryContentChildInfoDao();
        taskListInfoDao = mDaoSession.getTaskListInfoDao();
        taskContentInfoDao = mDaoSession.getTaskContentInfoDao();

    }

    private void setupDatabase() {
        //创建数据库,DevOpenHelper：创建SQLite数据库的SQLiteOpenHelper的具体实现
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MyApplication.getInstance().getApplicationContext(), Constants.DATABASE_USER_NAME, null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象,DaoMaster：GreenDao的顶级对象，作为数据库对象、用于创建表和删除表
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者,DaoSession：管理所有的Dao对象，Dao对象中存在着增删改查等API
        mDaoSession = daoMaster.newSession();
    }

    @Override
    public LoginUserInfo queryLoginUserInfo(String mUserName) {
        return loginUserInfoDao.queryBuilder().where(LoginUserInfoDao.Properties.Username.eq(mUserName)).build().unique();
    }

    @Override
    public LoginUserInfo queryLoginUserInfoToRecentlyTime(int recentlyTime) {
        return loginUserInfoDao.queryBuilder().where(LoginUserInfoDao.Properties.LoginIndex.eq(recentlyTime)).build().unique();
    }

    @Override
    public AppConfigurationInfo queryAppConfigurationInfo(String currentUser) {
        return appConfigurationBeanDao.queryBuilder().where(AppConfigurationInfoDao.Properties.CurrentUser.eq(currentUser)).unique();
    }

    @Override
    public DictionaryUpDateStatus queryDictionaryUpDateStatusInfo(String type) {
        return dictionaryUpDateStatusDao.queryBuilder().where(DictionaryUpDateStatusDao.Properties.DictionaryVersion.eq(type)).unique();
    }

    @Override
    public List<DictionaryContentInfo> queryDictionaryContentInfo(String type) {
        return dictionaryContentInfoDao.queryBuilder().where(DictionaryContentInfoDao.Properties.TableName.eq(type)).list();
    }

    @Override
    public DictionaryContentInfo queryDictionaryContentTypeInfo(String typeName) {
        return dictionaryContentInfoDao.queryBuilder().where(DictionaryContentInfoDao.Properties.TypeName.eq(typeName)).unique();
    }

    @Override
    public List<DictionaryContentChildInfo> queryDictionaryContentChildInfoInfo(String tableName) {
        return dictionaryContentChildInfoDao.queryBuilder().where(DictionaryContentChildInfoDao.Properties.TableName.eq(tableName)).list();
    }

    @Override
    public List<DictionaryContentChildInfo> queryDictionaryContentChildTypeInfo(String typeName) {
        return dictionaryContentChildInfoDao.queryBuilder().where(DictionaryContentChildInfoDao.Properties.TypeName.eq(typeName)).list();
    }

    @Override
    public ProcessDataConfigurationInfo queryProcessDataConfigurationInfo(String currentUser) {
        return processDataConfigurationInfoDao.queryBuilder().where(ProcessDataConfigurationInfoDao.Properties.Username.eq(currentUser)).unique();
    }

    @Override
    public List<TaskListInfo> queryTaskTypeListInfo(String taskType) {
        return taskListInfoDao.queryBuilder().where(TaskListInfoDao.Properties.TaskType.eq(taskType)).list();
    }

    @Override
    public TaskListInfo queryTaskIdOnlyInfo(String taskId) {
        return taskListInfoDao.queryBuilder().where(TaskListInfoDao.Properties.TaskId.eq(taskId)).unique();
    }

    @Override
    public TaskContentInfo queryTaskContentIdOnlyInfo(String taskId) {
        return taskContentInfoDao.queryBuilder().where(TaskContentInfoDao.Properties.TaskId.eq(taskId)).unique();
    }

    @Override
    public List<LoginUserInfo> loadLoginUserInfo() {
        return loginUserInfoDao.loadAll();
    }

    @Override
    public List<AppConfigurationInfo> loadAppConfigurationInfo() {
        return appConfigurationBeanDao.loadAll();
    }

    @Override
    public List<DictionaryUpDateStatus> loadDictionaryUpDateStatusInfo() {
        return dictionaryUpDateStatusDao.loadAll();
    }

    @Override
    public List<DictionaryContentInfo> loadDictionaryContentInfo() {
        return dictionaryContentInfoDao.loadAll();
    }

    @Override
    public List<DictionaryContentChildInfo> loadDictionaryContentChildInfo() {
        return dictionaryContentChildInfoDao.loadAll();
    }

    @Override
    public List<ProcessDataConfigurationInfo> loadProcessDataConfigurationInfo() {
        return processDataConfigurationInfoDao.loadAll();
    }

    @Override
    public List<TaskListInfo> loadTaskListAllInfo() {
        return taskListInfoDao.loadAll();
    }

    @Override
    public List<TaskContentInfo> loadTaskContentAllInfo() {
        return taskContentInfoDao.loadAll();
    }

    @Override
    public void insertLoginUserInfo(LoginUserInfo mLoginUserInfo) {
        loginUserInfoDao.insertOrReplace(mLoginUserInfo);
    }

    @Override
    public void insertAppConfigurationInfo(AppConfigurationInfo appConfigurationInfo) {
        appConfigurationBeanDao.insertOrReplace(appConfigurationInfo);
    }

    @Override
    public void insertDictionaryUpDateStatusInfo(DictionaryUpDateStatus dictionaryUpDateStatus) {
        dictionaryUpDateStatusDao.insertOrReplace(dictionaryUpDateStatus);
    }

    @Override
    public void insertDictionaryContentInfo(DictionaryContentInfo dictionaryLandUse) {
        dictionaryContentInfoDao.insertOrReplace(dictionaryLandUse);
    }

    @Override
    public void insertDictionaryContentChildInfo(DictionaryContentChildInfo dictionaryContentChildInfo) {
        dictionaryContentChildInfoDao.insertOrReplace(dictionaryContentChildInfo);
    }

    @Override
    public void insertProcessDataConfigurationInfo(ProcessDataConfigurationInfo processDataConfigurationInfo) {
        processDataConfigurationInfoDao.insertOrReplace(processDataConfigurationInfo);
    }

    @Override
    public void insertTaskListInfo(TaskListInfo taskListInfo) {
        taskListInfoDao.insertOrReplace(taskListInfo);
    }

    @Override
    public void insertTaskContentInfo(TaskContentInfo taskContentInfo) {
        taskContentInfoDao.insertOrReplace(taskContentInfo);
    }

    @Override
    public void deleteLoginUserInfo(String mUserName) {
        LoginUserInfo loginUserInfo = loginUserInfoDao.queryBuilder().where(LoginUserInfoDao.Properties.Username.eq(mUserName)).build().unique();
        if (loginUserInfo != null)
            loginUserInfoDao.delete(loginUserInfo);
    }

    @Override
    public void deleteDictionaryContentInfo(String type) {
        DictionaryContentInfo dictionaryContentInfo = dictionaryContentInfoDao.queryBuilder().where(DictionaryContentInfoDao.Properties.TypeName.eq(type)).build().unique();
        if (dictionaryContentInfo != null)
            dictionaryContentInfoDao.delete(dictionaryContentInfo);
    }

    @Override
    public void deleteDictionaryContentChildInfo(String typeName) {
        DictionaryContentChildInfo dictionaryContentChildInfo = dictionaryContentChildInfoDao.queryBuilder().where(DictionaryContentChildInfoDao.Properties.TypeName.eq(typeName)).build().unique();
        if (dictionaryContentChildInfo != null)
            dictionaryContentChildInfoDao.delete(dictionaryContentChildInfo);
    }

    @Override
    public void deleteTaskListInfo(String taskId) {
        TaskListInfo taskListInfo = taskListInfoDao.queryBuilder().where(TaskListInfoDao.Properties.TaskId.eq(taskId)).build().unique();
        if (taskListInfo != null)
            taskListInfoDao.delete(taskListInfo);
    }

    @Override
    public void deleteTaskContentInfo(String taskId) {
        TaskContentInfo taskContentInfo = taskContentInfoDao.queryBuilder().where(TaskContentInfoDao.Properties.TaskId.eq(taskId)).build().unique();
        if (taskContentInfo != null)
            taskContentInfoDao.delete(taskContentInfo);
    }

    @Override
    public void UpDataLoginUserInfo(LoginUserInfo mLoginUserInfo) {
        loginUserInfoDao.update(mLoginUserInfo);
    }

    @Override
    public void UpDataAppConfigurationInfo(AppConfigurationInfo appConfigurationInfo) {
        appConfigurationBeanDao.update(appConfigurationInfo);
    }

    @Override
    public void UpDataDictionaryUpDateStatusInfo(DictionaryUpDateStatus dictionaryUpDateStatus) {
        dictionaryUpDateStatusDao.update(dictionaryUpDateStatus);
    }

    @Override
    public void UpDataDictionaryContentInfo(DictionaryContentInfo dictionaryLandUse) {
        dictionaryContentInfoDao.update(dictionaryLandUse);
    }

    @Override
    public void UpDataProcessDataConfigurationInfo(ProcessDataConfigurationInfo processDataConfigurationInfo) {
        processDataConfigurationInfoDao.update(processDataConfigurationInfo);
    }


}
