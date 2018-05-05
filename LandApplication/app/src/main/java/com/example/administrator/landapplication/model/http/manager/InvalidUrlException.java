package com.example.administrator.landapplication.model.http.manager;

import android.text.TextUtils;

/**
 * Created by Administrator on 2017/10/30.
 */

public class InvalidUrlException extends RuntimeException {

    public InvalidUrlException(String url) {
        super("You've configured an invalid url : " + (TextUtils.isEmpty(url) ? "EMPTY_OR_NULL_URL" : url));
    }
}
