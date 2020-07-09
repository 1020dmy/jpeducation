package com.jianpei.jpeducation.bean.json;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CommentListJson {
    private String type;
    private String group_id;
    private String class_id;
    private int page_index;
    private int size;

    public CommentListJson(String type, String group_id, String class_id, int page_index, int size) {
        this.type = type;
        this.group_id = group_id;
        this.class_id = class_id;
        this.page_index = page_index;
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public int getPage_index() {
        return page_index;
    }

    public void setPage_index(int page_index) {
        this.page_index = page_index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
