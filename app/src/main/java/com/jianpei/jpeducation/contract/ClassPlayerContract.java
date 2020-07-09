package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.mclass.MClassInfoBean;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface ClassPlayerContract {
    interface Repository {
        Observable<BaseEntity<MClassInfoBean>> classInfo(String class_id);

    }


    interface Model {
        void classInfo(String class_id);
    }
}
