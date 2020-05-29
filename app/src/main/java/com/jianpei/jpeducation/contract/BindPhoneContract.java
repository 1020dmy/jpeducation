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
public interface BindPhoneContract {
    interface Repository {
        Observable<BaseEntity<UserInfoBean>> bindPhone(String uid, String phone, String code);

        Observable<BaseEntity<String>> sendCode(String phone);

    }


    interface Model {
        void bindPhone(String uid, String phone, String code);

        void sendCode(String phone);


    }
}
