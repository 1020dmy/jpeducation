package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.json.InsertGardenJson;
import com.jianpei.jpeducation.contract.PostNewsContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class PostNewsRepository extends BaseRepository implements PostNewsContract.Repository {

    @Override
    public Observable<BaseEntity<String>> insertGarden(String content, String images, String remind_id, String topic_id) {
        return RetrofitFactory.getInstance().API().insertGarden(new InsertGardenJson(content, images, remind_id, topic_id));
    }
}
