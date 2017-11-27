package com.kson.mvpframework.view;

import android.view.View;
import android.widget.Button;

import com.kson.mvpframework.R;
import com.kson.mvpframework.base.BaseActivity;
import com.kson.mvpframework.common.Api;
import com.kson.mvpframework.utils.LoggerManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/10/11
 * Description:
 */
public class InterceptorActivity extends BaseActivity {
    private Button click;

    @Override
    public int bindLayout() {
        return R.layout.activity_interception;
    }

    @Override
    public void setListener() {
        click.setOnClickListener(this);

    }

    @Override
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.click:

                //走拦截器方法
//                interceptor();

                try {
                    cache();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    private void cache() throws IOException {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(new Cache(getExternalCacheDir().getAbsoluteFile(), 1024 * 1024));
//        CacheControl cacheControl = new CacheControl.Builder()
//                .maxAge(60, TimeUnit.SECONDS)
//                .build();
        final OkHttpClient client = builder.build();

        final Request request = new Request.Builder().cacheControl(new CacheControl.Builder().onlyIfCached().maxAge(1000, TimeUnit.SECONDS).build()).url(Api.GETAD).get().build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response1 = client.newCall(request).execute();
                    System.out.println("response1:" + response1);
                    System.out.println("response1 cache:" + response1.cacheResponse());
                    System.out.println("response1 network:" + response1.networkResponse());

                    response1.body().close();
                    Response response2 = client.newCall(request).execute();
                    System.out.println("response2:" + response2);
                    System.out.println("response2 cache:" + response2.cacheResponse());
                    System.out.println("response2 network:" + response2.networkResponse());
                    response2.body().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

    /**
     * 拦截器方法
     */
    private void interceptor() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.writeTimeout(1, TimeUnit.HOURS);
        builder.readTimeout(1, TimeUnit.HOURS);
        builder.connectTimeout(100, TimeUnit.SECONDS);
        builder.addNetworkInterceptor(networkInterceptor);
        builder.addInterceptor(appInterceptor);

        OkHttpClient client = builder.build();

        Request request = new Request.Builder().url(Api.GETAD).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                call.cancel();

            }
        });

        client.dispatcher().cancelAll();

    }

    @Override
    public void initView() {

        click = (Button) findViewById(R.id.click);

    }

    @Override
    public void initData() {

    }

    /**
     * 应用拦截器
     */
    Interceptor appInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            LoggerManager.getInstance(tag).e("app interceptor start:");
            Request request = chain.request();
            LoggerManager.getInstance(tag).e("url:" + request.url().toString());
            Response response = chain.proceed(request);
            

            LoggerManager.getInstance(tag).e("app interceptor end:" + response);
            return response;
        }
    };

    /**
     * 网络拦截器
     */
    Interceptor networkInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            LoggerManager.getInstance(tag).e("network interceptor start:");
            Request request = chain.request();
            LoggerManager.getInstance(tag).e("url:" + request.url().toString());
            Response response = chain.proceed(request);

            LoggerManager.getInstance(tag).e("result:" + response.body().string());
            LoggerManager.getInstance(tag).e("network interceptor end:" + response);
            return response;
        }
    };
}
