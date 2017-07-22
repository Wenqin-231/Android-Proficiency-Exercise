package com.test.gank.ui.adapter;

/**
 * Created by wenqin on 2016/12/15.
 */

public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(RvViewHolder holder, T t, int position);
}
