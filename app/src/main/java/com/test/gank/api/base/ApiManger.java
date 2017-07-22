package com.test.gank.api.base;

import android.util.Log;

import com.test.gank.api.ApiService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Api接口管理器
 * Created by wenqin on 2017/5/5.
 */

public class ApiManger {

    private static volatile ApiManger sApiManger;
    private static volatile ApiService sApiService;

    private HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Log.d("okhttp", message);
        }
    });

    // 单例获得参数
    public static ApiManger get() {
        if (sApiManger == null) {
            synchronized (ApiManger.class) {
                if (sApiManger == null) {
                    sApiManger = new ApiManger();
                }
            }
        }
        return sApiManger;
    }

    public static void clear() {
        if (sApiManger != null) {
            sApiManger = null;
        }
    }

    public Retrofit getRetrofit() {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(HTTPSTrustManager.allowAllSSL(), new HTTPSTrustManager())
                .addInterceptor(logging)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpURL.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    /**
     * 自定义属性的构造器
     */
    public static class Builder {

        public Builder() {

        }

        public ApiManger build() {
            return ApiManger.get();
        }
    }

    public ApiService getApiservice() {
        if (sApiService == null) {
            synchronized (ApiService.class) {
                if (sApiService == null) {
                    sApiService = getRetrofit().create(ApiService.class);
                }
            }
        }
        return sApiService;
    }
}
