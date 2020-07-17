package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.school.AttentionBean;
import com.jianpei.jpeducation.bean.school.AttentionDataBean;
import com.jianpei.jpeducation.bean.school.TopicBean;

import java.util.ArrayList;
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
public interface PostNewsContract {

    interface Repository {


        Observable<BaseEntity<String>> insertGarden(String content, String images, String remind_id, String topic_id);

    }


    interface Model {


        void insertGarden(String content, List<String> images, ArrayList<AttentionBean> selectAttentionBeans, ArrayList<TopicBean> selectTopicBean);


    }
}
