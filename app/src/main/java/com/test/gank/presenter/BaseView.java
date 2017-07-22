package com.test.gank.presenter;

/**
 * Created by wenqin on 2017/7/22.
 */

public interface BaseView {

    void showLoadingView();

    void dismissLoadingView();

    void showError(String error);
}
