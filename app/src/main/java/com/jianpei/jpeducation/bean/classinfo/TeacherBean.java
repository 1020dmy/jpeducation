package com.jianpei.jpeducation.bean.classinfo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class TeacherBean implements Parcelable {


    /**
     * id : 26
     * title : 王老师
     * onclick : 0
     * sort : 0
     * status : 1
     * seo_title :
     * seo_keywords :
     * seo_description :
     * create_time : 0
     * update_time : 0
     * info : 注册一级建造师，信息系统管理工程师，机电实务资深讲师；一级建造师考试教辅编委，具有丰富的施工经验，功底深厚，讲课风格清新自然，善于归纳总结，重点突出，风趣幽默，深受学员的喜爱。
     * zj_class : 机电实务
     * img : http://dev_api.jianpei.com.cn/Uploads/image/image/20181224/20181224111706_97455.jpg
     * mz_img : http://dev_api.jianpei.com.cn/Uploads/image/image/20181224/20181224111710_93598.png
     * index : 0
     * phone :
     * class_category :
     * name :
     */

    private String id;
    private String title;
    private String onclick;
    private String sort;
    private String status;
    private String seo_title;
    private String seo_keywords;
    private String seo_description;
    private String create_time;
    private String update_time;
    private String info;
    private String zj_class;
    private String img;
    private String mz_img;
    private String index;
    private String phone;
    private String class_category;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeo_title() {
        return seo_title;
    }

    public void setSeo_title(String seo_title) {
        this.seo_title = seo_title;
    }

    public String getSeo_keywords() {
        return seo_keywords;
    }

    public void setSeo_keywords(String seo_keywords) {
        this.seo_keywords = seo_keywords;
    }

    public String getSeo_description() {
        return seo_description;
    }

    public void setSeo_description(String seo_description) {
        this.seo_description = seo_description;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getZj_class() {
        return zj_class;
    }

    public void setZj_class(String zj_class) {
        this.zj_class = zj_class;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMz_img() {
        return mz_img;
    }

    public void setMz_img(String mz_img) {
        this.mz_img = mz_img;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClass_category() {
        return class_category;
    }

    public void setClass_category(String class_category) {
        this.class_category = class_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.onclick);
        dest.writeString(this.sort);
        dest.writeString(this.status);
        dest.writeString(this.seo_title);
        dest.writeString(this.seo_keywords);
        dest.writeString(this.seo_description);
        dest.writeString(this.create_time);
        dest.writeString(this.update_time);
        dest.writeString(this.info);
        dest.writeString(this.zj_class);
        dest.writeString(this.img);
        dest.writeString(this.mz_img);
        dest.writeString(this.index);
        dest.writeString(this.phone);
        dest.writeString(this.class_category);
        dest.writeString(this.name);
    }

    public TeacherBean() {
    }

    protected TeacherBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.onclick = in.readString();
        this.sort = in.readString();
        this.status = in.readString();
        this.seo_title = in.readString();
        this.seo_keywords = in.readString();
        this.seo_description = in.readString();
        this.create_time = in.readString();
        this.update_time = in.readString();
        this.info = in.readString();
        this.zj_class = in.readString();
        this.img = in.readString();
        this.mz_img = in.readString();
        this.index = in.readString();
        this.phone = in.readString();
        this.class_category = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<TeacherBean> CREATOR = new Parcelable.Creator<TeacherBean>() {
        @Override
        public TeacherBean createFromParcel(Parcel source) {
            return new TeacherBean(source);
        }

        @Override
        public TeacherBean[] newArray(int size) {
            return new TeacherBean[size];
        }
    };
}
