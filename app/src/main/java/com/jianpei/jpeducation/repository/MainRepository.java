package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.HomeInfoJson;
import com.jianpei.jpeducation.contract.MainContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MainRepository extends BaseRepository implements MainContract.Repository {

    @Override
    public Observable<BaseEntity<String>> getHomeData(String catId) {
        return RetrofitFactory.getInstance().API().getHomeInfo(new HomeInfoJson(catId));
    }
}
