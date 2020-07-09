package com.jianpei.jpeducation.bean;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/7
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class PageDataBean {


    /**
     * page_index : 1
     * page_size : 20
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
