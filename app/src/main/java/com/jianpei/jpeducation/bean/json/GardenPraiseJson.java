package com.jianpei.jpeducation.bean.json;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/17
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class GardenPraiseJson {
    private String type;
    private String thread_id;

    private String topic_id;
    private String post_id;


    public GardenPraiseJson(String type, String thread_id, String topic_id, String post_id) {
        this.type = type;
        this.thread_id = thread_id;
        this.topic_id = topic_id;
        this.post_id = post_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }
}
