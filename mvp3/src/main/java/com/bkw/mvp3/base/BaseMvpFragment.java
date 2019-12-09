package com.bkw.mvp3.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

/**
 * Mvp基类Fragment（还可继承BaseFragment.需要请自定义）
 *
 * @author bkw
 */
public abstract class BaseMvpFragment<P extends BasePresenter, M extends IBaseModel> extends Fragment implements IBaseFragment {

    private static final String TAG = "BaseMvpFragment";
    /**
     * P层引用,具体有子类实现
     */
    public P mPresenter;

    public M mModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }


    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据：子类可以复写此方法
     */
    public void initData() {
        mPresenter = (P) initPresenter();
        if (mPresenter != null) {
            mModel = (M) mPresenter.getModel();
            if (mModel != null) {
                //绑定V层，M层
                mPresenter.attach(mModel, this);

                Log.d(TAG, "attach M V success.");
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.deathView();
            Log.d(TAG, "detach M V success.");
        }
    }
}
