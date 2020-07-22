package com.jianpei.jpeducation.bean.json;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/21
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class PaperDataJson {

    private int pageIndex;

    private int pageSize;
    private String cat_id;
    private String class_id;
    private String chapter_id;
    private String paper_type;


    public PaperDataJson(int pageIndex, int pageSize, String cat_id, String paper_type) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.cat_id = cat_id;
        this.paper_type = paper_type;
    }

    public PaperDataJson(int pageIndex, int pageSize, String cat_id, String class_id, String chapter_id, String paper_type) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.cat_id = cat_id;
        this.class_id = class_id;
        this.chapter_id = chapter_id;
        this.paper_type = paper_type;
    }

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

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getPaper_type() {
        return paper_type;
    }

    public void setPaper_type(String paper_type) {
        this.paper_type = paper_type;
    }
}
