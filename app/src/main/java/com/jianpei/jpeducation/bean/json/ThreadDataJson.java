package com.jianpei.jpeducation.bean.json;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ThreadDataJson {

    private String start_id;

    private String end_id;

    private String follow;

    private String topic_id;

    private String is_hot;

    public ThreadDataJson(String start_id, String end_id, String follow, String topic_id, String is_hot) {
        this.start_id = start_id;
        this.end_id = end_id;
        this.follow = follow;
        this.topic_id = topic_id;
        this.is_hot = is_hot;
    }

    public ThreadDataJson(String start_id, String end_id, String follow) {
        this.start_id = start_id;
        this.end_id = end_id;
        this.follow = follow;
    }

    public String getStart_id() {
        return start_id;
    }

    public void setStart_id(String start_id) {
        this.start_id = start_id;
    }

    public String getEnd_id() {
        return end_id;
    }

    public void setEnd_id(String end_id) {
        this.end_id = end_id;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(String is_hot) {
        this.is_hot = is_hot;
    }
}
