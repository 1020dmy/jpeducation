package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.school.ThreadDataBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface SchoolContract {


    interface Repository {
        //帖子列表
        Observable<BaseEntity<List<ThreadDataBean>>> threadData(String start_id, String end_id, String follow);

        //关注/取消关注
        Observable<BaseEntity<ThreadDataBean>> attention(String attention_id, String topic_id, String thread_id, List<ThreadDataBean> mThreadDataBeans);

        //点赞/取消点赞
        Observable<BaseEntity<ThreadDataBean>> gardenPraise(String type, String thread_id, String topic_id, String post_id);


    }


    interface Model {


        void threadData(String start_id, String end_id, String follow);

        void attention(String attention_id, String topic_id, String thread_id, List<ThreadDataBean> mThreadDataBeans);

        void gardenPraise(String type, String thread_id, String topic_id, String post_id);
    }
}
