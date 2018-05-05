package com.example.administrator.landapplication.soaptools;

import android.content.Context;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.kxml2.kdom.Element;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2018/3/16.
 */

public abstract class  Request<T> implements Callable<Boolean> {

    protected static final String NAMESPACE = "http://www.OA.com/webservices/";

    protected Context context;
    protected String url;
    protected String method;
    protected int type = 1;
    protected boolean needHeader = true;
    protected Element[] headers;
    protected PropertyInfo pi;
    protected SoapObject rpc;
    protected SoapSerializationEnvelope envelope;
    protected String tag;
    protected Future<Boolean> result;

    public interface SoapRequestListener<T> {
        public void onSuccess(T result);

        public void onError(String error);
    }

    public interface ListRequestListener<T> {
        public void onSuccess(T listResult, int pages);

        public void onError(String error);
    }

    protected void setType(int type) {
        if (type == 1) {
            this.type = 1;
        } else {
            this.type = 2;
        }
    }

    protected void headEnable(boolean enable) {
        needHeader = enable;
    }

    protected Element[] setdata(int count) {

        switch (count) {
            case 1:
                headers = OaSoapHeader.setSoapHeader();
                break;
            case 2:
                headers = SoapHeader.setSoapHeader();
                break;
            default:
                break;
        }
        return headers;
    }

    protected void setTag(String tag) {
        this.tag = tag;
    }

    protected String getTag() {
        return tag;
    }

    protected void cancel() {
        if (result != null) {
            result.cancel(true);
        }
    }

    protected void setTask(Future<Boolean> task) {
        result = task;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        //Log.d("GC Message", rpc.toString());
    }
}
