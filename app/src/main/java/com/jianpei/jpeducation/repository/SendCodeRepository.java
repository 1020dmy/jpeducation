package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.SendCodeJson;
import com.jianpei.jpeducation.contract.SendCodeContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SendCodeRepository extends BaseRepository implements SendCodeContract.Repository {

    @Override
    public Observable<BaseEntity<String>> sendCode(String phone, String type) {
        return RetrofitFactory.getInstance().API().getCode(new SendCodeJson(phone, type));
    }
}
