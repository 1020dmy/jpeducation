package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.LoginBean;

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
        Observable<BaseEntity<LoginBean>> sendCode(String mobile);

        Observable<BaseEntity<LoginBean>> register(String mobile, String code, String password);

    }


    interface Model {
        void sendCode(String mobile);

        void register(String mobile, String code, String password,String passwordR);

    }
}
