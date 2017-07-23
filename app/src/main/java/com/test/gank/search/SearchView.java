package com.test.gank.search;

import com.test.gank.model.GankItem;
import com.test.gank.presenter.BaseView;

import java.util.List;

/**
 * Created by wenqin on 2017/7/24.
 */

public interface SearchView extends BaseView{

    void onRequestSuccess(List<GankItem> data);

    void onRequestError();
}
