package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.DisciplinesBean;
import com.jianpei.jpeducation.contract.SelectDisciplineContract;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SelectDisciplineRepository implements SelectDisciplineContract.Repository {


    @Override
    public Observable<BaseEntity<ArrayList<DisciplinesBean>>> getCourseData() {
        return RetrofitFactory.getInstance().API().getCourseData();
    }
}
