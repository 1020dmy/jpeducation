package com.jianpei.jpeducation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CouponDataBean implements Parcelable {


    /**
     * id : 1612
     * user_id : 2367
     * intergral : 200
     * type : 0
     * add_time : 1578919242
     * ux_time : 1581511242
     * date : null
     * uid : null
     * cid : 0
     * condition : 500
     * cat_id : 0
     * add_time_str : 2020年01月13
     * ux_time_str : 2020年02月12
     */

    private String id;
    private String user_id;
    private String intergral;
    private String type;
    private String add_time;
    private String ux_time;
    private String date;
    private String uid;
    private String cid;
    private String condition;
    private String cat_id;
    private String add_time_str;
    private String ux_time_str;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getIntergral() {
        return intergral;
    }

    public void setIntergral(String intergral) {
        this.intergral = intergral;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getUx_time() {
        return ux_time;
    }

    public void setUx_time(String ux_time) {
        this.ux_time = ux_time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getAdd_time_str() {
        return add_time_str;
    }

    public void setAdd_time_str(String add_time_str) {
        this.add_time_str = add_time_str;
    }

    public String getUx_time_str() {
        return ux_time_str;
    }

    public void setUx_time_str(String ux_time_str) {
        this.ux_time_str = ux_time_str;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.user_id);
        dest.writeString(this.intergral);
        dest.writeString(this.type);
        dest.writeString(this.add_time);
        dest.writeString(this.ux_time);
        dest.writeString(this.date);
        dest.writeString(this.uid);
        dest.writeString(this.cid);
        dest.writeString(this.condition);
        dest.writeString(this.cat_id);
        dest.writeString(this.add_time_str);
        dest.writeString(this.ux_time_str);
    }

    public CouponDataBean() {
    }

    protected CouponDataBean(Parcel in) {
        this.id = in.readString();
        this.user_id = in.readString();
        this.intergral = in.readString();
        this.type = in.readString();
        this.add_time = in.readString();
        this.ux_time = in.readString();
        this.date = in.readParcelable(String.class.getClassLoader());
        this.uid = in.readParcelable(String.class.getClassLoader());
        this.cid = in.readString();
        this.condition = in.readString();
        this.cat_id = in.readString();
        this.add_time_str = in.readString();
        this.ux_time_str = in.readString();
    }

    public static final Creator<CouponDataBean> CREATOR = new Creator<CouponDataBean>() {
        @Override
        public CouponDataBean createFromParcel(Parcel source) {
            return new CouponDataBean(source);
        }

        @Override
        public CouponDataBean[] newArray(int size) {
            return new CouponDataBean[size];
        }
    };
}
