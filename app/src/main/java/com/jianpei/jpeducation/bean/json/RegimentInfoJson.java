package com.jianpei.jpeducation.bean.json;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class RegimentInfoJson {

    private String regiment_id;

    public RegimentInfoJson(String regiment_id) {
        this.regiment_id = regiment_id;
    }

    public String getRegiment_id() {
        return regiment_id;
    }

    public void setRegiment_id(String regiment_id) {
        this.regiment_id = regiment_id;
    }
}
