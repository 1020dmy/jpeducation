package com.jianpei.jpeducation.api.config;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/7
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class HttpConfig {
    public static final String request_head = "application/json; charset=utf-8";

    //网络请求时间配置
    public static long HTTP_TIME = 3000l;
    //网络请求url
    public static String BASE_URL = "http://dev_api.jianpei.com.cn/api/";
//
//    public static String BASE_URL = "http://open_api.jianpei.com.cn/api/";
//    public static String BASE_URL = "http://api_110.jianpei.com.cn/api/";


}
