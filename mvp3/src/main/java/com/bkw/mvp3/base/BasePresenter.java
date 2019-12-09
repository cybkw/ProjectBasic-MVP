package com.bkw.mvp3.base;

/**
 * P层
 *
 * @param <M> M层引用
 * @param <V> V层引用
 */
public abstract class BasePresenter<M, V> {

    public M mModel;
    public V mView;
    public RxMannager rxMannager = new RxMannager();

    /**
     * 返回M层的引用
     */
    public abstract M getModel();


    /**
     * 绑定V层，M层
     */
    public void attach(M m, V v) {
        this.mModel = m;
        this.mView = v;
    }


    /**
     * 解除绑定
     */
    public void deathView() {
        rxMannager.unSubscriber();
        mModel = null;
        mView = null;
    }
}
