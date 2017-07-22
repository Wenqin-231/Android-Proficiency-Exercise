package com.test.gank.api.base;


import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.test.gank.model.Result;
import com.test.gank.utils.App;
import com.test.gank.utils.NetworkUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by wenqin on 2017/5/2.
 */

public class HttpSubscriber<T> extends Subscriber<T> {

    private HttpListener mHttpListener;

    public HttpSubscriber(HttpListener httpListener) {
        mHttpListener = httpListener;
    }


    @Override
    public void onCompleted() {
        if (mHttpListener != null) {
            mHttpListener.onComplete();
        }
    }

    @Override
    public void onError(Throwable e) {

        if (NetworkUtil.isHttpStatusCode(e, 400)) {
            ResponseBody body = ((HttpException) e).response().errorBody();
            try {
                Result result = new Gson().fromJson(body.string(), Result.class);
                if (result != null) {
                    ToastError();
                }
            } catch (IOException ignored) {

            } catch (Throwable e1) {
                e1.printStackTrace();
            } finally {
                if (body != null) body.close();
            }
        } else {
            Toast.makeText(App.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        if (mHttpListener != null) {
            mHttpListener.onError(e);
        }
    }

    private void ToastError() {
        // 这里本来是可以根据返回的code处理相关的错误提示，但是Gank Api没有code错误处理
        Toast.makeText(App.getContext(), "网络访问异常", Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onNext(T t) {

        if (mHttpListener != null) {
            mHttpListener.onNext(t);
        }
    }

    // 转换器
    private class Func implements Func1<Result<T>, T> {

        @Override
        public T call(Result<T> result) {
            if (result == null) {
                Log.e("Http", "Func1's result is null");
                return null;
            }
            if (result.isError()) {
                ToastError();
            } else {
                Log.d("Http", "success");
            }
            return result.getData();
        }
    }

    public Func getFunc() {
        return new Func();
    }
}
