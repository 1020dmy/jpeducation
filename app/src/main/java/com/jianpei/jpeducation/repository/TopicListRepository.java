package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.json.TopicDataJson;
import com.jianpei.jpeducation.bean.school.TopicDataBean;
import com.jianpei.jpeducation.contract.TopicListContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class TopicListRepository extends BaseRepository implements TopicListContract.Repository {


    @Override
    public Observable<BaseEntity<TopicDataBean>> topicData(int pageIndex, int pageSize) {
        return RetrofitFactory.getInstance().API().topicData(new TopicDataJson(pageIndex, pageSize));
    }
}
