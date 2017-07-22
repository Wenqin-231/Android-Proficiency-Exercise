package com.test.gank.ui.adapter;
import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;

/**
 * Created by wenqin on 2016/12/16.
 */

public abstract class MultiCommonAdapter<T> extends MultiItemTypeAdapter<T> {

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> data;
    protected LayoutInflater mInflater;

    public MultiCommonAdapter(Context context, List<T> data, final int layoutId) {
        super(context, data);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        this.data = data;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(RvViewHolder holder, T t, int position) {
                MultiCommonAdapter.this.convert(holder,t,position);
            }
        });
    }

    protected abstract void convert(RvViewHolder holder, T t, int position);
}
