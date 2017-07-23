package com.test.gank.ui.adapter.base;

import android.support.annotation.IdRes;

/**
 * Created by wenqin on 2017/7/23.
 */

public abstract class LoadMoreView {
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_END = 2;

    private int mLoadMoreStatus = STATUS_LOADING;
    private boolean mLoadMoreEndGone = false;

    public void setLoadMoreStatus(int loadMoreStatus) {
        mLoadMoreStatus = loadMoreStatus;
    }

    public int getLoadMoreStatus() {
        return mLoadMoreStatus;
    }

    public void convert(RvViewHolder holder) {
        switch (mLoadMoreStatus) {
            case STATUS_LOADING:
                visibleLoading(holder, true);
                visibleLoadingEnd(holder, false);
                break;
            case STATUS_END:
                visibleLoading(holder, false);
                visibleLoadingEnd(holder, true);
                break;
        }
    }

    private void visibleLoadingEnd(RvViewHolder holder, boolean visible) {
        holder.setVisible(getLoadingViewId(), visible);
    }

    private void visibleLoading(RvViewHolder holder, boolean visible) {
        holder.setVisible(getLoadEndViewId(), visible);
    }

    public void setLoadMoreEndGone(boolean loadMoreEndGone) {
        mLoadMoreEndGone = loadMoreEndGone;
    }

    public boolean isLoadMoreEndGone() {
        if (getLoadEndViewId() == 0) {
            return true;
        }
        return mLoadMoreEndGone;
    }

    @Deprecated
    public boolean isLoadEndGone() {
        return mLoadMoreEndGone;
    }

    protected abstract @IdRes int getLoadingViewId();

    protected abstract @IdRes int getLoadEndViewId();
}
