package com.example.administrator.landapplication.model.http;


import com.example.administrator.landapplication.bean.OuterLayerEntity;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2017/10/27.
 */


public interface HttpHelper {

    Flowable<OuterLayerEntity> UpLoadAbout(Map map);

    Flowable<OuterLayerEntity> UpLoadFile(MultipartBody multipartBody);



}
