package com.jianpei.jpeducation.bean.mclass;

import com.jianpei.jpeducation.bean.PageDataBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ClassDataBean {

    private PageDataBean pageData;

    private List<MyClassBean> data;

    public PageDataBean getPageData() {
        return pageData;
    }

    public void setPageData(PageDataBean pageData) {
        this.pageData = pageData;
    }

    public List<MyClassBean> getData() {
        return data;
    }

    public void setData(List<MyClassBean> data) {
        this.data = data;
    }
}
