package com.jianpei.jpeducation.api.interceptor;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.jianpei.jpeducation.api.config.HttpConfig;
import com.jianpei.jpeducation.bean.AppInfoBean;
import com.jianpei.jpeducation.utils.safety.Configure;
import com.jianpei.jpeducation.utils.safety.DesUtil;
import com.jianpei.jpeducation.utils.safety.SafetyUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.TreeMap;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;


/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/7
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:拦截器工具类（日志拦截）
 */
public class InterceptorUtil {
    public static String TAG = "----";
    private static String Token = "";
    public final static Charset UTF8 = Charset.forName("UTF-8");

    //日志拦截器
    public static HttpLoggingInterceptor LogInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.w(TAG, "log: " + message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);//设置打印数据的级别
    }

    //请求参数处理
    public static Interceptor parameterInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                String method = request.method();
                TreeMap<String, Object> rootMap = new TreeMap<>();
                if ("GET".equals(method)) {
                } else {
                    RequestBody requestBody = request.body();
                    Buffer buffer = new Buffer();
                    requestBody.writeTo(buffer);
                    String oldParamsJson = buffer.readUtf8();
                    System.out.println("加密前参数：" + oldParamsJson);
                    String newJsonParams = "";
                    //参数Des加密
                    String encodeparams = DesUtil.encrypt(oldParamsJson);
                    //生成32随机数
                    String random = SafetyUtil.getRandomStringByLength(32);
                    //获取当前系统时间
                    int timestamp = (int) (System.currentTimeMillis() / 1000);
                    // 使用RSA算法将商户自己随机生成的AESkey加密
                    try {
                        rootMap.put("appId", Configure.appId);
                        rootMap.put("nonce_str", random);
                        rootMap.put("param", encodeparams);
                        rootMap.put("timestamp", timestamp);
                        //获取signature
                        String signature = SafetyUtil.getSing(rootMap);
                        rootMap.put("signature", signature);

                        newJsonParams = JSON.toJSONString(rootMap);

                        System.out.println("加密后的参数：" + newJsonParams);
                        request = request.newBuilder().post(RequestBody.create(newJsonParams, MediaType.parse(HttpConfig.request_head))).build();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
                return chain.proceed(request);
            }
        };
    }

    /**
     * 动态请求头添加
     */
    public static Interceptor addHeadInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
//                Request.Builder requestBuilder = original.newBuilder()
//                        .header("product", "JP-A-MP")
//                        .header("channel", "jianpei")
//                        .header("version", "1.0.0")
//                        .header("model", "")
//                        .header("device", "")
//                        .header("package", "")
//                        .header("uid", "")
//                        .header("imei", "")
//                        .header("push_token", "")
//                        .header("os_version", "")
//                        .header("vendor", "");
                String headValue = JSON.toJSONString(AppInfoBean.appInfoBean);
                Request.Builder requestBuilder = original.newBuilder().header("info", headValue);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };

    }
}
