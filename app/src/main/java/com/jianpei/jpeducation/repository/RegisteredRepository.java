package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.LoginBean;
import com.jianpei.jpeducation.contract.RegisteredContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/25
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class RegisteredRepository extends BaseRepository implements RegisteredContract.Repository {

    @Override
    public Observable<BaseEntity<LoginBean>> sendCode(String mobile) {
        return null;
    }

    @Override
    public Observable<BaseEntity<LoginBean>> register(String mobile, String code, String password) {
        return null;
    }
}
