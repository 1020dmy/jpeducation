package com.jianpei.jpeducation.bean.classinfo;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class RegimentDataBean {


    /**
     * pageData : {"page_index":1,"page_size":6,"total_page":1}
     * data : [{"id":"1","start_time":"1591681306","end_time":"1591781306","create_time":"1591681306","user_id":"2414","huod_id":"42","is_source":"1","user_name":"15801080541","img":"http://localhost/Source/pc/images/shiimg/a15.png","num_people":"5","remaining_number":3,"remaining_time":97385},{"id":"2","start_time":"1591681306","end_time":"1592681306","create_time":"1591681306","user_id":"2414","huod_id":"42","is_source":"1","user_name":"15801080541","img":"http://localhost/Source/pc/images/shiimg/a15.png","num_people":"5","remaining_number":4,"remaining_time":997385}]
     */

    private PageDataBean pageData;
    private List<RegimentBean> data;

    public PageDataBean getPageData() {
        return pageData;
    }

    public void setPageData(PageDataBean pageData) {
        this.pageData = pageData;
    }

    public List<RegimentBean> getData() {
        return data;
    }

    public void setData(List<RegimentBean> data) {
        this.data = data;
    }

    public static class PageDataBean {
        /**
         * page_index : 1
         * page_size : 6
         * total_page : 1
         */

        private int page_index;
        private int page_size;
        private int total_page;

        public int getPage_index() {
            return page_index;
        }

        public void setPage_index(int page_index) {
            this.page_index = page_index;
        }

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }
    }


}
