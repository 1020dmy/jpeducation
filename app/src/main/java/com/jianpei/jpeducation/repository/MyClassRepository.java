package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.json.ClassDataJson;
import com.jianpei.jpeducation.bean.mclass.ClassDataBean;
import com.jianpei.jpeducation.contract.MyClassContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MyClassRepository extends BaseRepository implements MyClassContract.Repository {

    @Override
    public Observable<BaseEntity<ClassDataBean>> classData(int type, int pageIndex, int pageSize) {
        return RetrofitFactory.getInstance().API().classData(new ClassDataJson(type, pageIndex, pageSize));
    }
}
