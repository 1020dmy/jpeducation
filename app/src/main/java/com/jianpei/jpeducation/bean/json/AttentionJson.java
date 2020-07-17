package com.jianpei.jpeducation.bean.json;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/17
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class AttentionJson {

    private String attention_id;
    private String topic_id;

    private String thread_id;


    public AttentionJson(String attention_id, String topic_id,String thread_id) {
        this.attention_id = attention_id;
        this.topic_id = topic_id;
        this.thread_id=thread_id;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getAttention_id() {
        return attention_id;
    }

    public void setAttention_id(String attention_id) {
        this.attention_id = attention_id;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }
}
