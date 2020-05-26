package com.jianpei.jpeducation.api;


import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.config.UrlConfig;
import com.jianpei.jpeducation.bean.ForgetPwdJson;
import com.jianpei.jpeducation.bean.LauncherBean;
import com.jianpei.jpeducation.bean.LoginJson;
import com.jianpei.jpeducation.bean.SendCodeJson;
import com.jianpei.jpeducation.bean.UserInfoBean;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
     * 获取启动图和引导图
     *
     * @return
     */
    @POST(UrlConfig.appset)
    Observable<BaseEntity<LauncherBean>> appSet();

    /**
     * 账号登陆接口
     *
     * @param loginJson
     * @return
     */
//    @POST(UrlConfig.LOGIN)
//    Observable<BaseEntity<LoginBean>> login(@Body LoginJson loginJson);

    /**
     * 获取验证码接口
     */
    @POST(UrlConfig.sendCode)
    Observable<BaseEntity<String>> getCode(@Body SendCodeJson sendCodeJson);


    /**
     * 用户验证码登陆
     */
    @POST(UrlConfig.codeLogin)
    Observable<BaseEntity<UserInfoBean>> codeLogin(@Body LoginJson loginJson);
    /**
     * 忘记密码
     */
    @POST(UrlConfig.forgetPwd)
    Observable<BaseEntity<String>> forgetPwd(@Body ForgetPwdJson forgetPwdJson);

    /**
     * 获取微信ACCESS_TOKEN
     *
     * @return
     */
//    @GET(UrlConfig.WEIXIN_ACCESSTOKEN)
//    Observable<AccessTokenBean> weixinAccessToken(@Query("appid") String appid, @Query("secret") String secret, @Query("code") String code);

    /**
     * 获取用户个人信息（UnionID 机制）
     *
     * @return
     */
//    @GET(UrlConfig.WEIXIN_USERINFO)
//    Observable<WxUserInfoBean> weixinUserInfo(@Query("access_token") String access_token, @Query("openid") String openid);

    /**
     * 微信登陆接口
     *
     * @param loginJson
     * @return
     */
//    @POST(UrlConfig.WX_LOGIN)
//    Observable<BaseEntity<String>> wxLogin();

}
