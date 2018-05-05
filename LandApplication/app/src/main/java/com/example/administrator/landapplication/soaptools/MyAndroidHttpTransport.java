package com.example.administrator.landapplication.soaptools;

import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.ServiceConnection;
import org.ksoap2.transport.ServiceConnectionSE;

import java.io.IOException;

/**
 * Created by Administrator on 2018/4/11.
 */

public class MyAndroidHttpTransport extends HttpTransportSE {
    private int timeout = 10000; // 默认超时时间为20s
    public MyAndroidHttpTransport(String url) {
        super(url);
    }
    public MyAndroidHttpTransport(String url, int timeout) {
        super(url);
        this.timeout = timeout;
    }
    //此方法使得超时有效
    public ServiceConnection getServiceConnection() throws IOException {
        ServiceConnectionSE serviceConnection = new ServiceConnectionSE(this.url,timeout);
        return serviceConnection;
    }
}
