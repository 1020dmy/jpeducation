package com.jianpei.jpeducation.api.customgson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/11
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return null;
    }
}
