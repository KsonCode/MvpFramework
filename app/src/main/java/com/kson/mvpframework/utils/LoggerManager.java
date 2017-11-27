package com.kson.mvpframework.utils;

import android.util.Log;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/10/08
 * Description:单例模式
 */
public class LoggerManager {

    private static  String tag = "default_tag";

    private static LoggerManager loggerManager;//

    private LoggerManager(String tag){
        this.tag = tag;
    }

    public static LoggerManager getInstance(String tag){

        if (loggerManager==null){
            synchronized (LoggerManager.class){
                if(loggerManager==null){
                    loggerManager = new LoggerManager(tag);
                }
            }
        }

        return loggerManager;


    }


    public void v(String msg){
        Log.v(tag,msg);
    }
    public void e(String msg){
        Log.e(tag,msg);
    }



}
