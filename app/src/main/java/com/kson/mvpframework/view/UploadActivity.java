package com.kson.mvpframework.view;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.kson.mvpframework.R;
import com.kson.mvpframework.common.Api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload2);
    }

    public void upload(View view){

//        http协议请求报文信息分为哪几部分：首行、多个请求头、头部结束标识位（空行），请求体（post）
//响应报文信息分：响应行（首行），多个响应头，空行、响应体
        //post请求方式一：application/x-www-form-urlencoded（只能上传string类型的参数，不能上传文件）
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("uid","71");

        Request request = new Request.Builder().post(builder.build()).url(Api.GETUSERINFO).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){

                    System.out.println(response.body().string());
                }
            }
        });

        //ssl/tls协议，

        try {
           Response response =  okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


        File file = null;
        //sd卡已挂载，并可用
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/kson/");
            if (!dir.exists()){
                dir.mkdirs();
            }
            file = new File(dir,"hi.jpg");//第一种获取文件写法
//            File file1  = new File(dir.getPath()+"t.jpg");
            if (!file.exists()){
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Map<String,Object> params = new HashMap<>();
        params.put("uid","71");
        if (file!=null){

            params.put("file",file);
            System.out.println("filepath:"+file.getPath());
        }
        upload(params);








    }

    /**
     * 上传文件（各种格式）
     * @param params
     */
    private void upload(Map<String,Object> params) {

        //post请求方式二：multipart/form-data(不仅能够上传string类型的参数，还可以上传文件（流的形式，file）)
        OkHttpClient okHttpClient1 = new OkHttpClient();


        MultipartBody.Builder builder1 = new MultipartBody.Builder();
        builder1.setType(MultipartBody.FORM);
        for (Map.Entry<String, Object> stringObjectEntry : params.entrySet()) {
            String key = stringObjectEntry.getKey();
            Object value = stringObjectEntry.getValue();
            if (value instanceof File){//如果请求的值是文件
                File file = (File) value;
                builder1.addFormDataPart(key,((File) value).getName(), RequestBody.create(MediaType.parse("application/octet-stream"),file));
            }else{//如果请求的值是string类型
                builder1.addFormDataPart(key,value.toString());
            }
        }


        Request request1 = new Request.Builder().post(builder1.build()).url(Api.UPLOAD).build();

        okHttpClient1.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("filesuccess："+response.body().string());
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = new FileOutputStream(new File("全路径"));
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length=inputStream.read(buffer))!=-1){
                    fileOutputStream.write(buffer,0,length);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }

            }
        });


    }

    /**
     * 设置okhttp超时时间
     */
    private  void setTimeout(){

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(1, TimeUnit.DAYS);//连接超时时间
        builder.readTimeout(1,TimeUnit.HOURS);
        builder.writeTimeout(1,TimeUnit.HOURS);

        OkHttpClient client = builder.build();




    }
}
