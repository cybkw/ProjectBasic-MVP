package com.bkw.mvp3.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * * Mvp基类Activity（还可继承BaseActivity.需要请自定义）
 *
 * @author bkw
 */
public abstract class BaseMvpActivity<P extends BasePresenter, M extends BaseModel> extends Activity implements IBaseActvitiy {

    public static final String TAG = "BaseMvpActivity";
    /**
     * 具体的P层由子类确定
     */
    public P mPresenter;

    /**
     * 具体M层由子类确定
     */
    public M mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    public void initData() {
        mPresenter = (P) initPresenter();
        if (mPresenter != null) {
            mModel = (M) mPresenter.getModel();
            //绑定V层，M层
            if (mModel != null) {
                mPresenter.attach(mModel, this);
            }
        }
        Log.d(TAG, "attach View");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出页面解绑V层引用
        if (mPresenter != null) {
            mPresenter.deathView();
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
