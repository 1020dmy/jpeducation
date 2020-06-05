package com.jianpei.jpeducation.bean.homedata;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/2
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class GroupInfoBean  implements Parcelable {

    /**
     * id : 5
     * cat_id : 107
     * title : 三年畅学班
     * sub_title : 2020年一注九门全科
     * desc : null
     * tags : null
     * status : 1
     * teacher_ids : {"9":"5","10":"4"}
     * img : null
     * content : null
     * start_time : 0
     * end_time : 1669820400
     * year_num : 3
     * deleted : 0
     * add_time : 1574059051
     * sort_num : 3
     * buy_num : 3
     * coupon_str :
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
    private String content;
    private String start_time;
    private String end_time;
    private String year_num;
    private String deleted;
    private String add_time;
    private String sort_num;
    private String buy_num;
    private String coupon_str;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
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

    public String getBuy_num() {
        return buy_num;
    }

    public void setBuy_num(String buy_num) {
        this.buy_num = buy_num;
    }

    public String getCoupon_str() {
        return coupon_str;
    }

    public void setCoupon_str(String coupon_str) {
        this.coupon_str = coupon_str;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.cat_id);
        dest.writeString(this.title);
        dest.writeString(this.sub_title);
        dest.writeString(this.desc);
        dest.writeString(this.tags);
        dest.writeString(this.status);
        dest.writeString(this.teacher_ids);
        dest.writeString(this.img);
        dest.writeString(this.content);
        dest.writeString(this.start_time);
        dest.writeString(this.end_time);
        dest.writeString(this.year_num);
        dest.writeString(this.deleted);
        dest.writeString(this.add_time);
        dest.writeString(this.sort_num);
        dest.writeString(this.buy_num);
        dest.writeString(this.coupon_str);
    }

    public GroupInfoBean() {
    }

    protected GroupInfoBean(Parcel in) {
        this.id = in.readString();
        this.cat_id = in.readString();
        this.title = in.readString();
        this.sub_title = in.readString();
        this.desc = in.readString();
        this.tags = in.readString();
        this.status = in.readString();
        this.teacher_ids = in.readString();
        this.img = in.readString();
        this.content = in.readString();
        this.start_time = in.readString();
        this.end_time = in.readString();
        this.year_num = in.readString();
        this.deleted = in.readString();
        this.add_time = in.readString();
        this.sort_num = in.readString();
        this.buy_num = in.readString();
        this.coupon_str = in.readString();
    }

    public static final Parcelable.Creator<GroupInfoBean> CREATOR = new Parcelable.Creator<GroupInfoBean>() {
        @Override
        public GroupInfoBean createFromParcel(Parcel source) {
            return new GroupInfoBean(source);
        }

        @Override
        public GroupInfoBean[] newArray(int size) {
            return new GroupInfoBean[size];
        }
    };
}
