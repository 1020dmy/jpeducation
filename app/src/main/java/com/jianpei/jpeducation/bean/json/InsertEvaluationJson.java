package com.jianpei.jpeducation.bean.json;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/17
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class InsertEvaluationJson {

    private String thread_id;
    private String content;
    private String post_id;
    private String user_id_at;


    public InsertEvaluationJson(String thread_id, String content, String post_id, String user_id_at) {
        this.thread_id = thread_id;
        this.content = content;
        this.post_id = post_id;
        this.user_id_at = user_id_at;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUser_id_at() {
        return user_id_at;
    }

    public void setUser_id_at(String user_id_at) {
        this.user_id_at = user_id_at;
    }
}
