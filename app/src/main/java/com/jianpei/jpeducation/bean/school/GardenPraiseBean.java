package com.jianpei.jpeducation.bean.school;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/18
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class GardenPraiseBean {

    private TopicBean topic_info;
    private ThreadDataBean thread_info;

    private EvaluationDataBean post_info;


    public TopicBean getTopic_info() {
        return topic_info;
    }

    public void setTopic_info(TopicBean topic_info) {
        this.topic_info = topic_info;
    }

    public ThreadDataBean getThread_info() {
        return thread_info;
    }

    public void setThread_info(ThreadDataBean thread_info) {
        this.thread_info = thread_info;
    }

    public EvaluationDataBean getPost_info() {
        return post_info;
    }

    public void setPost_info(EvaluationDataBean post_info) {
        this.post_info = post_info;
    }
}
