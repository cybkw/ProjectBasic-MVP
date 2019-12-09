package com.bkw.mvp3_sample.login;

import com.bkw.mvp3.base.BaseModel;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginModel extends BaseModel {

    public static LoginModel newInstance() {
        return new LoginModel();
    }

    public Observable<String> login(String uname) {
        return (Observable<String>) Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                //发起网络请求

                emitter.onNext("登录成功");
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new ObservableTransformer<String, String>() {
                    @Override
                    public ObservableSource<String> apply(Observable<String> upstream) {
                        //得到响应结果
                        return upstream;
                    }
                });
    }
}
