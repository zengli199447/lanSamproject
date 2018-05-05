package com.example.administrator.landapplication.model.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by Administrator on 2018/4/28.
 * 任务详情
 */

@Entity
public class TaskContentInfo {

    //主键自增长
    @Id(autoincrement = true)
    private Long id;

    //任务Id;
    @Unique
    private String taskId;
    private String CPointID;
    private String planID;
    private String CPointType;
    private String CPointTypeDesc;
    private String CPointName;
    private String isQCPoint;
    private String YY;
    private String JD;
    private String WD;
    private String HB;
    private String XZQHDM;
    private String SJMC;
    private String DJMC;
    private String XQMC;
    private String XZMC;
    private String CJMC;
    private String XCDYDM;
    private String DZ;
    private String FWMS;
    private String TDLY;
    private String TDLYDesc;
    private String TRLX;
    private String TRLXDesc;
    private String TRYL;
    private String TRYLDesc;

    public TaskContentInfo(String taskId, String CPointID, String planID, String CPointType, String CPointTypeDesc, String CPointName, String isQCPoint, String YY, String JD, String WD, String HB, String XZQHDM, String SJMC, String DJMC, String XQMC, String XZMC, String CJMC, String XCDYDM, String DZ, String FWMS, String TDLY, String TDLYDesc, String TRLX, String TRLXDesc, String TRYL, String TRYLDesc) {
        this.taskId = taskId;
        this.CPointID = CPointID;
        this.planID = planID;
        this.CPointType = CPointType;
        this.CPointTypeDesc = CPointTypeDesc;
        this.CPointName = CPointName;
        this.isQCPoint = isQCPoint;
        this.YY = YY;
        this.JD = JD;
        this.WD = WD;
        this.HB = HB;
        this.XZQHDM = XZQHDM;
        this.SJMC = SJMC;
        this.DJMC = DJMC;
        this.XQMC = XQMC;
        this.XZMC = XZMC;
        this.CJMC = CJMC;
        this.XCDYDM = XCDYDM;
        this.DZ = DZ;
        this.FWMS = FWMS;
        this.TDLY = TDLY;
        this.TDLYDesc = TDLYDesc;
        this.TRLX = TRLX;
        this.TRLXDesc = TRLXDesc;
        this.TRYL = TRYL;
        this.TRYLDesc = TRYLDesc;
    }

    @Generated(hash = 1834287526)
    public TaskContentInfo(Long id, String taskId, String CPointID, String planID, String CPointType, String CPointTypeDesc, String CPointName, String isQCPoint, String YY, String JD, String WD, String HB, String XZQHDM, String SJMC, String DJMC, String XQMC, String XZMC, String CJMC, String XCDYDM, String DZ, String FWMS, String TDLY, String TDLYDesc, String TRLX, String TRLXDesc, String TRYL,
            String TRYLDesc) {
        this.id = id;
        this.taskId = taskId;
        this.CPointID = CPointID;
        this.planID = planID;
        this.CPointType = CPointType;
        this.CPointTypeDesc = CPointTypeDesc;
        this.CPointName = CPointName;
        this.isQCPoint = isQCPoint;
        this.YY = YY;
        this.JD = JD;
        this.WD = WD;
        this.HB = HB;
        this.XZQHDM = XZQHDM;
        this.SJMC = SJMC;
        this.DJMC = DJMC;
        this.XQMC = XQMC;
        this.XZMC = XZMC;
        this.CJMC = CJMC;
        this.XCDYDM = XCDYDM;
        this.DZ = DZ;
        this.FWMS = FWMS;
        this.TDLY = TDLY;
        this.TDLYDesc = TDLYDesc;
        this.TRLX = TRLX;
        this.TRLXDesc = TRLXDesc;
        this.TRYL = TRYL;
        this.TRYLDesc = TRYLDesc;
    }

    @Generated(hash = 202662091)
    public TaskContentInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCPointID() {
        return this.CPointID;
    }

    public void setCPointID(String CPointID) {
        this.CPointID = CPointID;
    }

    public String getPlanID() {
        return this.planID;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }

    public String getCPointType() {
        return this.CPointType;
    }

    public void setCPointType(String CPointType) {
        this.CPointType = CPointType;
    }

    public String getCPointTypeDesc() {
        return this.CPointTypeDesc;
    }

    public void setCPointTypeDesc(String CPointTypeDesc) {
        this.CPointTypeDesc = CPointTypeDesc;
    }

    public String getCPointName() {
        return this.CPointName;
    }

    public void setCPointName(String CPointName) {
        this.CPointName = CPointName;
    }

    public String getIsQCPoint() {
        return this.isQCPoint;
    }

    public void setIsQCPoint(String isQCPoint) {
        this.isQCPoint = isQCPoint;
    }

    public String getYY() {
        return this.YY;
    }

    public void setYY(String YY) {
        this.YY = YY;
    }

    public String getJD() {
        return this.JD;
    }

    public void setJD(String JD) {
        this.JD = JD;
    }

    public String getWD() {
        return this.WD;
    }

    public void setWD(String WD) {
        this.WD = WD;
    }

    public String getHB() {
        return this.HB;
    }

    public void setHB(String HB) {
        this.HB = HB;
    }

    public String getXZQHDM() {
        return this.XZQHDM;
    }

    public void setXZQHDM(String XZQHDM) {
        this.XZQHDM = XZQHDM;
    }

    public String getSJMC() {
        return this.SJMC;
    }

    public void setSJMC(String SJMC) {
        this.SJMC = SJMC;
    }

    public String getDJMC() {
        return this.DJMC;
    }

    public void setDJMC(String DJMC) {
        this.DJMC = DJMC;
    }

    public String getXQMC() {
        return this.XQMC;
    }

    public void setXQMC(String XQMC) {
        this.XQMC = XQMC;
    }

    public String getXZMC() {
        return this.XZMC;
    }

    public void setXZMC(String XZMC) {
        this.XZMC = XZMC;
    }

    public String getCJMC() {
        return this.CJMC;
    }

    public void setCJMC(String CJMC) {
        this.CJMC = CJMC;
    }

    public String getXCDYDM() {
        return this.XCDYDM;
    }

    public void setXCDYDM(String XCDYDM) {
        this.XCDYDM = XCDYDM;
    }

    public String getDZ() {
        return this.DZ;
    }

    public void setDZ(String DZ) {
        this.DZ = DZ;
    }

    public String getFWMS() {
        return this.FWMS;
    }

    public void setFWMS(String FWMS) {
        this.FWMS = FWMS;
    }

    public String getTDLY() {
        return this.TDLY;
    }

    public void setTDLY(String TDLY) {
        this.TDLY = TDLY;
    }

    public String getTDLYDesc() {
        return this.TDLYDesc;
    }

    public void setTDLYDesc(String TDLYDesc) {
        this.TDLYDesc = TDLYDesc;
    }

    public String getTRLX() {
        return this.TRLX;
    }

    public void setTRLX(String TRLX) {
        this.TRLX = TRLX;
    }

    public String getTRLXDesc() {
        return this.TRLXDesc;
    }

    public void setTRLXDesc(String TRLXDesc) {
        this.TRLXDesc = TRLXDesc;
    }

    public String getTRYL() {
        return this.TRYL;
    }

    public void setTRYL(String TRYL) {
        this.TRYL = TRYL;
    }

    public String getTRYLDesc() {
        return this.TRYLDesc;
    }

    public void setTRYLDesc(String TRYLDesc) {
        this.TRYLDesc = TRYLDesc;
    }




}
