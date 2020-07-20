package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.HomeInfoJson;
import com.jianpei.jpeducation.bean.elective.GroupHomeBean;
import com.jianpei.jpeducation.contract.ElectiveContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/20
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ElectiveRepository extends BaseRepository implements ElectiveContract.Repository {

    @Override
    public Observable<BaseEntity<GroupHomeBean>> groupHome(String cat_id) {
        return RetrofitFactory.getInstance().API().groupHome(new HomeInfoJson(cat_id));
    }
}
