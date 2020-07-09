package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.json.ClassInfoJson;
import com.jianpei.jpeducation.bean.mclass.MClassInfoBean;
import com.jianpei.jpeducation.contract.ClassPlayerContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ClassPlayerRepository extends BaseRepository implements ClassPlayerContract.Repository {


    @Override
    public Observable<BaseEntity<MClassInfoBean>> classInfo(String class_id) {
        return RetrofitFactory.getInstance().API().classInfo(new ClassInfoJson(class_id));
    }
}
