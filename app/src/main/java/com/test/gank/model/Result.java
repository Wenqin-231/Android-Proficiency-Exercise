package com.test.gank.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wenqin on 2017/7/22.
 */

public class Result<T> {

    private boolean error;
    @SerializedName("results")
    private T data;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
