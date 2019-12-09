package com.bkw.mvp3_sample.login;

import android.util.Log;

import com.bkw.mvp3.base.BasePresenter;

import io.reactivex.functions.Consumer;

public class LoginPresenter extends BasePresenter<LoginModel, ILoginView> {


    private static final String TAG = "LoginPresenter";

    /**
     * 登录
     */
    public void login(String uname) {
        if (mView==null || mModel==null){
            Log.d(TAG,"V 或 M层为空");
            return;
        }
        mView.showProgress();
        rxMannager.subscriber(getModel().login(uname)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mView.hideProgress();
                        mView.loginSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.loginFailed();
                    }
                }));

    }


    /**
     * 登出
     */
    public void logout() {

    }

    @Override
    public LoginModel getModel() {
        return new LoginModel();
    }

}
