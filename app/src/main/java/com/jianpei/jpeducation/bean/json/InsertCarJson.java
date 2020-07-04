package com.jianpei.jpeducation.bean.json;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/2
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class InsertCarJson {

    private String group_id;
    private String class_ids;
    private String suites_ids;

    public InsertCarJson(String group_id, String class_ids, String suites_ids) {
        this.group_id = group_id;
        this.class_ids = class_ids;
        this.suites_ids = suites_ids;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getClass_ids() {
        return class_ids;
    }

    public void setClass_ids(String class_ids) {
        this.class_ids = class_ids;
    }

    public String getSuites_ids() {
        return suites_ids;
    }

    public void setSuites_ids(String suites_ids) {
        this.suites_ids = suites_ids;
    }
}
