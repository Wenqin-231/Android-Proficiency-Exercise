package com.test.gank.api;

import com.test.gank.model.GankItem;
import com.test.gank.model.Result;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by wenqin on 2017/7/22.
 */

public interface ApiService {

    @GET("data/Android/{pageCount}/{pageIndex}")
    Observable<Result<List<GankItem>>> requestAndroidData(@Path("pageCount") int pageCount,
                                                          @Path("pageIndex") int pageIndex);

    @GET("data/iOS/{pageCount}/{pageIndex}")
    Observable<Result<List<GankItem>>> requestIOSData(@Path("pageCount") int pageCount,
                                                        @Path("pageIndex") int pageIndex);

    @GET("data/前端/{pageCount}/{pageIndex}")
    Observable<Result<List<GankItem>>> requestWedData(@Path("pageCount") int pageCount,
                                                        @Path("pageIndex") int pageIndex);
}
