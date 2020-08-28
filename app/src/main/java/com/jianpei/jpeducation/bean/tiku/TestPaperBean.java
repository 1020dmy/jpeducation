package com.jianpei.jpeducation.bean.tiku;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/21
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class TestPaperBean implements Parcelable {


    /**
     * id : 139
     * paper_name : 一级建造师-公路工程实务-历年真题-2019
     * cat_id : 97
     * class_id : 1
     * chapter_id : 0
     * user_is_complete : 0
     * user_record_id :
     */

    private String id;
    private String paper_name;
    private String cat_id;
    private String class_id;
    private String chapter_id;
    private String user_is_complete;
    private String user_record_id;

    private String total_que_num;
    private String success_num;
    private String create_time_str;
    private String paper_type;

    public String getPaper_type() {
        return paper_type;
    }

    public void setPaper_type(String paper_type) {
        this.paper_type = paper_type;
    }

    public String getTotal_que_num() {
        return total_que_num;
    }

    public void setTotal_que_num(String total_que_num) {
        this.total_que_num = total_que_num;
    }

    public String getSuccess_num() {
        return success_num;
    }

    public void setSuccess_num(String success_num) {
        this.success_num = success_num;
    }

    public String getCreate_time_str() {
        return create_time_str;
    }

    public void setCreate_time_str(String create_time_str) {
        this.create_time_str = create_time_str;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaper_name() {
        return paper_name;
    }

    public void setPaper_name(String paper_name) {
        this.paper_name = paper_name;
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

    public String getUser_is_complete() {
        return user_is_complete;
    }

    public void setUser_is_complete(String user_is_complete) {
        this.user_is_complete = user_is_complete;
    }

    public String getUser_record_id() {
        return user_record_id;
    }

    public void setUser_record_id(String user_record_id) {
        this.user_record_id = user_record_id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.paper_name);
        dest.writeString(this.cat_id);
        dest.writeString(this.class_id);
        dest.writeString(this.chapter_id);
        dest.writeString(this.user_is_complete);
        dest.writeString(this.user_record_id);
        dest.writeString(this.total_que_num);
        dest.writeString(this.success_num);
        dest.writeString(this.create_time_str);
        dest.writeString(this.paper_type);
    }

    public TestPaperBean() {
    }

    protected TestPaperBean(Parcel in) {
        this.id = in.readString();
        this.paper_name = in.readString();
        this.cat_id = in.readString();
        this.class_id = in.readString();
        this.chapter_id = in.readString();
        this.user_is_complete = in.readString();
        this.user_record_id = in.readString();
        this.total_que_num = in.readString();
        this.success_num = in.readString();
        this.create_time_str = in.readString();
        this.paper_type = in.readString();
    }

    public static final Creator<TestPaperBean> CREATOR = new Creator<TestPaperBean>() {
        @Override
        public TestPaperBean createFromParcel(Parcel source) {
            return new TestPaperBean(source);
        }

        @Override
        public TestPaperBean[] newArray(int size) {
            return new TestPaperBean[size];
        }
    };
}
