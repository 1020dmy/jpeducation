package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.elective.GroupHomeBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface FeedbackContract {

    interface Repository {
        Observable<BaseEntity<String>> feedback(String images, String content, String phone,String type);
    }


    interface Model {
        void feedback(List<String> images, String content, String phone, String type);
    }
}
