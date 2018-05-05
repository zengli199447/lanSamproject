package com.example.administrator.landapplication.network;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.administrator.landapplication.global.DataClass;
import com.example.administrator.landapplication.model.DataManager;
import com.example.administrator.landapplication.model.db.entity.DictionaryContentChildInfo;
import com.example.administrator.landapplication.model.db.entity.DictionaryContentInfo;
import com.example.administrator.landapplication.model.event.CommonEvent;
import com.example.administrator.landapplication.model.event.EventCode;
import com.example.administrator.landapplication.model.rxtools.RxBus;
import com.example.administrator.landapplication.soaptools.Request;
import com.example.administrator.landapplication.soaptools.RequestQueue;
import com.example.administrator.landapplication.soaptools.SoapRequest;
import com.example.administrator.landapplication.soaptools.UrlList;
import com.example.administrator.landapplication.utils.LogUtil;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/25.
 * 字典数据获取处理
 */

public class DictionaryNetWork {

    private String TAG = "DictionaryNetWork";
    private Context context;
    public DataManager dataManager;
    private int index = 0;
    private final String[] tableNames = new String[]{"LandUse", "SoilType", "CropTypes", "IrrigationWaterType", "Topography", "SamplingEquipment", "OrganicSample", "SoilColor", "SoilTexture", "SoilMoisture", "Weather", "PeripheralLocation"};
    private String[] classCode = {"1100", " ", "1107", "1109", "1103", "1202", "1204", "1304", "1306", "1308", "1600", "1111"};
    private List<String> codeList = new ArrayList<>();
    private List<String> valueList = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (index < msg.what) {
                index = msg.what;
                getDictionaryUpDataStatus(msg.what);
                RxBus.getDefault().post(new CommonEvent(EventCode.PROGRESS_UPDATA, String.valueOf(msg.what)));
            } else {
                getDictionaryData(msg.what);
            }
        }
    };

    public DictionaryNetWork(Context context, DataManager dataManager) {
        this.context = context;
        this.dataManager = dataManager;
    }

    //字典表
    public void getDictionaryUpDataStatus(int status) {
        Map<String, Object> param = new HashMap<>();
        String LastUpdateTimeType = new StringBuffer().append(status).append("Dictionary").toString();
        switch (status) {
            case 0: //土地利用（是否更新）
                param.put("TDLY", 1100);
                getNetWorkData(param, UrlList.TDLYLastUpdateTime, UrlList.TDLYCodeDictionary, 0, "", "", LastUpdateTimeType, status);
                break;

            case 1: //土壤类型（是否更新）
                getNetWorkData(param, UrlList.TRLXLastUpdateTime, UrlList.TRLX_CODE_DICTIONARY, 0, "", "", LastUpdateTimeType, status);
                break;

            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11: //作物类型,灌溉水类型,地形地貌,采样器具,样品容器,土壤颜色,土壤质地,土壤湿度,天气,其他位置（是否更新）
                param.put("classCode", classCode[status]);
                getNetWorkData(param, UrlList.AllLastUpdateTime, UrlList.CdManager, 0, "", "", LastUpdateTimeType, status);
                break;

        }
    }

    //字典表
    public void getDictionaryData(int status) {
        Map<String, Object> param = new HashMap<>();
        switch (status) {
            case 0: //土地利用
                param.put("TDLY", "1100");
                getNetWorkData(param, UrlList.queryTDLY, UrlList.TDLYCodeDictionary, 1, "DM", "MC", "", status);
                break;
            case 1: //土壤主类
                getNetWorkData(param, UrlList.queryTRLX, UrlList.TRLX_CODE_DICTIONARY, 1, "TZDM", "TLMC", "", status);
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11: //作物类型,灌溉水类型,地形地貌,采样器具,样品容器,土壤颜色,土壤质地,土壤湿度,天气
                param.put("classCode", classCode[status]);
                param.put("colList", "");
                param.put("strWhere", "");
                param.put("strOrder", "");
                getNetWorkData(param, UrlList.querycodeDictionaryClass, UrlList.CdManager, 1, "objCode", "objDesc", "", status);
                break;
        }
    }


    /**
     * 字典表
     *
     * @param requestBody 请求体
     * @param httpName    接口名
     * @param Url         接口路径
     */
    public void getNetWorkData(Map<String, Object> requestBody, String httpName, String Url, final Integer FormartDictionaryType, final String DbCode, final String DbValue, final String LastUpdateTimeType, final int DictionaryType) {
        RequestQueue.getQueue().add(new SoapRequest(context, Url, httpName, requestBody, new Request.SoapRequestListener<SoapObject>() {
            @Override
            public void onSuccess(SoapObject result) {
                Log.e("DictionaryNetWork", "onSuccess : " + result.toString());
                switch (FormartDictionaryType) {
                    case 0: //更新时间
                        if (result != null) {
                            String string = result.getProperty("getLastUpdateTimeResult").toString();
                            if (DataClass.DbInitDictionaryVersion(dataManager, LastUpdateTimeType, string)) {
                                handler.sendEmptyMessage(DictionaryType);
                            } else {
                                handler.sendEmptyMessage(DictionaryType + 1);
                            }
                        }
                        break;
                    case 1: //字典内容
                        if (DbCode.equals("TZDM") && DbValue.equals("TLMC"))
                            deleteDictionarySoilAndTheClassInfo();
                        deleteDictionaryInfo(DictionaryType);
                        if (result.getProperty(0) != null && ((SoapObject) result.getProperty(0)).getProperty("diffgram") != null) {
                            SoapObject diffgram = (SoapObject) ((SoapObject) ((SoapObject) result.getProperty(0)).getProperty("diffgram")).getProperty(0);
                            int propertyCount = diffgram.getPropertyCount();
                            for (int i = 0; i < propertyCount; i++) {
                                SoapObject soapObject = (SoapObject) diffgram.getProperty(i);
                                String code = soapObject.getProperty(DbCode).toString();
                                String value = soapObject.getProperty(DbValue).toString();
                                LogUtil.e(TAG, "code : " + code + " - " + "value : " + value);
                                LogUtil.e(TAG, "tableNames[DictionaryType] " + tableNames[DictionaryType]);
                                DataClass.DbInitDictionaryContentInfo(dataManager, tableNames[DictionaryType], code, value);
                                if (DbCode.equals("TZDM") && DbValue.equals("TLMC")) {
                                    Map<String, Object> param = new HashMap<>();
                                    param.put("TRLX", code);
                                    DbSoilTypeInfo(param, UrlList.queryTRYL, UrlList.TRLX_CODE_DICTIONARY, "TZDM", "TLMC", value);
                                }
                            }
                        }
                        handler.sendEmptyMessage(DictionaryType + 1);
                        LogUtil.e(TAG, "dataManager.loadDictionaryContentInfo().size() - : " + dataManager.loadDictionaryContentInfo().size());
                        break;
                }
            }

            @Override
            public void onError(String error) {
                LogUtil.e("DictionaryNetWork", "NetWorkData : " + error);
                handler.sendEmptyMessage(DictionaryType);
            }
        }));
    }

    /**
     * 亚类信息
     *
     * @param requestBody 请求体
     * @param httpName    接口名
     * @param Url         接口路径
     */
    private void DbSoilTypeInfo(Map<String, Object> requestBody, String httpName, String Url, final String DbValue, final String DbCode, final String tableName) {
        RequestQueue.getQueue().add(new SoapRequest(context, Url, httpName, requestBody, new Request.SoapRequestListener<SoapObject>() {
            @Override
            public void onSuccess(SoapObject result) {
                if (result.getProperty(0) != null && ((SoapObject) result.getProperty(0)).getProperty("diffgram") != null) {
                    SoapObject object = (SoapObject) ((SoapObject) result.getProperty(0)).getProperty("diffgram");
                    if (object.getPropertyCount() > 0) {
                        SoapObject diffgram = (SoapObject) ((SoapObject) ((SoapObject) result.getProperty(0)).getProperty("diffgram")).getProperty(0);
                        int propertyCount = diffgram.getPropertyCount();
                        for (int i = 0; i < propertyCount; i++) {
                            SoapObject soapObject = (SoapObject) diffgram.getProperty(i);
                            DataClass.DbInitDictionaryContentChildInfo(dataManager, tableName, OrValue(soapObject.getProperty(DbValue).toString()), soapObject.getProperty(DbCode).toString());
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {
                LogUtil.e("DbSoilTypeInfo", "NetWorkData : " + error);
            }
        }));
    }

    //根据类型名称删除
    private void deleteDictionaryInfo(int DictionaryType) {
        List<DictionaryContentInfo> dictionaryContentInfos = dataManager.queryDictionaryContentInfo(tableNames[DictionaryType]);
        LogUtil.e(TAG, "dictionaryContentInfos : " + dictionaryContentInfos.size());
        if (dictionaryContentInfos != null && dictionaryContentInfos.size() > 0) {
            for (int i = 0; i < dictionaryContentInfos.size(); i++) {
                dataManager.deleteDictionaryContentInfo(dictionaryContentInfos.get(i).getTypeName());
            }
        }
    }

    //土壤亚类删除
    private void deleteDictionarySoilAndTheClassInfo() {
        List<DictionaryContentInfo> dictionaryContentInfos = dataManager.queryDictionaryContentInfo(tableNames[1]);
        for (int i = 0; i < dictionaryContentInfos.size(); i++) {
            List<DictionaryContentChildInfo> dictionaryContentChildInfos = dataManager.queryDictionaryContentChildInfoInfo(dictionaryContentInfos.get(i).getTypeName());
            for (int j = 0; j < dictionaryContentChildInfos.size(); j++) {
                LogUtil.e(TAG, "deleteDictionarySoilAndTheClassInfo : " + j);
                dataManager.deleteDictionaryContentChildInfo(dictionaryContentChildInfos.get(j).getTypeName());
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
