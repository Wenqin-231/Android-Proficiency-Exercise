package com.test.gank.search;

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
 * Created by wenqin on 2017/7/24.
 */

public class SearchPresenterImpl extends BasePresenterImpl implements SearchPresenter{

    private RxFragment mFragment;
    private SearchView mSearchView;

    public SearchPresenterImpl(RxFragment fragment, SearchView searchView) {
        mFragment = fragment;
        mSearchView = searchView;
    }

    @Override
    public void search(String searchContent, int pageCount, int pageIndex) {
        mSearchView.showLoadingView();
        Observable<Result<List<GankItem>>> observable = ApiManger.get().getApiservice().
                searchData(searchContent, pageCount, pageIndex);

        HttpRx.get().doHttp(mFragment, observable, new HttpListener<List<GankItem>>() {
            @Override
            public void onNext(List<GankItem> itemList) {
                mSearchView.dismissLoadingView();
                mSearchView.onRequestSuccess(itemList);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mSearchView.dismissLoadingView();
                mSearchView.onRequestError();
            }
        });
    }
}
