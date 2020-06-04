package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.LauncherBean;
import com.jianpei.jpeducation.contract.LauncherContract;

import io.reactivex.Observable;


/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/22
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class LauncherRepository implements LauncherContract.Repository {

    @Override
    public Observable<BaseEntity<LauncherBean>> appSet() {
        return RetrofitFactory.getInstance().API().appSet();
    }
}
