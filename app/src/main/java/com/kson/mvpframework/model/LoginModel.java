package com.kson.mvpframework.model;

import android.content.Context;

import com.kson.mvpframework.common.Api;
import com.kson.mvpframework.utils.OkCallback;
import com.kson.mvpframework.utils.OkHttpMethod;
import com.kson.mvpframework.utils.OkhttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/10/08
 * Description:首页model
 */
public class LoginModel {
    public void login(Context context,String mobile, String pwd, OkCallback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("password", pwd);
        OkhttpUtils.getOkhttpInstance(context).call(OkHttpMethod.POST,Api.LOGIN,params,callback);
    }
}
