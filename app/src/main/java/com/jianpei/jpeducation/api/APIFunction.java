package com.jianpei.jpeducation.api;


import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.config.UrlConfig;
import com.jianpei.jpeducation.bean.AccessTokenBean;
import com.jianpei.jpeducation.bean.LoginBean;
import com.jianpei.jpeducation.bean.LoginJson;
import com.jianpei.jpeducation.bean.WxUserInfoBean;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryName;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/7
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface APIFunction {


    /**
     * 登陆接口
     *
     * @param loginJson
     * @return
     */
    @POST(UrlConfig.LOGIN)
    Observable<BaseEntity<LoginBean>> login(@Body LoginJson loginJson);

    /**
     * 获取微信ACCESS_TOKEN
     *
     * @return
     */
    @GET(UrlConfig.WEIXIN_ACCESSTOKEN)
    Observable<AccessTokenBean> weixinAccessToken(@Query("appid") String appid, @Query("secret") String secret, @Query("code") String code);

    /**
     * 获取用户个人信息（UnionID 机制）
     *
     * @return
     */
    @GET(UrlConfig.WEIXIN_USERINFO)
    Observable<WxUserInfoBean> weixinUserInfo(@Query("access_token") String access_token, @Query("openid") String openid);

    /**
     * 微信登陆接口
     *
     * @param loginJson
     * @return
     */
    @POST(UrlConfig.WX_LOGIN)
    Observable<BaseEntity<String>> wxLogin();

}
