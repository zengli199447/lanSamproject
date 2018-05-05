package com.example.administrator.landapplication.model.http.api;

import com.example.administrator.landapplication.bean.OuterLayerEntity;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2018/4/10.
 */

public interface MyApis {

    String HOST = "http://192.168.14.199:8088/portal/r/";

    /**
     * 上传文件
     *
     * @return
     */
    @POST("jd")
    Flowable<OuterLayerEntity> UpLoadFile(@Body MultipartBody multipartBody);

    /**
     * 文本提交
     *
     * @return
     */
    @POST("jd")
    Flowable<OuterLayerEntity> UpLoadAbout(@QueryMap Map<String, String> map);


}
