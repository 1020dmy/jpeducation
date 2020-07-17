package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.school.TopicDataBean;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface TopicListContract {


    interface Repository {


        Observable<BaseEntity<TopicDataBean>> topicData(int pageIndex, int pageSize);

    }


    interface Model {


        void topicData(int pageIndex, int pageSize);


    }
}
