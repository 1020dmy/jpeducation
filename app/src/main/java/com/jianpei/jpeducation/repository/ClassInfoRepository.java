package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.classinfo.ClassInfoBean;
import com.jianpei.jpeducation.bean.json.GroupInfoJson;
import com.jianpei.jpeducation.contract.ClassInfoContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ClassInfoRepository extends BaseRepository implements ClassInfoContract.Repository {

    @Override
    public Observable<BaseEntity<ClassInfoBean>> groupInfo(String groupId, String regimentId) {
        return RetrofitFactory.getInstance().API().groupInfo(new GroupInfoJson(groupId, regimentId));
    }
}
