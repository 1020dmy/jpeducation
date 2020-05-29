package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.DisciplinesBean;
import com.jianpei.jpeducation.bean.UserInfoBean;

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
public interface SelectDisciplineContract {

    interface Repository {
        Observable<BaseEntity<ArrayList<DisciplinesBean>>> getCourseData();
    }


    interface Model {

        void getCourseData();

    }
}
