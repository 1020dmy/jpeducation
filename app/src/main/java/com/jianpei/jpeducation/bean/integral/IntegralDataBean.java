package com.jianpei.jpeducation.bean.integral;

import com.jianpei.jpeducation.bean.PageDataBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/7
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class IntegralDataBean {


    /**
     * pageData : {"page_index":1,"page_size":20,"total_page":1}
     * data : [{"id":"8","type":"2","integral":"-50","create_time_str":"2020-06-03 16:20:59","source":"兑换资料"}]
     */

    private PageDataBean pageData;
    private List<DataBean> data;

    public PageDataBean getPageData() {
        return pageData;
    }

    public void setPageData(PageDataBean pageData) {
        this.pageData = pageData;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


    public static class DataBean {
        /**
         * id : 8
         * type : 2
         * integral : -50
         * create_time_str : 2020-06-03 16:20:59
         * source : 兑换资料
         */

        private String id;
        private String type;
        private String integral;
        private String create_time_str;
        private String source;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getCreate_time_str() {
            return create_time_str;
        }

        public void setCreate_time_str(String create_time_str) {
            this.create_time_str = create_time_str;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }
}
