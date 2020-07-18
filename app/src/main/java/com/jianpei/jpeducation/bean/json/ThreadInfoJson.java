package com.jianpei.jpeducation.bean.json;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/18
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ThreadInfoJson {

    private String thread_id;

    public ThreadInfoJson(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }
}
