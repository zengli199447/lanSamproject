package com.example.administrator.landapplication.soaptools;

import com.example.administrator.landapplication.global.MyApplication;

import java.util.concurrent.Future;


/**
 * Created by Administrator on 2018/3/16.
 */

public class RequestQueue {

    private volatile static RequestQueue mQueue;

    public static RequestQueue getQueue() {
        if (mQueue == null) {
            synchronized (RequestQueue.class) {
                if (mQueue == null) {
                    mQueue = new RequestQueue();
                }
            }
        }
        return mQueue;
    }

    public void add(Request request) {
        Future<Boolean> result = MyApplication.executorService.submit(request);
        request.setTask(result);
    }


}


