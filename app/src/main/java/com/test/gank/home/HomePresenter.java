package com.test.gank.home;

import com.test.gank.model.GankItem;
import com.test.gank.presenter.BasePresenter;

import java.util.List;

/**
 * Created by wenqin on 2017/7/22.
 */

public interface HomePresenter extends BasePresenter {
    /**
     * 查询网络数据
     */
    void requestData(String dataStyle, int pageCount, int pageIndex);

    /**
     * 查询数据库数据
     */
    void queryDbData(String type,GankItem gankItem);

    void insertData(List<GankItem> itemList);
}
