package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.LoginBean;
import com.jianpei.jpeducation.bean.LoginJson;
import com.jianpei.jpeducation.contract.LoginContract;

import io.reactivex.rxjava3.core.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/13
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class LoginRepository extends BaseRepository implements LoginContract.Repository {


    @Override
    public Observable<BaseEntity<LoginBean>> login(String mobile, String password) {
        return RetrofitFactory.getInstance().API().login(new LoginJson(mobile, password));
    }
}
