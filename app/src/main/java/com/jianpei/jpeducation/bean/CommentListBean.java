package com.jianpei.jpeducation.bean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CommentListBean {


    /**
     * pageData : {"page_index":1,"page_size":10,"total_page":1}
     */

    private PageDataBean pageData;

    private List<CommentBean> data;

    public List<CommentBean> getData() {
        return data;
    }

    public void setData(List<CommentBean> data) {
        this.data = data;
    }

    public PageDataBean getPageData() {
        return pageData;
    }

    public void setPageData(PageDataBean pageData) {
        this.pageData = pageData;
    }


}
