package com.example.administrator.landapplication.global;

import android.content.Context;
import android.util.Log;

import com.example.administrator.landapplication.bean.UserAboutBean;
import com.example.administrator.landapplication.bean.UserBean;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.NetEventCode;
import com.example.administrator.landapplication.model.rxtools.RxBus;
import com.example.administrator.landapplication.soaptools.Request;
import com.example.administrator.landapplication.soaptools.RequestQueue;
import com.example.administrator.landapplication.soaptools.SoapRequest;
import com.example.administrator.landapplication.utils.LogUtil;

import org.ksoap2.serialization.SoapObject;

import java.util.Map;


/**
 * Created by Administrator on 2018/3/16.
 * 登陆信息静态类 / 网络请求类
 */

public class GlobalNetWorkManager<T> {
    private UserBean curUser;

    public GlobalNetWorkManager() {

    }


    //    ------------------------------------ 静态数据 ------------------------------------

    public Object StaticNetBean(SoapObject property, T bean) {
        if (bean instanceof UserBean) {
            try {
                curUser = (UserBean) bean;
                curUser.orgID = property.getProperty("orgID").toString();
                curUser.orgName = property.getProperty("orgName").toString();
                curUser.jobNumber = property.getProperty("jobNumber").toString();
                curUser.clgCode = property.getProperty("clgCode").toString();
                curUser.clgName = property.getProperty("clgName").toString();
                curUser.uCode = property.getProperty("uCode").toString();
                curUser.uName = property.getProperty("uName").toString();
                curUser.manType = property.getProperty("manType").toString();
                curUser.pID = property.getProperty("pID").toString();
            } catch (Exception e) {
                LogUtil.e("Exception : ", e.toString());
            } finally {
                curUser.userID = property.getProperty("userID").toString();
                curUser.workNumber = property.getProperty("workNumber").toString();
                curUser.userType = property.getProperty("userType").toString();
                curUser.userName = property.getProperty("userName").toString();
                curUser.sysUserDesc = property.getProperty("sysUserDesc").toString();
                curUser.passWord = property.getProperty("passWord").toString();
                curUser.userCName = property.getProperty("userCName").toString();
                curUser.loginMode = property.getProperty("loginMode").toString();
                curUser.userIP = property.getProperty("userIP").toString();
                curUser.dynaPSW = property.getProperty("dynaPSW").toString();
                curUser.loginMessage = property.getProperty("loginMessage").toString();
            }
            return curUser;
        } else if (bean instanceof UserAboutBean) {
            UserAboutBean userAboutBean = (UserAboutBean) bean;
            userAboutBean.userRolesDesc = property.getProperty("userRolesDesc").toString();
            userAboutBean.roleLevel = property.getProperty("roleLevel").toString();
        }
        return null;
    }


//    ------------------------------------ 网络请求 ------------------------------------

    /**
     * @param requestBody 请求体
     * @param httpName    接口名
     * @param Url         接口路径
     * @param Code        响应码
     */
    public static void getNetWorkData(Map<String, Object> requestBody, String httpName, String Url, final int Code, Context context) {
        RequestQueue.getQueue().add(new SoapRequest(context, Url, httpName, requestBody, new Request.SoapRequestListener<SoapObject>() {
            @Override
            public void onSuccess(SoapObject result) {
                RxBus.getDefault().post(new CommonEvent(Code, result));
                Log.e("GlobalNetWorkManager", "onSuccess : " + result.toString());
            }

            @Override
            public void onError(String error) {
                RxBus.getDefault().post(new CommonEvent(NetEventCode.NETWORK_ERROR, error));
                LogUtil.e("GlobalNetWorkManager", "NetWorkData : " + error);
            }
        }));
    }


}
