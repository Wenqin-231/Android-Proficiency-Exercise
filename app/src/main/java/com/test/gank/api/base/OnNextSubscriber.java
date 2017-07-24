package com.test.gank.api.base;

import rx.functions.Action1;

/**
 * Created by wenqin on 2017/7/25.
 */

public class OnNextSubscriber<T> implements Action1<T> {

    private OnDoNextListener<T> mOnCallListener;

    public OnNextSubscriber(OnDoNextListener<T> onCallListener) {
        mOnCallListener = onCallListener;
    }

    @Override
    public void call(T t) {
        if (mOnCallListener != null) {
            try {
                mOnCallListener.onCall(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
