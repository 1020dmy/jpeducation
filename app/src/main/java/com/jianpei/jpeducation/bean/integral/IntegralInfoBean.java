package com.jianpei.jpeducation.bean.integral;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/6
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class IntegralInfoBean {


    /**
     * day_status : 1
     * registration_rules_url :
     * registration_count : 5
     * registration_image :
     * registration_info : [{"date_str":"周日","date":"2020-06-07","is_sign":2,"integral_price":"5","sign_str":"未签"},{"date_str":"周一","date":"2020-06-08","is_sign":1,"integral_price":"5","sign_str":"已签"},{"date_str":"周二","date":"2020-06-09","is_sign":3,"integral_price":"5","sign_str":"待签"}]
     */

    private int day_status;
    private String registration_rules_url;
    private int registration_count;
    private String registration_image;
    private String total_registration;
    private String day_time;
    private List<RegistrationInfoBean> registration_info;


    public String getDay_time() {
        return day_time;
    }

    public void setDay_time(String day_time) {
        this.day_time = day_time;
    }

    public String getTotal_registration() {
        return total_registration;
    }

    public void setTotal_registration(String total_registration) {
        this.total_registration = total_registration;
    }

    public int getDay_status() {
        return day_status;
    }

    public void setDay_status(int day_status) {
        this.day_status = day_status;
    }

    public String getRegistration_rules_url() {
        return registration_rules_url;
    }

    public void setRegistration_rules_url(String registration_rules_url) {
        this.registration_rules_url = registration_rules_url;
    }

    public int getRegistration_count() {
        return registration_count;
    }

    public void setRegistration_count(int registration_count) {
        this.registration_count = registration_count;
    }

    public String getRegistration_image() {
        return registration_image;
    }

    public void setRegistration_image(String registration_image) {
        this.registration_image = registration_image;
    }

    public List<RegistrationInfoBean> getRegistration_info() {
        return registration_info;
    }

    public void setRegistration_info(List<RegistrationInfoBean> registration_info) {
        this.registration_info = registration_info;
    }

    public static class RegistrationInfoBean {
        /**
         * date_str : 周日
         * date : 2020-06-07
         * is_sign : 2
         * integral_price : 5
         * sign_str : 未签
         */

        private String date_str;
        private String date;
        private int is_sign;
        private String integral_price;
        private String sign_str;

        public String getDate_str() {
            return date_str;
        }

        public void setDate_str(String date_str) {
            this.date_str = date_str;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getIs_sign() {
            return is_sign;
        }

        public void setIs_sign(int is_sign) {
            this.is_sign = is_sign;
        }

        public String getIntegral_price() {
            return integral_price;
        }

        public void setIntegral_price(String integral_price) {
            this.integral_price = integral_price;
        }

        public String getSign_str() {
            return sign_str;
        }

        public void setSign_str(String sign_str) {
            this.sign_str = sign_str;
        }
    }
}
