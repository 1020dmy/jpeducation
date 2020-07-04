package com.jianpei.jpeducation.bean.order;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/2
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class OrderListBean {



    private List<OrderDataBean> data;

    public List<OrderDataBean> getData() {
        return data;
    }

    public void setData(List<OrderDataBean> data) {
        this.data = data;
    }

    /**
     * pageData : {"pageIndex":1,"pageSize":10,"totalPage":4}
     */

    private PageDataBean pageData;

    public PageDataBean getPageData() {
        return pageData;
    }

    public void setPageData(PageDataBean pageData) {
        this.pageData = pageData;
    }

    public static class PageDataBean {
        /**
         * pageIndex : 1
         * pageSize : 10
         * totalPage : 4
         */

        private int pageIndex;
        private int pageSize;
        private int totalPage;

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }
    }
}
