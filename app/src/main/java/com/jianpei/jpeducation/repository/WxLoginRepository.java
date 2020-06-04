package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.UserInfoBean;
import com.jianpei.jpeducation.bean.WxLoginJson;
import com.jianpei.jpeducation.contract.WxLoginContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class WxLoginRepository implements WxLoginContract.Repository {

    @Override
    public Observable<BaseEntity<UserInfoBean>> wxLogin(String refreshToken, String expiration, String screen_name, String access_token, String city, String gender, String openid, String province, String iconurl) {
        return RetrofitFactory.getInstance().API().wxLogin(new WxLoginJson("app",refreshToken, expiration, screen_name, access_token, city, gender, openid, province, iconurl));
    }
}
