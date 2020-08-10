package com.jianpei.jpeducation.bean.json;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/24
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class OrderInfoJson {
    private String order_id;


    public OrderInfoJson(String order_id) {
        this.order_id = order_id;
    }


    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
