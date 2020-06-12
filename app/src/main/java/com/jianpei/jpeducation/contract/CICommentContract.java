package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.CommentListBean;
import com.jianpei.jpeducation.bean.classinfo.DirectoryProfessionBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface  CICommentContract {

    interface Repository {

        Observable<BaseEntity<CommentListBean>> commentList(String groupId, int page_index, int size);

    }


    interface Model {
        void commentList(String groupId, int page_index, int size);

    }
}
