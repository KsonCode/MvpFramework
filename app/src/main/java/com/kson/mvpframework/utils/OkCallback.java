package com.kson.mvpframework.utils;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/10/11
 * Description:okhttp响应的回调接口
 */
public interface OkCallback {
    void onFailure(String e,String msg);//e:异常数据，msg：请求失败提示
    void onResponse(String result);//请求成功json串
}
