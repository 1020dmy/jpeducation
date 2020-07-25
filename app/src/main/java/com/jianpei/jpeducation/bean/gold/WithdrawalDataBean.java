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
public class WithdrawalDataBean {

    private PageDataBean pageData;

    private List<WithdrawalBean> data;

    public PageDataBean getPageData() {
        return pageData;
    }

    public void setPageData(PageDataBean pageData) {
        this.pageData = pageData;
    }

    public List<WithdrawalBean> getData() {
        return data;
    }

    public void setData(List<WithdrawalBean> data) {
        this.data = data;
    }
}
