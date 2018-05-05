package com.example.administrator.landapplication.soaptools;


/**
 * Created by Administrator on 2018/3/16.
 */

public class UrlList {

    public final static String PREFIX_URL = "http://121.41.57.116:3601/"; //"http://121.41.57.116:3601/";
    public final static String Web_URL = "http://121.41.57.116:3602/";//"http://121.41.57.116:3602/";
    public static final String NAMESPACE = "http://www.OA.com/webservices/";

    //登陆
    public final static String LOGIN = PREFIX_URL + "UM/loginManager.asmx";


    //收货
    public final static String MONITORING_CENTER_LIST_RECEPTION = NAMESPACE + "TM/SampleReceiveInfoManager.asmx";

    //字典相关 ：
    //1.土壤类型代码字典相关服务 -------------------------------------------------------------------
    public final static String TRLX_CODE_DICTIONARY = PREFIX_URL + "Option/TRLXCodeDictionary.asmx";
    //	获取土壤类型（主类）结果集
    public final static String queryTRLX = "queryTRLX";
    //获取指定类型的土壤亚类结果集
    public final static String queryTRYL = "queryTRYL";
    //	获取土壤分类代码字典最后更新时间
    public final static String TRLXLastUpdateTime = "getLastUpdateTime";

    //土地利用代码字典相关服务 ---------------------------------------------------------------------
    public final static String TDLYCodeDictionary = PREFIX_URL + "Option/TDLYCodeDictionary.asmx";
    //	获取土壤类型（主类）结果集
    public final static String queryTDLY = "queryTDLY";
    //	获取土壤分类代码字典最后更新时间
    public final static String TDLYLastUpdateTime = "getLastUpdateTime";

    //代码字典相关服务-------------------------------------------------------------------------------
    public final static String CdManager = PREFIX_URL + "System/cdManager.asmx";
    //查询指定大类的代码字典条目
    public final static String querycodeDictionaryClass = "codeDictionaryClass";
    //获取指定大类代码字典最后更新时间
    public final static String AllLastUpdateTime = "getLastUpdateTime";

    //系统信息	提供系统版本信息服务---------------------------------
    public final static String SYSTEMINFO = PREFIX_URL + "System/systemInfo.asmx";
    //获取最新版本的安卓App信息（返回对象）
    public final static String LastVersionApp = "getLastVersionApp";

    // 用户管理
    public final static String USERMANAGER = PREFIX_URL + "UM/userManager.asmx";
    //获取用户自身的完整信息
    public final static String UserSelfInfo = "getUserSelfInfo";

    // 在线任务分配管理
    public final static String ONLINETASKMANAGER = PREFIX_URL + "TM/OnlineTaskManager.asmx";
    //获取指定人员的任务分配信息，返回结果集
    public final static String queryTaskDistributionRecordByUserID = "queryTaskDistributionRecordByUserID"; // --
    public final static String stopMonitorTask = "stopMonitorTask";  //停止
    public final static String addSamplingInfoByJson = "addSamplingInfoByJson"; //提交采样

    // 新建任务分配管理
    public final static String NEWTASKMANAGER = PREFIX_URL + "TM/NewTaskManager.asmx";
    public final static String queryMonitorTaskListByTaskerID = "queryMonitorTaskListByTaskerID";
    public final static String getMonitorTaskByID = "getMonitorTaskByID";
    public final static String startMonitorTask = "startMonitorTask";
    public final static String queryMyMonitorTaskList = "queryMyMonitorTaskList";  //在线/新建 任务获取


}
