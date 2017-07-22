package com.test.gank.api;

import java.util.List;

import retrofit2.adapter.rxjava.Result;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by wenqin on 2017/7/22.
 */

public interface ApiService {

    @GET("data/Android/{pageCount}/{pageIndex}")
    Observable<Result<List<String>>> requestAndroidData(@Path("pageCount") int pageCount,
                                                        @Path("pageIndex") int pageIndex);

    @GET("data/iOS/{pageCount}/{pageIndex}")
    Observable<Result<List<String>>> requestIOSData(@Path("pageCount") int pageCount,
                                                        @Path("pageIndex") int pageIndex);

    @GET("data/前端/{pageCount}/{pageIndex}")
    Observable<Result<List<String>>> requestWedData(@Path("pageCount") int pageCount,
                                                        @Path("pageIndex") int pageIndex);
}
