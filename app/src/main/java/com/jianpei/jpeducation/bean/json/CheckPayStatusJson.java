package com.jianpei.jpeducation.bean.json;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/30
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CheckPayStatusJson {

    private String order_id;

    private String pay_type;

    public CheckPayStatusJson(String order_id, String pay_type) {
        this.order_id = order_id;
        this.pay_type = pay_type;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }
}
