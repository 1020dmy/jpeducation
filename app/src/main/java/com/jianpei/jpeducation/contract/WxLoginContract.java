package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.UserInfoBean;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface WxLoginContract {
    interface Repository {
        Observable<BaseEntity<UserInfoBean>> wxLogin(String refreshToken, String expiration, String screen_name, String access_token, String city, String gender, String openid, String province, String iconurl);
    }


    interface Model {
        void wxLogin(String refreshToken, String expiration, String screen_name, String access_token, String city, String gender, String openid, String province, String iconurl);

    }
}
