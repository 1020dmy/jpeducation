package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.CommentListBean;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface CommentContract {


    interface Repository {
        Observable<BaseEntity<String>> insertComment(String class_id, String content);

        Observable<BaseEntity<CommentListBean>> commentList(String type,String groupId,String class_id, int page_index, int size);
    }


    interface Model {

        void insertComment(String class_id, String content);

        void commentList(String type,String groupId,String class_id, int page_index, int size);

    }
}
