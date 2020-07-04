package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.json.InsertCommentJson;
import com.jianpei.jpeducation.contract.CommentClassContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CommentClassRepository extends BaseRepository implements CommentClassContract.Repository {

    @Override
    public Observable<BaseEntity<String>> insertComment(String class_id, String content) {
        return RetrofitFactory.getInstance().API().insertComment(new InsertCommentJson(class_id, content));
    }
}
