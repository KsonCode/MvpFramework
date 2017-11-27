package com.kson.mvpframework;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.kson.mvpframework.databinding.ActivityMainBinding;
import com.kson.mvpframework.entity.UserEntity;
import com.kson.mvpframework.view.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        UserEntity user = new UserEntity();
        user.msg = "kson";
        activityMainBinding.setU(user);//网络请求的数据转换成的对象，设置给data

        AsyncTask asyncTask = null;
        asyncTask.execute("");



//        tv = (TextView) findViewById(R.id.tv);
//        tv.setText("test");
    }

    public void login(View v){
        startActivity(new Intent(this,LoginActivity.class));
    }
}
