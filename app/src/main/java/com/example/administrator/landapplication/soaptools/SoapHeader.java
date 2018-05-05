package com.example.administrator.landapplication.soaptools;

import com.example.administrator.landapplication.bean.UserBean;

import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;


/**
 * Created by Administrator on 2018/3/16.
 */

public class SoapHeader {

    private static final String NAMESPACE = "http://www.OA.com/webservices/";

    /**
     * 设置soapheader头
     */
    public static Element[] setSoapHeader() {

        Element[] headers;
        headers = new Element[1];
        headers[0] = new Element().createElement(NAMESPACE, "pageHeader ");
        Element curUser = new Element().createElement(NAMESPACE, "curUser");
        // userID
        Element userID = new Element().createElement(NAMESPACE, "userID");
        userID.addChild(Node.TEXT, UserBean.userID);
        curUser.addChild(Node.ELEMENT, userID);
        // userName
        Element userName = new Element().createElement(NAMESPACE, "userName");
        userName.addChild(Node.TEXT, UserBean.userName);
        curUser.addChild(Node.ELEMENT, userName);
        // sysUserDesc
        Element sysUserDesc = new Element().createElement(NAMESPACE,
                "sysUserDesc");
        sysUserDesc.addChild(Node.TEXT, UserBean.sysUserDesc);
        curUser.addChild(Node.ELEMENT, sysUserDesc);
        // passWord
        Element passWord = new Element().createElement(NAMESPACE, "passWord");
        passWord.addChild(Node.TEXT, UserBean.passWord);
        curUser.addChild(Node.ELEMENT, passWord);
        // userCName
        Element userCName = new Element().createElement(NAMESPACE, "userCName");
        userCName.addChild(Node.TEXT, UserBean.userCName);
        curUser.addChild(Node.ELEMENT, userCName);
        // pID
        Element pID = new Element().createElement(NAMESPACE, "pID");
        pID.addChild(Node.TEXT, UserBean.pID);
        curUser.addChild(Node.ELEMENT, pID);
        // _ip
        Element _ip = new Element().createElement(NAMESPACE, "_ip");
        _ip.addChild(Node.TEXT, UserBean.userIP);
        curUser.addChild(Node.ELEMENT, _ip);
        // _dynaPSW
        Element _dynaPSW = new Element().createElement(NAMESPACE, "_dynaPSW");
        _dynaPSW.addChild(Node.TEXT, UserBean.dynaPSW);
        curUser.addChild(Node.ELEMENT, _dynaPSW);
        // _message
        Element _message = new Element().createElement(NAMESPACE, "_dynaPSW");
        _message.addChild(Node.TEXT, UserBean.loginMessage);
        curUser.addChild(Node.ELEMENT, _message);
        // clgCode
        Element clgCode = new Element().createElement(NAMESPACE, "clgCode");
        clgCode.addChild(Node.TEXT, UserBean.clgCode);
        curUser.addChild(Node.ELEMENT, clgCode);
        // clgName
        Element clgName = new Element().createElement(NAMESPACE, "clgName");
        clgName.addChild(Node.TEXT, UserBean.clgName);
        curUser.addChild(Node.ELEMENT, clgName);
        // uCode
        Element uCode = new Element().createElement(NAMESPACE, "uCode");
        uCode.addChild(Node.TEXT, UserBean.uCode);
        curUser.addChild(Node.ELEMENT, uCode);
        // uName
        Element uName = new Element().createElement(NAMESPACE, "uName");
        uCode.addChild(Node.TEXT, UserBean.uName);
        curUser.addChild(Node.ELEMENT, uName);

        headers[0].addChild(Node.ELEMENT, curUser);
        return headers;


    }

}
