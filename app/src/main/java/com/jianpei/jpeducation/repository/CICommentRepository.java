package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.CommentListBean;
import com.jianpei.jpeducation.bean.json.CommentListJson;
import com.jianpei.jpeducation.contract.CICommentContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CICommentRepository extends BaseRepository implements CICommentContract.Repository {

    @Override
    public Observable<BaseEntity<CommentListBean>> commentList(String groupId, int page_index, int size) {
        return RetrofitFactory.getInstance().API().commentList(new CommentListJson(groupId,page_index,size));
    }
}
