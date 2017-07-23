package com.test.gank.model;

/**
 * Created by wenqin on 2017/7/23.
 */

public class LoadMoreBean {

    public static final int STATUS_DEFAULT = 0;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_END = 2;

    private int loadMoreStatus = STATUS_DEFAULT;

    public int getLoadMoreStatus() {
        return loadMoreStatus;
    }

    public void setLoadMoreStatus(int loadMoreStatus) {
        this.loadMoreStatus = loadMoreStatus;
    }
}
