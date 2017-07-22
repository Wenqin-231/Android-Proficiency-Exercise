package com.test.gank.home;

import com.test.gank.api.base.ApiManger;
import com.test.gank.api.base.HttpListener;
import com.test.gank.api.base.HttpRx;
import com.test.gank.model.GankItem;
import com.test.gank.model.Result;
import com.test.gank.presenter.BasePresenterImpl;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.List;

import rx.Observable;

/**
 * Created by wenqin on 2017/7/22.
 */

public class HomePresenterImpl extends BasePresenterImpl implements HomePresenter{

    private RxFragment mFragment;
    private HomeView mHomeView;

    public HomePresenterImpl(RxFragment homeFragment, HomeView homeView) {
        mFragment = homeFragment;
        mHomeView = homeView;
    }

    @Override
    public void requestAndroidData(int pageCount, int pageIndex) {
        mHomeView.showLoadingView();
        Observable<Result<List<GankItem>>> observable = ApiManger.get()
                .getApiservice()
                .requestAndroidData(pageCount, pageIndex);

        HttpRx.get().doHttp(mFragment, observable, new HttpListener<List<GankItem>>() {
            @Override
            public void onNext(List<GankItem> gankItemList) {
                if (mHomeView != null) {
                    mHomeView.dismissLoadingView();
                    mHomeView.onAndroidRequestSuccess(gankItemList);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (mHomeView != null) {
                    mHomeView.dismissLoadingView();
                    mHomeView.onAndroidRequestError();
                }
                super.onError(e);
            }
        });
    }

    @Override
    public void requestIOSData(int pageCount, int pageIndex) {

    }

    @Override
    public void requestWebData(int pageCount, int pageIndex) {

    }
}
