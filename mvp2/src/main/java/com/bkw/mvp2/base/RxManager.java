package com.bkw.mvp2.base;


import com.orhanobut.logger.Logger;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 管理RxJava,配合MVP模式，页面销毁时取消订阅
 *
 * @author bkw
 * @date 2018/4/18
 */

public class RxManager {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public void register(Disposable d) {
        Logger.d("mCompositeDisposable register");
        mCompositeDisposable.add(d);
    }

    public void unSubscribe() {
        Logger.d("mCompositeDisposable unSubscribe");
        mCompositeDisposable.dispose();
    }
}
