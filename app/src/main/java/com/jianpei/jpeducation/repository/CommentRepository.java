package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.CommentListBean;
import com.jianpei.jpeducation.bean.json.CommentListJson;
import com.jianpei.jpeducation.bean.json.InsertCommentJson;
import com.jianpei.jpeducation.contract.CommentContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CommentRepository extends BaseRepository implements CommentContract.Repository {


    @Override
    public Observable<BaseEntity<String>> insertComment(String class_id, String content) {
        return RetrofitFactory.getInstance().API().insertComment(new InsertCommentJson(class_id, content));
    }

    @Override
    public Observable<BaseEntity<CommentListBean>> commentList(String type, String groupId, String class_id, int page_index, int size) {
        return RetrofitFactory.getInstance().API().commentList(new CommentListJson(type, groupId, class_id, page_index, size));
    }
}
