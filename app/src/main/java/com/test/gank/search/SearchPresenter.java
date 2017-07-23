package com.test.gank.search;

import com.test.gank.presenter.BasePresenter;

/**
 * Created by wenqin on 2017/7/24.
 */

public interface SearchPresenter extends BasePresenter{
    void search(String searchContent, int pageCount, int pageIndex);
}
