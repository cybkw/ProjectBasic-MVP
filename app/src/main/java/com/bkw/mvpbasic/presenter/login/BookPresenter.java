package com.bkw.mvpbasic.presenter.login;

import com.bkw.mvpbasic.BookContract;
import com.bkw.mvpbasic.model.bean.book.BookDetailBean;
import com.bkw.mvpbasic.model.login.BookModel;

import io.reactivex.functions.Consumer;

public class BookPresenter extends BookContract.P {
    @Override
    public void getDetails(String id) {
        mRxManager.register(mIModel.getDetails(id).subscribe(new Consumer<BookDetailBean>() {
            @Override
            public void accept(BookDetailBean bean) throws Exception {
                mIView.callback(bean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mIView.callback(null);
            }
        }));
    }

    @Override
    public BookModel getModel() {
        return new BookModel();
    }

    @Override
    public void onStart() {

    }
}
