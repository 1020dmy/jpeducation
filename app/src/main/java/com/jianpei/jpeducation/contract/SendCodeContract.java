package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.UserInfoBean;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface SendCodeContract {

    interface Repository {
//        Observable<BaseEntity<UserInfoBean>> bindPhone(String uid, String phone, String code);

        Observable<BaseEntity<String>> sendCode(String phone, String type);

    }


    interface Model {
//        void bindPhone(String uid, String phone, String code);

        void sendCode(String phone, String type);


    }
}
