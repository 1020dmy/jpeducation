package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.VersionDetectBean;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/10
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface VersionDetectContract {
    interface Repository {
        Observable<BaseEntity<VersionDetectBean>> versionDetect();
    }

    interface Model {
        void versionDetect();

    }

}
