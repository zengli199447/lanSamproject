package com.example.administrator.landapplication.soaptools;

import com.example.administrator.landapplication.bean.UserBean;

import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;


/**
 * Created by Administrator on 2018/3/16.
 */

public class OaSoapHeader {

    private static final String NAMESPACE = "http://www.OA.com/webservices/";

    public static Element[] setSoapHeader() {

        Element[] headers = new Element[1];
        headers[0] = new Element().createElement(NAMESPACE, "pageHeader");
        Element curUser = new Element().createElement(NAMESPACE, "curUser");
        // userID
        Element userID = new Element().createElement(NAMESPACE, "userID");
        userID.addChild(Node.TEXT, UserBean.userID);
        curUser.addChild(Node.ELEMENT, userID);

        // workNumber
        Element workNumber = new Element().createElement(NAMESPACE, "workNumber");
        workNumber.addChild(Node.TEXT, UserBean.workNumber);
        curUser.addChild(Node.ELEMENT, workNumber);

        // userType
        Element userType = new Element().createElement(NAMESPACE, "workNumber");
        workNumber.addChild(Node.TEXT, UserBean.userType);
        curUser.addChild(Node.ELEMENT, userType);

        // userName
        Element userName = new Element().createElement(NAMESPACE, "userName");
        userName.addChild(Node.TEXT, UserBean.userName);
        curUser.addChild(Node.ELEMENT, userName);

        // sysUserDesc
        Element sysUserDesc = new Element().createElement(NAMESPACE, "sysUserDesc");
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

        // loginMode
        Element loginMode = new Element().createElement(NAMESPACE, "loginMode");
        loginMode.addChild(Node.TEXT, UserBean.loginMode);
        curUser.addChild(Node.ELEMENT, loginMode);

        // orgID
        Element orgID = new Element().createElement(NAMESPACE, "orgID");
        orgID.addChild(Node.TEXT, UserBean.orgID);
        curUser.addChild(Node.ELEMENT, orgID);

        // orgName
        Element orgName = new Element().createElement(NAMESPACE, "orgName");
        orgName.addChild(Node.TEXT, UserBean.orgName);
        curUser.addChild(Node.ELEMENT, orgName);

        // userIP
        Element userIP = new Element().createElement(NAMESPACE, "userIP");
        userIP.addChild(Node.TEXT, UserBean.userIP);
        curUser.addChild(Node.ELEMENT, userIP);

        // dynaPSW
        Element dynaPSW = new Element().createElement(NAMESPACE, "dynaPSW");
        dynaPSW.addChild(Node.TEXT, UserBean.dynaPSW);
        curUser.addChild(Node.ELEMENT, dynaPSW);

        // loginMessage
        Element loginMessage = new Element().createElement(NAMESPACE, "loginMessage");
        loginMessage.addChild(Node.TEXT, UserBean.loginMessage);
        curUser.addChild(Node.ELEMENT, loginMessage);

        headers[0].addChild(Node.ELEMENT, curUser);
        return headers;
    }

}
