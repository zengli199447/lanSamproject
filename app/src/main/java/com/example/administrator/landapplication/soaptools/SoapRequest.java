package com.example.administrator.landapplication.soaptools;

import android.app.Activity;
import android.content.Context;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/16.
 */

public class SoapRequest extends Request<SoapObject> {
    private Map<String, Object> param;
    private SoapRequestListener<SoapObject> mListener;
    private int contextType = 0;   //0=activity,1:service

    public SoapRequest(Context context, String url, String method,
                       Map<String, Object> param, SoapRequestListener<SoapObject> listener) {
        this.url = url.trim();
        this.context = context;
        this.method = method.trim();
        this.param = param;
        mListener = listener;
    }
    public SoapRequest(Context context, String url, String method,
                       Map<String, Object> param, SoapRequestListener<SoapObject> listener,int contextType) {
        this.url = url.trim();
        this.context = context;
        this.method = method.trim();
        this.param = param;
        this.contextType= contextType;
        mListener = listener;
    }

    @Override
    public Boolean call() {

        try {
            rpc = new SoapObject(NAMESPACE, method);
            pi = new PropertyInfo();
            if (param != null) {
                for (Map.Entry<String, Object> entry : param.entrySet()) {
                    pi = new PropertyInfo();
                    pi.setName(entry.getKey());
                    pi.setValue(param.get(entry.getKey()));
                    rpc.addProperty(pi);
                }
            }
            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            if (needHeader) {
                headers = setdata(type);
                envelope.headerOut = headers;
            } else {
                envelope.headerOut = null;
            }
            envelope.bodyOut = rpc;
            envelope.dotNet = true;
            envelope.setOutputSoapObject(rpc);
            MyAndroidHttpTransport ht = new MyAndroidHttpTransport(url);
            ht.call((NAMESPACE+method), envelope);
        } catch (final IOException e) {
            if(contextType==0) {

                ((Activity) context).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mListener.onError(e.getMessage());
                    }
                });
            }else {
                mListener.onError(e.getMessage());

            }
            e.printStackTrace();
            return false;
        } catch (final XmlPullParserException e) {
            if(contextType==0) {
                ((Activity) context).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mListener.onError(e.getMessage());
                    }
                });
                e.printStackTrace();
                return false;
            }else{
                mListener.onSuccess((SoapObject) envelope.bodyIn);
            }
        }
        if (envelope.bodyIn != null) {
            if(contextType==0){
                ((Activity) context).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mListener.onSuccess((SoapObject) envelope.bodyIn);
                    }
                });
            }else{
                mListener.onSuccess((SoapObject) envelope.bodyIn);
            }

        } else {
            if(contextType==0) {
                ((Activity) context).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mListener.onError("返回结果为空,有可能是WebService服务端错误!");
                    }
                });
            }else {
                mListener.onError("返回结果为空,有可能是WebService服务端错误!");
            }
        }
        return true;
    }

}
