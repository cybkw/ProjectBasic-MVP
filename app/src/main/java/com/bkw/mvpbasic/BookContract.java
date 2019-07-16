package com.bkw.mvpbasic;

import com.bkw.mvp1.BasePresenter;
import com.bkw.mvp1.IBaseActivity;
import com.bkw.mvp1.IBaseModel;
import com.bkw.mvpbasic.model.bean.book.BookDetailBean;

import io.reactivex.Observable;

public interface BookContract {
    abstract static class P extends BasePresenter<IBookModel, IBookView> {
        //登录
        public abstract void getDetails(String id);

    }

    interface IBookModel extends IBaseModel {
        //发起登陆请求
        Observable<BookDetailBean> getDetails(String id);
    }

    interface IBookView extends IBaseActivity {
        //获取登录结果
        void callback(BookDetailBean bean);
    }
}
