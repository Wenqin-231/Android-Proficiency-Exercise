package com.test.gank.home;

import com.test.gank.api.base.ApiManger;
import com.test.gank.api.base.HttpListener;
import com.test.gank.api.base.HttpRx;
import com.test.gank.db.DaoSession;
import com.test.gank.db.GankItemDao;
import com.test.gank.model.GankItem;
import com.test.gank.model.Result;
import com.test.gank.presenter.BasePresenterImpl;
import com.trello.rxlifecycle.components.support.RxFragment;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.rx.RxDao;
import org.greenrobot.greendao.rx.RxQuery;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wenqin on 2017/7/22.
 */

public class HomePresenterImpl extends BasePresenterImpl implements HomePresenter {

    private RxFragment mFragment;
    private HomeView mHomeView;
    private RxDao<GankItem, Long> mRxDao;
    private QueryBuilder<GankItem> mQueryBuilder;

    public HomePresenterImpl(RxFragment homeFragment, HomeView homeView, DaoSession daoSession) {
        mFragment = homeFragment;
        mHomeView = homeView;
        mRxDao = daoSession.getGankItemDao().rx();
        mQueryBuilder = daoSession.getGankItemDao().queryBuilder();
    }

    @Override
    public void requestData(String dataStyle, int pageCount, int pageIndex) {
        mHomeView.showLoadingView();

        Observable<Result<List<GankItem>>> observable = ApiManger.get()
                .getApiservice()
                .requestData(dataStyle, pageCount, pageIndex);

        HttpRx.get().doHttp(mFragment, observable,
                new HttpListener<List<GankItem>>() {
                    @Override
                    public void onNext(List<GankItem> gankItemList) {
                        if (mHomeView != null) {
                            mHomeView.dismissLoadingView();
                            mHomeView.onRequestSuccess(gankItemList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mHomeView != null) {
                            mHomeView.dismissLoadingView();
                            mHomeView.onRequestError();
                        }
                        super.onError(e);
                    }
                });
    }

    @Override
    public void queryDbData(String type, GankItem gankItem) {

        RxQuery<GankItem> rxQuery;
        // 查找的数据以发布时间排序
        if (gankItem == null) { // 如果没有数据，也就是第一次请求，那么查找全部
            rxQuery = mQueryBuilder.where(GankItemDao.Properties.Type.eq(type))
                    .orderAsc(GankItemDao.Properties.PublishedAt).rx();
        } else { // 如果是分页请求出错了，用最后一条数据匹配publish时间
            rxQuery = mQueryBuilder.where(GankItemDao.Properties.Type.eq(type),
                    GankItemDao.Properties.PublishedAt.le(gankItem.getPublishedAt()))
                    .orderAsc(GankItemDao.Properties.PublishedAt).rx();
        }

        // 在IO线程执行数据库操作
        rxQuery.list()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Action1<List<GankItem>>() {
                    @Override
                    public void call(List<GankItem> itemList) {
                        mHomeView.onQueryData(itemList);
                    }
                });
    }

    @Override
    public void insertData(List<GankItem> gankItemList) {
        for (int i = 0; i < gankItemList.size(); i++) {
            // 在IO线程执行数据库操作
            mRxDao.insertOrReplace(gankItemList.get(i))
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }
    }
}
