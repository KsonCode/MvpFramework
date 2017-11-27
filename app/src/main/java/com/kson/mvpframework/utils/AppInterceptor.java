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
public class AppInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        LoggerManager.getInstance("app interceptor:").e("app interceptor start:");
        Request request = chain.request();
        LoggerManager.getInstance("app interceptor:").e("url:" + request.url().toString());
        Response response = chain.proceed(request);


        LoggerManager.getInstance("app interceptor:").e("app interceptor end:" + response);
        return response;
    }
}
