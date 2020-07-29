package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.UserInfoBean;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/25
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface RegisteredContract {

    interface Repository {
//        Observable<BaseEntity<String>> sendCode(String phone);

        Observable<BaseEntity<UserInfoBean>> register(String phone, String code, String password, String passwordR);

    }


    interface Model {
//        void sendCode(String phone);

        void register(String phone, String code, String password, String passwordR);

    }
}
