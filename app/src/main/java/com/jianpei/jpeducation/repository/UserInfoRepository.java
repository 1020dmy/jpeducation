package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.UserInfoBean;
import com.jianpei.jpeducation.bean.json.CarInfoJson;
import com.jianpei.jpeducation.bean.json.EditUserJson;
import com.jianpei.jpeducation.contract.UserInfoContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class UserInfoRepository extends BaseRepository implements UserInfoContract.Repository {

    @Override
    public Observable<BaseEntity<UserInfoBean>> userInfo() {
        return RetrofitFactory.getInstance().API().userInfo(new CarInfoJson());
    }


    @Override
    public Observable<BaseEntity<UserInfoBean>> editUser(String avatar, String user_name, String sex, String birthday) {
        return RetrofitFactory.getInstance().API().editUser(new EditUserJson(avatar, user_name, sex, birthday));
    }
}
