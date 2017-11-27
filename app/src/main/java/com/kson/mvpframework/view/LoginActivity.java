package com.kson.mvpframework.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.kson.mvpframework.R;
import com.kson.mvpframework.base.BaseActivity;
import com.kson.mvpframework.presenter.LoginPresenter;
import com.kson.mvpframework.utils.LoggerManager;

public class LoginActivity extends BaseActivity implements MainView{

    private EditText mMobileEt;
    private EditText mPwdEt;
    private Button loginBtn;

    private LoginPresenter presenter;
    private ProgressBar progressBar;

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void setListener() {
        loginBtn.setOnClickListener(this);

    }

    @Override
    public void Click(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                LoggerManager.getInstance(tag).v("msg");
//                try {
//                    int i = 5/0;
//                    LoggerManager.getInstance(tag).e("try:");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    LoggerManager.getInstance(tag).e("exception:"+e);
//                }finally {
//                    LoggerManager.getInstance(tag).e("finally");
//                }
//                Intent intent = new Intent(this,MainActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("","");
//                intent.putExtras(bundle);
//                startActivity(intent);
//                startActivity(MainActivity.class);
//                startActivity(MainActivity.class,bundle);
                presenter.login(mMobileEt.getText().toString(),mPwdEt.getText().toString());
                break;
        }

    }

    @Override
    public void initView(){

        mMobileEt = (EditText) findViewById(R.id.et_mobile);
        mPwdEt = (EditText) findViewById(R.id.et_pwd);
        loginBtn = (Button) findViewById(R.id.btn_login);
        progressBar = (ProgressBar) findViewById(R.id.progress);

    }


//    public void test () throws IOException{
//
//    }

    @Override
    public void initData() {
        presenter = new LoginPresenter(this,this);
        setStatus(false);
        setShowActionBar(false);
    }



    @Override
    public void showProgress() {

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void failure(String msg) {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void nameError(String msg) {

//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        showToast(msg);

    }

    @Override
    public void passError(String msg) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        showToast(msg);

    }

    @Override
    public void loginSuccess(String msg) {
        showToast(msg);
        progressBar.setVisibility(View.GONE);
        startActivity(UploadActivity.class);

    }

    @Override
    public void loginError(String msg) {
        showToast(msg);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetroy();
    }
}
