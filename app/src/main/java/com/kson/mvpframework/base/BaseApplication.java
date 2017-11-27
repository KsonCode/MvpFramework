package com.kson.mvpframework.base;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/10/08
 * Description:封装的基类application
 */
public class BaseApplication extends Application {

    public static Context context;
    public static int screen_width = -1;
    public static int screen_height = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        getScreen();
    }

    private void getScreen() {

        DisplayMetrics display = getResources().getDisplayMetrics();
        screen_width = display.widthPixels;
        screen_height = display.heightPixels;
    }
}
