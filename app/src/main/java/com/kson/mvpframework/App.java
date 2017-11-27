package com.kson.mvpframework;

import com.kson.mvpframework.base.BaseApplication;
import com.kson.mvpframework.utils.CrashHandler;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/10/08
 * Description:
 */
public class App extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        CrashHandler.getInstance().init(this);

//        int i = 5/0;

    }




    @Override
    public void onTerminate() {
        super.onTerminate();
        System.exit(0);
    }
}
