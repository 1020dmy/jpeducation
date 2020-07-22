package com.jianpei.jpeducation.bean.tiku;

import com.jianpei.jpeducation.bean.PageDataBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/21
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class PaperDataBean {

    private PageDataBean pageData;

    private List<TestPaperBean> data;

    public PageDataBean getPageData() {
        return pageData;
    }

    public void setPageData(PageDataBean pageData) {
        this.pageData = pageData;
    }

    public List<TestPaperBean> getData() {
        return data;
    }

    public void setData(List<TestPaperBean> data) {
        this.data = data;
    }
}
