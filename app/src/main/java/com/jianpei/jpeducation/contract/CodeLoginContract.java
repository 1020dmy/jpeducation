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
public interface CodeLoginContract {

    interface Repository {
        Observable<BaseEntity<String>> sendCode(String phone);

        Observable<BaseEntity<String>> codeLogin(String phone, String code);

    }


    interface Model {
        void codeLogin(String phone, String code);

        void sendCode(String phone);

    }
}
