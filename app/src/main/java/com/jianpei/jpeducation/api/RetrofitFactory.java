package com.jianpei.jpeducation.api;

import com.jianpei.jpeducation.api.config.HttpConfig;
import com.jianpei.jpeducation.api.customgson.Retrofit2ConverterFactory;
import com.jianpei.jpeducation.api.interceptor.InterceptorUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/7
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class RetrofitFactory {

    protected static RetrofitFactory retrofitFactory;
    protected static APIFunction apiFunction;


    private RetrofitFactory() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .addInterceptor(InterceptorUtil.LogInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URL)
                .addConverterFactory(new Retrofit2ConverterFactory())//gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//rxjava转换器
                .client(okHttpClient)
                .build();


        apiFunction = retrofit.create(APIFunction.class);

    }

    public static RetrofitFactory getInstance() {

        if (retrofitFactory == null) {
            synchronized (RetrofitFactory.class) {
                if (retrofitFactory == null) {
                    retrofitFactory = new RetrofitFactory();
                }
            }
        }
        return retrofitFactory;
    }

    public APIFunction API() {
        return apiFunction;
    }

}
