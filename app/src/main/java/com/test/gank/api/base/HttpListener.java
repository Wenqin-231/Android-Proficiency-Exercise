package com.test.gank.api.base;

/**
 * Created by wenqin on 2017/5/2.
 */

public abstract class HttpListener<T> {

    public abstract void onNext(T t);

    public void onError(Throwable e){

    }

    public void onCancel() {

    }

    public void onComplete() {

    }
}
