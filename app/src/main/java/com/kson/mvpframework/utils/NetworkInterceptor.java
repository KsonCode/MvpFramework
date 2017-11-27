package com.kson.mvpframework.utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/10/18
 * Description:
 */
public class NetworkInterceptor implements Interceptor {
    private String tag = "network interceptor";
    @Override
    public Response intercept(Chain chain) throws IOException {
        LoggerManager.getInstance(tag).e("network interceptor start:");
        Request request = chain.request();
        LoggerManager.getInstance(tag).e("url:" + request.url().toString());
        Response response = chain.proceed(request);

        LoggerManager.getInstance(tag).e("result:" + response.body().string());
        LoggerManager.getInstance(tag).e("network interceptor end:" + response);
        return response;
    }
}
