package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.VersionDetectBean;
import com.jianpei.jpeducation.contract.VersionDetectContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/10
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class VersionDetectRepository extends BaseRepository implements VersionDetectContract.Repository {
    @Override
    public Observable<BaseEntity<VersionDetectBean>> versionDetect() {
        return RetrofitFactory.getInstance().API().versionDetect();
    }
}
