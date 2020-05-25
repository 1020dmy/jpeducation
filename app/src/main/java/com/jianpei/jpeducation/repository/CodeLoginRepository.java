package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.CodeLoginJson;
import com.jianpei.jpeducation.bean.SendCodeJson;
import com.jianpei.jpeducation.contract.CodeLoginContract;

import java.util.Map;

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
        return RetrofitFactory.getInstance().API().getCode(new SendCodeJson(phone));
    }

    @Override
    public Observable<BaseEntity<String>> codeLogin(String phone, String code) {
        return RetrofitFactory.getInstance().API().codeLogin(new CodeLoginJson(phone,code));
    }
}
