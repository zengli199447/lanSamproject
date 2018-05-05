package com.example.administrator.landapplication.global;


import com.example.administrator.landapplication.R;
import com.example.administrator.landapplication.bean.PersonalToolsBean;
import com.example.administrator.landapplication.bean.UserAboutBean;
import com.example.administrator.landapplication.bean.UserToolsTypeBean;
import com.example.administrator.landapplication.model.DataManager;
import com.example.administrator.landapplication.model.db.entity.DictionaryContentChildInfo;
import com.example.administrator.landapplication.model.db.entity.DictionaryContentInfo;
import com.example.administrator.landapplication.model.db.entity.DictionaryUpDateStatus;
import com.example.administrator.landapplication.model.db.entity.LoginUserInfo;
import com.example.administrator.landapplication.model.db.entity.ProcessDataConfigurationInfo;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;


/**
 * Created by Administrator on 2018/3/2 0002.
 * 本地数据提取类
 */

public class DataClass {

    private String TAG = "DataClass";
    public DataManager dataManager;
    private ArrayList<Integer> RecentlyTimeList = new ArrayList<>();
    private ArrayList<Object> objectList;
    private ArrayList<UserToolsTypeBean> userToolsTypeBeanArrayList = new ArrayList<>();
    private ArrayList<PersonalToolsBean> personalToolsBeanArrayList = new ArrayList<>();

    public DataClass() {

    }

    public DataClass(DataManager dataManager) {
        this.dataManager = dataManager;
    }

//    ------------------------------------ 本地数据操作 ----------------------------------------

    public void DbCurrentUser(String userName, String passWord, int currentTime) {  //保存/修改当前帐号信息
        final List<LoginUserInfo> loginUserInfos = dataManager.loadLoginUserInfo();
        if (loginUserInfos != null && loginUserInfos.size() > 0) {
            LoginUserInfo currentUserInfo = dataManager.queryLoginUserInfo(userName);
            if (currentUserInfo != null && userName.equals(currentUserInfo.getUsername())) {
                dataManager.UpDataLoginUserInfo(new LoginUserInfo(currentUserInfo.getId(), userName, passWord, currentUserInfo.getUserPhoto(), currentTime));
            } else {
                dataManager.insertLoginUserInfo(new LoginUserInfo(userName, passWord, "", currentTime));
            }
        } else {
            dataManager.insertLoginUserInfo(new LoginUserInfo(userName, passWord, "", currentTime));
        }
    }

    public LoginUserInfo GetCurrentUser() { // 获取上一次登录用户信息
        LoginUserInfo loginUserInfo = null;
        final List<LoginUserInfo> loginUserInfos = dataManager.loadLoginUserInfo();
        if (loginUserInfos != null && loginUserInfos.size() > 0) {
            for (LoginUserInfo loginUser : loginUserInfos) {
                RecentlyTimeList.add(loginUser.getLoginIndex());
            }
            int max = Collections.max(RecentlyTimeList);
            loginUserInfo = dataManager.queryLoginUserInfoToRecentlyTime(max);
        }
        return loginUserInfo;
    }

    //字典表版本
    public static boolean DbInitDictionaryVersion(DataManager dataManager, String dictionaryName, String dictionaryUpDataTime) {
        List<DictionaryUpDateStatus> dictionaryUpDateStatuses = dataManager.loadDictionaryUpDateStatusInfo();
        if (dictionaryUpDateStatuses != null && dictionaryUpDateStatuses.size() > 0) {
            DictionaryUpDateStatus dictionaryNameInfo = dataManager.queryDictionaryUpDateStatusInfo(dictionaryName);
            if (dictionaryNameInfo != null) {
                if (!dictionaryUpDataTime.equals(dictionaryNameInfo.getDictionaryVersionNamber())) {
                    dataManager.UpDataDictionaryUpDateStatusInfo(new DictionaryUpDateStatus(dictionaryNameInfo.getId(), dictionaryNameInfo.getDictionaryVersion(), dictionaryUpDataTime));
                    return true;
                } else {
                    return false;
                }
            } else {
                dataManager.insertDictionaryUpDateStatusInfo(new DictionaryUpDateStatus(dictionaryName, dictionaryUpDataTime));
                return true;
            }
        } else {
            dataManager.insertDictionaryUpDateStatusInfo(new DictionaryUpDateStatus(dictionaryName, dictionaryUpDataTime));
            return true;
        }
    }

    //字典表类型插入
    public static void DbInitDictionaryContentInfo(DataManager dataManager, String tableName, String code, String value) {
        dataManager.insertDictionaryContentInfo(new DictionaryContentInfo(tableName, value, code));
    }

    public static void DbInitDictionaryContentChildInfo(DataManager dataManager, String tableName, String code, String value) {
        dataManager.insertDictionaryContentChildInfo(new DictionaryContentChildInfo(tableName, value, code));
    }

    //定位仪数据配置表
    public void DbProcessDataConfigurationInfo(String userName, String locator_number, String locator_model) {
        List<ProcessDataConfigurationInfo> processDataConfigurationInfos = dataManager.loadProcessDataConfigurationInfo();
        if (processDataConfigurationInfos != null && processDataConfigurationInfos.size() > 0) {
            ProcessDataConfigurationInfo processDataConfigurationInfo = dataManager.queryProcessDataConfigurationInfo(userName);
            if (processDataConfigurationInfo != null && userName.equals(processDataConfigurationInfo.getUsername())) {
                dataManager.UpDataProcessDataConfigurationInfo(new ProcessDataConfigurationInfo(processDataConfigurationInfo.getId(), userName, locator_number, locator_model));
            } else {
                dataManager.insertProcessDataConfigurationInfo(new ProcessDataConfigurationInfo(userName, locator_number, locator_model));
            }
        } else {
            dataManager.insertProcessDataConfigurationInfo(new ProcessDataConfigurationInfo(userName, locator_number, locator_model));
        }
    }


//    ------------------------------------ 静态过渡数据 --------------------------------------------

    public static String CacheFile = "";
    public static String PhoneNumber = "15628874187";
    public static String DeliveryUnit = "";
    public static String DeliveryMan = "";
    public static String ReceivingBatchUnit = "";
    public static int QrAction;
    public static String HierarchicalType = "";


//    ------------------------------------ 模拟填充数据 ----------------------------------------

    //权限分配功能
    public ArrayList<UserToolsTypeBean> initPression() {
        MyApplication instance = MyApplication.getInstance();
        switch (PermissionsClass.UserType) {
            case 0:
                userToolsTypeBeanArrayList.add(new UserToolsTypeBean(instance.getString(R.string.new_task), 0, instance.getResources().getDrawable(R.drawable.new_task)));
                userToolsTypeBeanArrayList.add(new UserToolsTypeBean(instance.getString(R.string.online_task), 1, instance.getResources().getDrawable(R.drawable.online_task)));
                userToolsTypeBeanArrayList.add(new UserToolsTypeBean(instance.getString(R.string.inspection), 2, instance.getResources().getDrawable(R.drawable.checking_navigation)));
                userToolsTypeBeanArrayList.add(new UserToolsTypeBean(instance.getString(R.string.locator_setting), 5, instance.getResources().getDrawable(R.drawable.home_setting)));
//                userToolsTypeBeanArrayList.add(new UserToolsTypeBean(instance.getString(R.string.area), 5, instance.getResources().getDrawable(R.drawable.exit)));
//                userToolsTypeBeanArrayList.add(new UserToolsTypeBean(instance.getString(R.string.monitoring_center_list_reception), 7, instance.getResources().getDrawable(R.drawable.the_goods)));
//                userToolsTypeBeanArrayList.add(new UserToolsTypeBean(instance.getString(R.string.laboratory_confirm), 8, instance.getResources().getDrawable(R.drawable.the_goods)));
                break;
            case 1:
                userToolsTypeBeanArrayList.add(new UserToolsTypeBean(instance.getString(R.string.the_goods), 4, instance.getResources().getDrawable(R.drawable.the_goods)));
                userToolsTypeBeanArrayList.add(new UserToolsTypeBean(instance.getString(R.string.exit), 3, instance.getResources().getDrawable(R.drawable.exit)));
                break;
            case 2:
                userToolsTypeBeanArrayList.add(new UserToolsTypeBean(instance.getString(R.string.monitoring_center_reception), 6, instance.getResources().getDrawable(R.drawable.the_goods)));
                userToolsTypeBeanArrayList.add(new UserToolsTypeBean(instance.getString(R.string.exit), 3, instance.getResources().getDrawable(R.drawable.exit)));
                break;
        }
        return userToolsTypeBeanArrayList;
    }

    //个人中心
    public ArrayList<PersonalToolsBean> initPersonalTools() {
        MyApplication instance = MyApplication.getInstance();
        personalToolsBeanArrayList.add(new PersonalToolsBean(instance.getString(R.string.personal_phoneNumber), 0, PhoneNumber, instance.getResources().getDrawable(R.drawable.fird_about)));
        personalToolsBeanArrayList.add(new PersonalToolsBean(instance.getString(R.string.personal_modify_password), 1, "", instance.getResources().getDrawable(R.drawable.personal_apply)));
        return personalToolsBeanArrayList;
    }


}
