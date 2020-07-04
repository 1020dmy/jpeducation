package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface CommentClassContract {

    interface Repository {
        Observable<BaseEntity<String>> insertComment(String class_id, String content);


    }


    interface Model {

        void insertComment(String class_id, String content);

    }
}
