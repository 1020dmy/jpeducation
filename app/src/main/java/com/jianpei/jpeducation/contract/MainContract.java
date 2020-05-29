package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.UserInfoBean;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface MainContract {


    interface Repository {
        Observable<BaseEntity<String>> getHomeData(String catId);
    }


    interface Model {
        void getHomeData(String catId);
    }
}
