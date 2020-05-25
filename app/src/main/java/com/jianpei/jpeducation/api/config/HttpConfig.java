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
//    public static final String request_head = "application/x-www-form-urlencoded";


    //网络请求时间配置
    public static long HTTP_TIME = 3000l;
    //网络请求url
    public static String BASE_URL = "http://dev_api.jianpei.com.cn/api/";

}
