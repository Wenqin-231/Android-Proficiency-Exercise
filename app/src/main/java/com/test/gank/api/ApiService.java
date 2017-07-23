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

    @GET("data/{dataStyle}/{pageCount}/{pageIndex}")
    Observable<Result<List<GankItem>>> requestData(@Path("dataStyle") String dataStyle,
                                                   @Path("pageCount") int pageCount,
                                                   @Path("pageIndex") int pageIndex);

    @GET("search/query/{searchContent}/category/all/count/{pageCount}/page/{pageIndex}")
    Observable<Result<List<GankItem>>> searchData(@Path("searchContent") String searchContent,
                                                  @Path("pageCount") int pageCount,
                                                  @Path("pageIndex") int pageIndex);


}
