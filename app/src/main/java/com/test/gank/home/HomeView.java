package com.test.gank.home;

import com.test.gank.model.GankItem;
import com.test.gank.presenter.BaseView;

import java.util.List;

/**
 * Created by wenqin on 2017/7/22.
 */

public interface HomeView extends BaseView{

    void onAndroidRequestSuccess(List<GankItem> data);

    void onAndroidRequestError();

}
