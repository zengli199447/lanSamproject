package com.example.administrator.landapplication.utils;

import com.example.administrator.landapplication.model.http.manager.InvalidUrlException;

import okhttp3.HttpUrl;

/**
 * Created by Administrator on 2018/4/10.
 */

public class Utils {
    private Utils() {
        throw new IllegalStateException("do not instantiation me");
    }

    public static HttpUrl checkUrl(String url) {
        HttpUrl parseUrl = HttpUrl.parse(url);
        if (null == parseUrl) {
            throw new InvalidUrlException(url);
        } else {
            return parseUrl;
        }
    }

}
