package com.jianpei.jpeducation.bean.school;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MThreadBean {

    private List<ThreadDataBean> data;


    /**
     * attention : {"u_count":"3","atten_count":"0"}
     */

    private AttentionBean attention;


    public List<ThreadDataBean> getData() {
        return data;
    }

    public void setData(List<ThreadDataBean> data) {
        this.data = data;
    }

    public AttentionBean getAttention() {
        return attention;
    }

    public void setAttention(AttentionBean attention) {
        this.attention = attention;
    }

    public static class AttentionBean {
        /**
         * u_count : 3
         * atten_count : 0
         */

        private String u_count;
        private String atten_count;

        public String getU_count() {
            return u_count;
        }

        public void setU_count(String u_count) {
            this.u_count = u_count;
        }

        public String getAtten_count() {
            return atten_count;
        }

        public void setAtten_count(String atten_count) {
            this.atten_count = atten_count;
        }
    }
}
