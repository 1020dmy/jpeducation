package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.BindPhoneJson;
import com.jianpei.jpeducation.bean.SendCodeJson;
import com.jianpei.jpeducation.bean.UserInfoBean;
import com.jianpei.jpeducation.contract.BindPhoneContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class BindPhoneRepository implements BindPhoneContract.Repository {

    @Override
    public Observable<BaseEntity<UserInfoBean>> bindPhone(String uid, String phone, String code) {
        return RetrofitFactory.getInstance().API().bingPhone(new BindPhoneJson(uid,phone,code));
    }

//    @Override
//    public Observable<BaseEntity<String>> sendCode(String phone) {
//        return RetrofitFactory.getInstance().API().getCode(new SendCodeJson(phone, "other"));
//    }
}
