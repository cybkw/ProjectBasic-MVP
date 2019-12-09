package com.bkw.mvp3.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 管理RxJava的订阅与注销
 *
 * @author bkw
 */
public class RxMannager {

    public CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void subscriber(Disposable d) {
        compositeDisposable.add(d);
    }

    public void unSubscriber() {
        compositeDisposable.dispose();
    }
}
