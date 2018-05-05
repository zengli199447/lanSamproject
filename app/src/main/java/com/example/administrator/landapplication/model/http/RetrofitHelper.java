package com.example.administrator.landapplication.model.http;

import com.example.administrator.landapplication.bean.OuterLayerEntity;
import com.example.administrator.landapplication.model.http.api.MyApis;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2018/4/10.
 */

public class RetrofitHelper implements HttpHelper{

    private MyApis mMyApiService;

    @Inject
    public RetrofitHelper(MyApis myApiService) {
        this.mMyApiService = myApiService;
    }

    @Override
    public Flowable<OuterLayerEntity> UpLoadAbout(Map map) {
        return mMyApiService.UpLoadAbout(map);
    }

    @Override
    public Flowable<OuterLayerEntity> UpLoadFile(MultipartBody multipartBody) {
        return mMyApiService.UpLoadFile(multipartBody);
    }
}
