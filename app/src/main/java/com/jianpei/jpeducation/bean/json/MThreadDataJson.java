package com.jianpei.jpeducation.bean.json;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/20
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MThreadDataJson {

    private int pageIndex;
    private int pageSize;
    private String user_id;

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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public MThreadDataJson(int pageIndex, int pageSize, String user_id) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.user_id = user_id;
    }
}
