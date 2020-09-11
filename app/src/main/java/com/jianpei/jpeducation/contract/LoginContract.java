package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.UserInfoBean;

import io.reactivex.Observable;


/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/11
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface LoginContract {
    interface Repository {
        Observable<BaseEntity<UserInfoBean>> login(String mobile, String password);
        Observable<BaseEntity<String>> loginOut();

    }


    interface Model {
        void login(String mobile, String password);
        void loginOut();
    }

}
