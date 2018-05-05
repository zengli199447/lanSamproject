package com.example.administrator.landapplication.model.db;

import com.example.administrator.landapplication.model.db.entity.AppConfigurationInfo;
import com.example.administrator.landapplication.model.db.entity.DictionaryContentChildInfo;
import com.example.administrator.landapplication.model.db.entity.DictionaryContentInfo;
import com.example.administrator.landapplication.model.db.entity.DictionaryUpDateStatus;
import com.example.administrator.landapplication.model.db.entity.LoginUserInfo;
import com.example.administrator.landapplication.model.db.entity.ProcessDataConfigurationInfo;
import com.example.administrator.landapplication.model.db.entity.TaskContentInfo;
import com.example.administrator.landapplication.model.db.entity.TaskListInfo;

import java.util.List;


/**
 * Created by Administrator on 2018/1/5.
 */

public interface DBHelper {

    //---------------------------条件查询---------------------------------------

    /**
     * 查询 LoginUserInfo数据
     *
     * @return
     */
    LoginUserInfo queryLoginUserInfo(String mUserName);

    /**
     * 查询 LoginUserInfo数据
     *
     * @return
     */
    LoginUserInfo queryLoginUserInfoToRecentlyTime(int recentlyTime);


    /**
     * 查询 AppConfigurationInfo数据
     *
     * @return
     */

    AppConfigurationInfo queryAppConfigurationInfo(String currentUser);


    /**
     * 查询 Dictionary数据
     *
     * @return
     */

    DictionaryUpDateStatus queryDictionaryUpDateStatusInfo(String type);

    List<DictionaryContentInfo> queryDictionaryContentInfo(String type);

    DictionaryContentInfo queryDictionaryContentTypeInfo(String typeName);

    List<DictionaryContentChildInfo> queryDictionaryContentChildInfoInfo(String tableName);

    List<DictionaryContentChildInfo> queryDictionaryContentChildTypeInfo(String typeName);

    /**
     * 查询 ProcessDataConfigurationInfo数据
     *
     * @return
     */

    ProcessDataConfigurationInfo queryProcessDataConfigurationInfo(String currentUser);

    /**
     * 查询 TaskListInfo数据
     *
     * @return
     */

    List<TaskListInfo> queryTaskTypeListInfo(String taskType);

    TaskListInfo queryTaskIdOnlyInfo(String taskId);

    /**
     * 查询 TaskContentInfo数据
     *
     * @return
     */

    TaskContentInfo queryTaskContentIdOnlyInfo(String taskId);


    //---------------------------查询所有（无筛选条件）---------------------------

    /**
     * 查询所有 LoginUserInfo数据
     *
     * @return
     */
    List<LoginUserInfo> loadLoginUserInfo();

    /**
     * 查询所有 AppConfigurationInfo数据
     *
     * @return
     */
    List<AppConfigurationInfo> loadAppConfigurationInfo();

    /**
     * 查询所有的 Dictionary数据
     *
     * @return
     */

    List<DictionaryUpDateStatus> loadDictionaryUpDateStatusInfo();

    List<DictionaryContentInfo> loadDictionaryContentInfo();

    List<DictionaryContentChildInfo> loadDictionaryContentChildInfo();


    /**
     * 查询所有 ProcessDataConfigurationInfo数据
     *
     * @return
     */
    List<ProcessDataConfigurationInfo> loadProcessDataConfigurationInfo();

    /**
     * 查询所有 TaskListInfo数据
     *
     * @return
     */
    List<TaskListInfo> loadTaskListAllInfo();

    /**
     * 查询所有 TaskContentInfo数据
     *
     * @return
     */
    List<TaskContentInfo> loadTaskContentAllInfo();

    //---------------------------插入数据（更新数据）-----------------------------

    /**
     * 插入一条 LoginUserInfo数据
     *
     * @return
     */
    void insertLoginUserInfo(LoginUserInfo mLoginUserInfo);


    /**
     * 插入一条 AppConfigurationInfo数据
     *
     * @return
     */
    void insertAppConfigurationInfo(AppConfigurationInfo appConfigurationInfo);

    /**
     * 插入一条 Dictionary数据
     *
     * @return
     */

    void insertDictionaryUpDateStatusInfo(DictionaryUpDateStatus dictionaryUpDateStatus);

    void insertDictionaryContentInfo(DictionaryContentInfo dictionaryLandUse);

    void insertDictionaryContentChildInfo(DictionaryContentChildInfo dictionaryContentChildInfo);

    /**
     * 插入一条 ProcessDataConfigurationInfo数据
     *
     * @return
     */
    void insertProcessDataConfigurationInfo(ProcessDataConfigurationInfo processDataConfigurationInfo);

    /**
     * 插入一条 TaskListInfo数据
     *
     * @return
     */
    void insertTaskListInfo(TaskListInfo taskListInfo);

    /**
     * 插入一条 TaskListInfo数据
     *
     * @return
     */
    void insertTaskContentInfo(TaskContentInfo taskContentInfo);

    //---------------------------删除数据(条件删除)-------------------------------

    /**
     * 删除一个 LoginUserInfo数据
     *
     * @return
     */
    void deleteLoginUserInfo(String mUserName);

    /**
     * 删除一个 Dictionary数据
     *
     * @return
     */

    void deleteDictionaryContentInfo(String type);

    void deleteDictionaryContentChildInfo(String typeName);

    /**
     * 删除一个 TaskListInfo数据
     *
     * @return
     */
    void deleteTaskListInfo(String taskId);

    /**
     * 删除一个 TaskContentInfo数据
     *
     * @return
     */
    void deleteTaskContentInfo(String taskId);

    //---------------------------修改数据()-------------------------------

    /**
     * 修改一条 IpAndPortInfo数据
     *
     * @return
     */
    void UpDataLoginUserInfo(LoginUserInfo mLoginUserInfo);

    /**
     * 更新 UpDataAppConfigurationInfo 数据
     *
     * @return
     */
    void UpDataAppConfigurationInfo(AppConfigurationInfo appConfigurationInfo);

    /**
     * 更新 Dictionary数据
     *
     * @return
     */

    void UpDataDictionaryUpDateStatusInfo(DictionaryUpDateStatus dictionaryUpDateStatus);

    void UpDataDictionaryContentInfo(DictionaryContentInfo dictionaryLandUse);


    /**
     * 修改一条 ProcessDataConfigurationInfo数据
     *
     * @return
     */
    void UpDataProcessDataConfigurationInfo(ProcessDataConfigurationInfo processDataConfigurationInfo);

}
