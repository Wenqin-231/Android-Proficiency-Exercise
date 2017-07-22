package com.test.gank.api.base;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wenqin on 2017/5/5.
 */

public class HttpRx {

    private static volatile HttpRx sHttpRx;

    public static HttpRx get() {
        if (sHttpRx == null) {
            synchronized (HttpRx.class) {
                if (sHttpRx == null) {
                    sHttpRx = new HttpRx();
                }
            }
        }
        return sHttpRx;
    }

    @SuppressWarnings("unchecked")
    public Subscription doHttp(RxAppCompatActivity appCompatActivity, Observable observable,
                               HttpListener httpListener) {

        HttpSubscriber httpSubscriber = new HttpSubscriber(httpListener);
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(appCompatActivity.bindToLifecycle())
                .map(httpSubscriber.getFunc())
                .subscribe(httpSubscriber);

        return subscription;
    }

    @SuppressWarnings("unchecked")
    public Subscription doHttp(Observable observable,
                               HttpListener httpListener) {

        HttpSubscriber httpSubscriber = new HttpSubscriber(httpListener);
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(httpSubscriber.getFunc())
                .subscribe(httpSubscriber);

        return subscription;
    }

    @SuppressWarnings("unchecked")
    public Subscription doHttp(RxFragment rxFragment , Observable observable,
                               HttpListener httpListener) {

        HttpSubscriber httpSubscriber = new HttpSubscriber(httpListener);
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxFragment.bindToLifecycle())
                .map(httpSubscriber.getFunc())
                .subscribe(httpSubscriber);

        return subscription;
    }


}
