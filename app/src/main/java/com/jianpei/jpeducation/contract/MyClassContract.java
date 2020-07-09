package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.mclass.ClassDataBean;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface MyClassContract {

    interface Repository {
        Observable<BaseEntity<ClassDataBean>> classData(int type,int pageIndex ,int pageSize);
    }

    interface Model {
        void classData(int type,int pageIndex ,int pageSize);
    }
}
