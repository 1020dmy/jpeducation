package com.jianpei.jpeducation.bean.school;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class TopicDataBean {
    private List<TopicBean> recently_list;

    private List<TopicBean> base_list;


    public List<TopicBean> getRecently_list() {
        return recently_list;
    }

    public void setRecently_list(List<TopicBean> recently_list) {
        this.recently_list = recently_list;
    }

    public List<TopicBean> getBase_list() {
        return base_list;
    }

    public void setBase_list(List<TopicBean> base_list) {
        this.base_list = base_list;
    }
}
