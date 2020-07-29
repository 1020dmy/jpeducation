package com.jianpei.jpeducation.bean.school;

import com.jianpei.jpeducation.bean.PageDataBean;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MThreadDataBean {

    private PageDataBean pageData;

    private MThreadBean data;


    public PageDataBean getPageData() {
        return pageData;
    }

    public void setPageData(PageDataBean pageData) {
        this.pageData = pageData;
    }

    public MThreadBean getData() {
        return data;
    }

    public void setData(MThreadBean data) {
        this.data = data;
    }
}
