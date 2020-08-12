package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.json.FeedbackJson;
import com.jianpei.jpeducation.contract.FeedbackContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class FeedbackRepository extends BaseRepository implements FeedbackContract.Repository {

    @Override
    public Observable<BaseEntity<String>> feedback(String images, String content, String phone,String type) {
        return RetrofitFactory.getInstance().API().feedback(new FeedbackJson(images, content, phone,type));
    }
}
