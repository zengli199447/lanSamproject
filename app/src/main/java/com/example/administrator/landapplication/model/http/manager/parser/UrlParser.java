package com.example.administrator.landapplication.model.http.manager.parser;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/10/30.
 */

public interface UrlParser {
    /**
     * 将 {@link RetrofitUrlManager#mDomainNameHub} 中映射的 Url 解析成完整的{@link HttpUrl}
     * 用来替换 @{@link Request#url} 达到动态切换 Url
     *
     * @param domainUrl
     * @return
     */
    HttpUrl parseUrl(HttpUrl domainUrl, HttpUrl url);
}
