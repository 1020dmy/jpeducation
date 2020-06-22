package com.jianpei.jpeducation.bean.classinfo;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ClassInfoBean {


    /**
     * id : 38
     * cat_id : 97
     * title : 无忧班
     * sub_title :
     * desc :
     * tags :
     * status : 1
     * teacher_ids :
     * img :
     * content :
     * start_time : 0
     * end_time : 1632927600
     * year_num : 4
     * deleted : 0
     * add_time : 1585076083
     * sort_num : 0
     * start_time_str : 1970-01-01
     * end_time_str : 2021-09-29
     * original_price_info : ￥1580.00-45380.00
     * buy_num : 2
     * material_des : 购买课程送相关课程资料，快点来领取吧
     * our_service : 关于服务的介绍
     * our_guarantee : 关于保障的介绍
     * video_id : 4403
     * video_time_str : 0.0h-11191.9h
     * is_coupon : 1
     * teacher_names : ["宿老师","王老师","杨老师","李老师","李老师","周老师","  王老师","王老师"]
     */

    private String id;
    private String cat_id;
    private String title;
    private String sub_title;
    private String desc;
    private String tags;
    private String status;
    private String teacher_ids;
    private String img;
    private List<String> content;
    private String start_time;
    private String end_time;
    private String year_num;
    private String deleted;
    private String add_time;
    private String sort_num;
    private String start_time_str;
    private String end_time_str;
    private String original_price_info;
    private String huod_price_info;
    private String buy_num;
    private String material_des;
    private String our_service;
    private String our_guarantee;
    private String video_id;
    private String video_time_str;
    private int is_coupon;
    private List<String> teacher_names;
    private List<TeacherBean> teachers;

    public String getHuod_price_info() {
        return huod_price_info;
    }

    public void setHuod_price_info(String huod_price_info) {
        this.huod_price_info = huod_price_info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTeacher_ids() {
        return teacher_ids;
    }

    public void setTeacher_ids(String teacher_ids) {
        this.teacher_ids = teacher_ids;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getYear_num() {
        return year_num;
    }

    public void setYear_num(String year_num) {
        this.year_num = year_num;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getSort_num() {
        return sort_num;
    }

    public void setSort_num(String sort_num) {
        this.sort_num = sort_num;
    }

    public String getStart_time_str() {
        return start_time_str;
    }

    public void setStart_time_str(String start_time_str) {
        this.start_time_str = start_time_str;
    }

    public String getEnd_time_str() {
        return end_time_str;
    }

    public void setEnd_time_str(String end_time_str) {
        this.end_time_str = end_time_str;
    }

    public String getOriginal_price_info() {
        return original_price_info;
    }

    public void setOriginal_price_info(String original_price_info) {
        this.original_price_info = original_price_info;
    }

    public String getBuy_num() {
        return buy_num;
    }

    public void setBuy_num(String buy_num) {
        this.buy_num = buy_num;
    }

    public String getMaterial_des() {
        return material_des;
    }

    public void setMaterial_des(String material_des) {
        this.material_des = material_des;
    }

    public String getOur_service() {
        return our_service;
    }

    public void setOur_service(String our_service) {
        this.our_service = our_service;
    }

    public String getOur_guarantee() {
        return our_guarantee;
    }

    public void setOur_guarantee(String our_guarantee) {
        this.our_guarantee = our_guarantee;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getVideo_time_str() {
        return video_time_str;
    }

    public void setVideo_time_str(String video_time_str) {
        this.video_time_str = video_time_str;
    }

    public int getIs_coupon() {
        return is_coupon;
    }

    public void setIs_coupon(int is_coupon) {
        this.is_coupon = is_coupon;
    }

    public List<String> getTeacher_names() {
        return teacher_names;
    }

    public void setTeacher_names(List<String> teacher_names) {
        this.teacher_names = teacher_names;
    }

    public List<TeacherBean> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherBean> teachers) {
        this.teachers = teachers;
    }
}
