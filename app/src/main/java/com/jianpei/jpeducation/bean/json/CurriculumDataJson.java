package com.jianpei.jpeducation.bean.json;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/23
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CurriculumDataJson {
    private String cat_id;
    private String parent_id;

    public CurriculumDataJson(String cat_id, String parent_id) {
        this.cat_id = cat_id;
        this.parent_id = parent_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }
}
