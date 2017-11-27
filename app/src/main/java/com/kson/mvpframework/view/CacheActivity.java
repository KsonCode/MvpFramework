package com.kson.mvpframework.view;

import android.view.View;

import com.kson.mvpframework.R;
import com.kson.mvpframework.base.BaseActivity;
import com.kson.mvpframework.common.Api;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CacheActivity extends BaseActivity {


    @Override
    public int bindLayout() {
        return R.layout.activity_cache;
    }

    @Override
    public void setListener() {

    }

    @Override
    public void Click(View view) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    public void click(View view){

        cache();
    }

    /**
     * 测试okhttp缓存功能
     */
    private void cache() {
        int totalsize = 10*1024*1024;//10m
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(1, TimeUnit.HOURS);
        builder.writeTimeout(1,TimeUnit.HOURS);
        builder.connectTimeout(1,TimeUnit.HOURS);
//        builder.addInterceptor();//
        File file = getExternalCacheDir().getAbsoluteFile();
        System.out.println("filepath:"+getExternalCacheDir().getAbsoluteFile());
        builder.cache(new Cache(file,totalsize));//LruCache算法（内存和本地sdcard／硬盘），最近最少使用算法：移除掉，DiskLruCache

        final OkHttpClient client = builder.build();
        final Request request = new Request.Builder().cacheControl(new CacheControl.Builder().maxAge(60000,TimeUnit.HOURS).build()).get().url(Api.GETAD).build();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//            Call reponse = client.newCall(request);
                    Response resonse = client.newCall(request).execute();//同步
                    System.out.println("response1："+resonse);
                    System.out.println("response1 cache："+resonse.cacheResponse());
                    System.out.println("response1 network："+resonse.networkResponse());

                    resonse.body().close();//才能正常的存储到sdcard


                    Response resonse2 = client.newCall(request).execute();//同步
                    System.out.println("response2："+resonse2);
                    System.out.println("response2 cache："+resonse2.cacheResponse());
                    System.out.println("response2 network："+resonse2.networkResponse());
                    resonse2.body().close();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }
}
