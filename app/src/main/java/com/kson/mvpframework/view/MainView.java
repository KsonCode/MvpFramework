package com.kson.mvpframework.view;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/10/08
 * Description:
 */
public interface MainView extends IBaseView{
    void nameError(String msg);
    void passError(String msg);
    void loginSuccess(String msg);
    void loginError(String msg);
}
