package com.huht.rxjavademo.util;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by huht on 2016/9/14.
 */
public enum  RetrofitClient {
    INSTANCE;
    private Retrofit.Builder builder;

    RetrofitClient(){
        builder =  new Retrofit.Builder().client(OKHttpFactory.INSTANCE.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    }

    public Retrofit.Builder getBuilder(){
        return builder;
    }

    public Retrofit build(String url) {
        return builder.baseUrl(url).build();
    }
}
