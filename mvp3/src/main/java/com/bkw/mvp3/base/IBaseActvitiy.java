package com.bkw.mvp3.base;

public interface IBaseActvitiy extends IBaseView {

    /**
     * 显示加载框
     */
    void showProgress();

    /**
     * 隐藏加载框
     */
    void hideProgress();
}
