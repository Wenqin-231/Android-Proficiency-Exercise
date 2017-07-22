package com.test.gank.home;

import com.test.gank.presenter.BasePresenter;

/**
 * Created by wenqin on 2017/7/22.
 */

public interface HomePresenter extends BasePresenter {

    void requestAndroidData(int pageCount, int pageIndex);

    void requestIOSData(int pageCount, int pageIndex);

    void requestWebData(int pageCount, int pageIndex);
}
