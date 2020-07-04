package com.jianpei.jpeducation.bean.json;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class InsertCommentJson {

    private String class_id;
    private String content;

    public InsertCommentJson(String class_id, String content) {
        this.class_id = class_id;
        this.content = content;
    }


    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
