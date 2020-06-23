package com.jianpei.jpeducation.bean.json;

public class ClassGenerateOrderJson {


    private String goods_type;
    private String group_id;
    private String coupon_id;
    private String order_id;
    private String class_ids;
    private String suites_ids;
    private String regiment_id;
    private String gather_id;


    public ClassGenerateOrderJson(String goods_type, String group_id, String coupon_id, String order_id, String class_ids, String suites_ids, String regiment_id, String gather_id) {
        this.goods_type = goods_type;
        this.group_id = group_id;
        this.coupon_id = coupon_id;
        this.order_id = order_id;
        this.class_ids = class_ids;
        this.suites_ids = suites_ids;
        this.regiment_id = regiment_id;
        this.gather_id = gather_id;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getClass_ids() {
        return class_ids;
    }

    public void setClass_ids(String class_ids) {
        this.class_ids = class_ids;
    }

    public String getSuites_ids() {
        return suites_ids;
    }

    public void setSuites_ids(String suites_ids) {
        this.suites_ids = suites_ids;
    }

    public String getRegiment_id() {
        return regiment_id;
    }

    public void setRegiment_id(String regiment_id) {
        this.regiment_id = regiment_id;
    }

    public String getGather_id() {
        return gather_id;
    }

    public void setGather_id(String gather_id) {
        this.gather_id = gather_id;
    }
}
