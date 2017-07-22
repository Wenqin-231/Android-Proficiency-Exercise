package com.test.gank.presenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by wenqin on 2017/7/22.
 */

public class BasePresenterImpl implements BasePresenter{

    private CompositeSubscription mCompositeSubscription;

    protected void addSubsciption(Subscription s) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(s);
    }

    @Override
    public void unsubscrible() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void onDestroy() {

    }
}
