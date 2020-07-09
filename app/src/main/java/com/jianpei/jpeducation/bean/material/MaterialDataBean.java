package com.jianpei.jpeducation.bean.material;

import com.jianpei.jpeducation.bean.PageDataBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/8
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MaterialDataBean {


    private PageDataBean pageData;

    private List<MaterialTitle> data;

    public PageDataBean getPageData() {
        return pageData;
    }

    public void setPageData(PageDataBean pageData) {
        this.pageData = pageData;
    }

    public List<MaterialTitle> getData() {
        return data;
    }

    public void setData(List<MaterialTitle> data) {
        this.data = data;
    }
}
