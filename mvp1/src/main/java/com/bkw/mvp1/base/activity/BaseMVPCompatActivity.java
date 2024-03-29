package com.bkw.mvp1.base.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.bkw.mvp1.BasePresenter;
import com.bkw.mvp1.IBaseActivity;
import com.bkw.mvp1.IBaseModel;
import com.bkw.mvp1.utils.ToastUtils;

/**
 * Created by Horrarndoo on 2017/4/6.
 * <p>
 * Mvp Activity基类
 */
public abstract class BaseMVPCompatActivity<P extends BasePresenter, M extends IBaseModel> extends
        BaseCompatActivity implements IBaseActivity {
    /**
     * presenter 具体的presenter由子类确定
     */
    protected P mPresenter;

    /**
     * model 具体的model由子类确定
     */
    private M mIMode;

    /**
     * 初始化数据
     * <p>
     * 子类可以复写此方法初始化子类数据
     */
    @Override
    protected void initData() {
        super.initData();
        mPresenter = (P) initPresenter();
        if (mPresenter != null) {
            mIMode = (M) mPresenter.getModel();
            if (mIMode != null) {
                mPresenter.attachMV(mIMode, this);
            }
            //Logger.d("attach M V success.");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachMV();
            //Logger.d("detach M V success.");
        }
    }

    @Override
    public void showWaitDialog(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void hideWaitDialog() {
        hideProgressDialog();
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void startNewActivity(@NonNull Class<?> clz) {
        startActivity(clz);
    }

    @Override
    public void startNewActivity(@NonNull Class<?> clz, Bundle bundle) {
        startActivity(clz, bundle);
    }

    @Override
    public void startNewActivityForResult(@NonNull Class<?> clz, Bundle bundle, int requestCode) {
        startActivityForResult(clz, bundle, requestCode);
    }

    @Override
    public void hideKeybord() {
        hiddenKeyboard();
    }

    @Override
    public void back() {
        super.onBackPressedSupport();
    }
}
