package com.bkw.mvpbasic.model.login;


import com.bkw.mvp1.BaseModel;
import com.bkw.mvp1.helper.RetrofitCreateHelper;
import com.bkw.mvp1.helper.RxHelper;
import com.bkw.mvpbasic.BookContract;
import com.bkw.mvpbasic.api.DoubanApi;
import com.bkw.mvpbasic.model.bean.book.BookDetailBean;

import io.reactivex.Observable;

public class BookModel extends BaseModel implements BookContract.IBookModel {

    @Override
    public Observable<BookDetailBean> getDetails(String id) {
        return RetrofitCreateHelper.createApi(DoubanApi.class, DoubanApi.HOST)
                .getBookDetail(id).compose(RxHelper.<BookDetailBean>rxSchedulerHelper());
    }
}
