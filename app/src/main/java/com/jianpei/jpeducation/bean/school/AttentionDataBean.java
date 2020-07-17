package com.jianpei.jpeducation.bean.school;


import com.jianpei.jpeducation.bean.PageDataBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class AttentionDataBean {


    private PageDataBean pageData;
    private List<AttentionBean> data;

    public PageDataBean getPageData() {
        return pageData;
    }

    public void setPageData(PageDataBean pageData) {
        this.pageData = pageData;
    }

    public List<AttentionBean> getData() {
        return data;
    }

    public void setData(List<AttentionBean> data) {
        this.data = data;
    }
}
