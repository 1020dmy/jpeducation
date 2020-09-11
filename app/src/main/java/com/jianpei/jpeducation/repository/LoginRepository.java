package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.LoginJson;
import com.jianpei.jpeducation.bean.UserInfoBean;
import com.jianpei.jpeducation.contract.LoginContract;

import io.reactivex.Observable;


/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/13
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class LoginRepository implements LoginContract.Repository {


    @Override
    public Observable<BaseEntity<UserInfoBean>> login(String mobile, String password) {
        return RetrofitFactory.getInstance().API().codeLogin(new LoginJson(mobile, password, "", 1, 0,""));
    }

    @Override
    public Observable<BaseEntity<String>> loginOut() {
        return RetrofitFactory.getInstance().API().loginOut();
    }
}
