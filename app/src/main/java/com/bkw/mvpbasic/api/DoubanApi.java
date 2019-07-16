package com.bkw.mvpbasic.api;

import com.bkw.mvpbasic.model.bean.book.BookDetailBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface DoubanApi {
    public final static String HOST = "Https://api.douban.com/";


    @GET("v2/book/{id}")
    Observable<BookDetailBean> getBookDetail(@Path("id") String id);
}
