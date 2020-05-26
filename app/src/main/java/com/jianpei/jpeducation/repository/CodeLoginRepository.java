package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.LoginJson;
import com.jianpei.jpeducation.bean.SendCodeJson;
import com.jianpei.jpeducation.bean.UserInfoBean;
import com.jianpei.jpeducation.contract.CodeLoginContract;


import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/25
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CodeLoginRepository extends BaseRepository implements CodeLoginContract.Repository {

    @Override
    public Observable<BaseEntity<String>> sendCode(String phone) {
        return RetrofitFactory.getInstance().API().getCode(new SendCodeJson(phone, "login"));
    }

    @Override
    public Observable<BaseEntity<UserInfoBean>> codeLogin(String phone, String code) {
        return RetrofitFactory.getInstance().API().codeLogin(new LoginJson(phone, "", code, 2, 0,""));
    }
}
