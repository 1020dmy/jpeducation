package com.jianpei.jpeducation.bean.school;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/20
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ThreadFromTopicDataBean {

    private TopicInfoBean topic_info;

    private List<ThreadDataBean> data;


    public TopicInfoBean getTopic_info() {
        return topic_info;
    }

    public void setTopic_info(TopicInfoBean topic_info) {
        this.topic_info = topic_info;
    }

    public List<ThreadDataBean> getData() {
        return data;
    }

    public void setData(List<ThreadDataBean> data) {
        this.data = data;
    }
}
