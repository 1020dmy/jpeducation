package com.jianpei.jpeducation.bean.mine;

import com.jianpei.jpeducation.bean.PageDataBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MessageDataBean {

    private PageDataBean pageData;

    private List<MessageBean> data;

    public PageDataBean getPageData() {
        return pageData;
    }

    public void setPageData(PageDataBean pageData) {
        this.pageData = pageData;
    }

    public List<MessageBean> getData() {
        return data;
    }

    public void setData(List<MessageBean> data) {
        this.data = data;
    }
}
