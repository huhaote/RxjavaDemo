package com.huht.rxjavademo.Service;

import com.huht.rxjavademo.bean.MovieEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by huht on 2016/9/14.
 */
public interface  MovieService {
    @GET("top250")
    Observable<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);
}
