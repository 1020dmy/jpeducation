package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.LoginBean;
import com.jianpei.jpeducation.bean.LoginJson;

import io.reactivex.rxjava3.core.Observable;

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
        Observable<BaseEntity<LoginBean>> login(String mobile, String password);
    }


    interface Model {
        void login(String mobile, String password);

    }

}
