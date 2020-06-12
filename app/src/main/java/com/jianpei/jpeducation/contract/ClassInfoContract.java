package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.UserInfoBean;
import com.jianpei.jpeducation.bean.classinfo.ClassInfoBean;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface ClassInfoContract {

    interface Repository {

        Observable<BaseEntity<ClassInfoBean>> groupInfo(String  groupId, String regimentId);

    }


    interface Model {
        void groupInfo(String  groupId,String regimentId);

    }
}