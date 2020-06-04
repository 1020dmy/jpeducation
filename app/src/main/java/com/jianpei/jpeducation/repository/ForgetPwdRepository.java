package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.ForgetPwdJson;
import com.jianpei.jpeducation.bean.SendCodeJson;
import com.jianpei.jpeducation.contract.ForgetPwdContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/26
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ForgetPwdRepository implements ForgetPwdContract.Repository {


    @Override
    public Observable<BaseEntity<String>> sendCode(String phone) {
        return RetrofitFactory.getInstance().API().getCode(new SendCodeJson(phone, "reset_pwd"));
    }

    @Override
    public Observable<BaseEntity<String>> changePwd(String phone, String code, String pwd, String pwdR) {
        return RetrofitFactory.getInstance().API().forgetPwd(new ForgetPwdJson(phone, pwd, code, pwdR));
    }
}
