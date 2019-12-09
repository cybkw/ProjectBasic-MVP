package com.bkw.mvp3_sample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bkw.mvp3.base.BaseMvpActivity;
import com.bkw.mvp3.base.BasePresenter;
import com.bkw.mvp3_sample.login.ILoginView;
import com.bkw.mvp3_sample.login.LoginModel;
import com.bkw.mvp3_sample.login.LoginPresenter;

public class MainActivity extends BaseMvpActivity<LoginPresenter, LoginModel> implements ILoginView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void initView() {

    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void loginSuccess() {
        Log.d("TAG", "登录成功");
    }

    @Override
    public void loginFailed() {
        Log.d("TAG", "登录失败");
    }

    @Override
    public void showProgress() {
        Log.d("TAG", "显示加载框");
    }

    @Override
    public void hideProgress() {
        Log.d("TAG", "隐藏加载框");
    }

    @Override
    public BasePresenter initPresenter() {
        return new LoginPresenter();
    }

    public void loginClick(View view) {
        mPresenter.login("bkw");
    }
}
