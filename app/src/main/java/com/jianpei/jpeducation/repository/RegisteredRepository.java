package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.LoginJson;
import com.jianpei.jpeducation.bean.SendCodeJson;
import com.jianpei.jpeducation.bean.UserInfoBean;
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
    public Observable<BaseEntity<String>> sendCode(String phone) {
        return RetrofitFactory.getInstance().API().getCode(new SendCodeJson(phone, "regist"));
    }

    @Override
    public Observable<BaseEntity<UserInfoBean>> register(String phone, String code, String password, String passwordR) {
        return RetrofitFactory.getInstance().API().codeLogin(new LoginJson(phone, password, code, 3, 0,passwordR));
    }
}
