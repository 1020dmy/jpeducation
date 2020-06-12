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
    private String group_id;
    private int page_index;
    private int size;


    public CommentListJson(String group_id, int page_index, int size) {
        this.group_id = group_id;
        this.page_index = page_index;
        this.size = size;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }
}
