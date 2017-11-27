package com.kson.mvpframework.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.kson.mvpframework.utils.OkCallback;
import com.kson.mvpframework.model.entity.UserEntity;
import com.kson.mvpframework.model.LoginModel;
import com.kson.mvpframework.view.MainView;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/10/08
 * Description:主页的控制器
 */
public class LoginPresenter {

    private MainView mainview;
    private LoginModel mainmodel;
    private Context context;


    public LoginPresenter(MainView mainview, Context context) {

        this.context = context;
        this.mainview = mainview;
        mainmodel = new LoginModel();

    }

    public void login(String mobile, String pwd) {

        if (TextUtils.isEmpty(mobile)) {
            mainview.nameError("手机号不能为空");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            mainview.nameError("密码不能为空");
            return;
        }

        mainview.showProgress();

        mainmodel.login(context, mobile, pwd, new OkCallback() {
            @Override
            public void onFailure(String e,String msg) {
                mainview.failure(msg);
                mainview.hideProgress();
            }

            @Override
            public void onResponse(String result) {
                UserEntity user = new Gson().fromJson(result, UserEntity.class);
                if (user != null) {
                    if ("0".equals(user.code)) {
                        mainview.loginSuccess(user.msg);
                    } else {
                        mainview.loginError(user.msg);
                    }
                }
                mainview.hideProgress();

            }
        });

    }

    public void register() {

    }

    public void onDetroy() {
        mainview = null;
    }
}
