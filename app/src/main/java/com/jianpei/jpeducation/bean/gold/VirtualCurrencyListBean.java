package com.jianpei.jpeducation.bean.gold;

import com.jianpei.jpeducation.bean.PageDataBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/25
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class VirtualCurrencyListBean {

    private PageDataBean pageData;

    private List<GoldBean> data;

    public PageDataBean getPageData() {
        return pageData;
    }

    public void setPageData(PageDataBean pageData) {
        this.pageData = pageData;
    }

    public List<GoldBean> getData() {
        return data;
    }

    public void setData(List<GoldBean> data) {
        this.data = data;
    }
}
