package com.kson.mvpframework.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/10/11
 * Description:封装okhttp，rxjava
 */
public class OkhttpUtils {
    private Context mContext;
    OkHttpClient client;
    //单例模式
    private static OkhttpUtils okhttpInstance;//声明单例模式私有属性

    /**
     * 私有构造方法
     *
     * @param context
     */
    private OkhttpUtils(Context context) {
        this.mContext = context;
        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        final InputStream inputStream;
        try {
            inputStream = mContext.getAssets().open("kson_server.cer"); // 得到证书的输入流
            try {

                trustManager = trustManagerForCertificates(inputStream);//以流的方式读入证书
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{trustManager}, null);
                sslSocketFactory = sslContext.getSocketFactory();

            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }

            client = new OkHttpClient.Builder()
                    .addInterceptor(new LogInterceptor())
                    .addNetworkInterceptor(new NetworkInterceptor())
                    .sslSocketFactory(sslSocketFactory, trustManager)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对外暴露的实例
     *
     * @param context
     * @return
     */
    public static OkhttpUtils getOkhttpInstance(Context context) {
        if (okhttpInstance == null) {
            synchronized (OkhttpUtils.class) {//同步锁机制
                if (okhttpInstance == null) {
                    okhttpInstance = new OkhttpUtils(context);
                }
            }
        }
        return okhttpInstance;
    }

    /**
     * 请求方法
     *
     * @param okHttpMethod 请求方式
     * @param url          请求的url
     * @param params       请求参数集合
     * @param okcallback   自定义的请求回调接口
     */
    public void call(OkHttpMethod okHttpMethod, String url, Map<String, Object> params, final OkCallback okcallback) {
        Request request = null;
        //okhttpclient对象构建者
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.connectTimeout(5, TimeUnit.SECONDS);
//        builder.readTimeout(5, TimeUnit.SECONDS);
//        builder.writeTimeout(5, TimeUnit.SECONDS);
//        builder.addInterceptor(new LoggingInterceptor());

        //okhttpclient对象
//        client = builder.build();
        String mUrl = url+"?";//拼接get请求的url
        if (okHttpMethod == OkHttpMethod.GET) {//get方式请求
            System.out.println("url:"+mUrl);
            if (params != null && params.size() > 0) {
                for (Map.Entry<String, Object> stringObjectEntry : params.entrySet()) {
                    mUrl += stringObjectEntry.getKey() + "=" + stringObjectEntry.getValue() + "&";
                }
                request = new Request.Builder().url(mUrl).build();
            }

        } else if (okHttpMethod == OkHttpMethod.POST) {//post方式请求
            FormBody.Builder fbuilder = new FormBody.Builder();
            if (params != null && params.size() > 0) {
                for (Map.Entry<String, Object> stringObjectEntry : params.entrySet()) {

                    fbuilder.add(stringObjectEntry.getKey(), stringObjectEntry.getValue().toString());
                }
            }
            request = new Request.Builder().url(url).post(fbuilder.build()).build();
        }


        //执行异步请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {

                final String exception = e.toString();

                ((Activity) mContext).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (okcallback != null) {
                            okcallback.onFailure(exception, "请求失败");
                        }
                    }
                });


            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                //子线程下载数据
                final StringBuffer result = new StringBuffer();
                InputStream inputStream = null;
                BufferedReader bufferedReader = null;
                try {
                    if (response.isSuccessful()){
                        inputStream = response.body().byteStream();
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String content = null;
                        while ((content=bufferedReader.readLine())!=null){
                            result.append(content);
                        }
                        if (result!=null){
                            //打印请求结果
                            System.out.println("responseResult:"+result);;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
//                    bufferedReader.close();
//                    inputStream.close();
                }
                //子线程运行完毕，切换到主线程更新ui
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (okcallback != null) {
                            if (!TextUtils.isEmpty(result)){
                                okcallback.onResponse(result.toString());
                            }

                        }
                    }
                });
            }
        });

    }

    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    /**
     * 添加password
     * @param password
     * @return
     * @throws GeneralSecurityException
     */
    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType()); // 这里添加自定义的密码，默认
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

}
