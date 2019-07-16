package com.bkw.mvpbasic.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.bkw.mvp1.BasePresenter;
import com.bkw.mvp1.base.activity.BaseMVPCompatActivity;
import com.bkw.mvpbasic.BookContract;
import com.bkw.mvpbasic.R;
import com.bkw.mvpbasic.model.bean.book.BookDetailBean;
import com.bkw.mvpbasic.presenter.login.BookPresenter;

/**
 * @author bkw
 */
public class BookActivity extends BaseMVPCompatActivity<BookContract.P, BookContract.IBookModel> implements BookContract.IBookView {

    @Override
    protected void initView(Bundle savedInstanceState) {
        mPresenter.getDetails("id");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void callback(BookDetailBean bean) {
        //显示请求结果
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return new BookPresenter();
    }
}
