package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.LauncherBean;

import io.reactivex.Observable;


/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/22
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface LauncherContract {

    interface Repository {
        Observable<BaseEntity<LauncherBean>> appSet();
    }


    interface Model {
        void appSet();

    }
}