package com.kson.mvpframework.view;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/10/08
 * Description:view的基类接口，抽取公共方法
 */
public interface IBaseView {
    void showProgress();
    void hideProgress();
    void failure(String msg);
}
