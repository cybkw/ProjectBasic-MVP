package com.bkw.mvp2.base.persenter;

import android.os.Bundle;
import android.util.Log;

import com.bkw.mvp2.base.RxManager;
import com.bkw.mvp2.base.view.BaseMvpView;
import com.google.gson.Gson;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * @author 刘镓旗
 * @date 2017/11/17
 * @description 所有Presenter的基类，并不强制实现这些方法，有需要在重写
 */
public class BaseMvpPresenter<V extends BaseMvpView> {

    protected RxManager mRxManager = new RxManager();
    /**
     * V层view
     */
    private V mView;

    /**
     * Presenter被创建后调用
     *
     * @param savedState 被意外销毁后重建后的Bundle
     */
    public void onCreatePersenter(Bundle savedState) {
        Log.e("perfect-mvp", "P onCreatePersenter = ");

    }


    /**
     * 绑定View
     */
    public void onAttachMvpView(V mvpView) {
        mView = mvpView;
        Log.e("perfect-mvp", "P onResume");
    }

    /**
     * 解除绑定View
     */
    public void onDetachMvpView() {
        mRxManager.unSubscribe();
        mView = null;
        Log.e("perfect-mvp", "P onDetachMvpView = ");
    }

    /**
     * Presenter被销毁时调用
     */
    public void onDestroyPersenter() {
        Log.e("perfect-mvp", "P onDestroy = ");
    }

    /**
     * 在Presenter意外销毁的时候被调用，它的调用时机和Activity、Fragment、View中的onSaveInstanceState
     * 时机相同
     *
     * @param outState
     */
    public void onSaveInstanceState(Bundle outState) {
        Log.e("perfect-mvp", "P onSaveInstanceState = ");
    }

    /**
     * 获取V层接口View
     *
     * @return 返回当前MvpView
     */
    public V getMvpView() {
        return mView;
    }

    public static RequestBody request(JSONObject object) {
        return RequestBody.create(MediaType.parse("utf-8"), object.toString());
    }

    public static RequestBody request(Object object) {
        Gson gson = new Gson();
        return RequestBody.create(MediaType.parse("UTF-8"), gson.toJson(object));
    }
}
