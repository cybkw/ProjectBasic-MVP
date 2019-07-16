package com.bkw.mvp2.base.factory;


import com.bkw.mvp2.base.persenter.BaseMvpPresenter;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author 刘镓旗
 * @date 2017/11/17
 * @description 标注创建Presenter的注解
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface CreatePresenter {
    Class<? extends BaseMvpPresenter> value();
}
