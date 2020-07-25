package com.jianpei.jpeducation.bean.gold;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/25
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class GoldBean implements Parcelable {


    /**
     * id : 42
     * user_id : 4139
     * type : 1
     * before : 76.00
     * after : 78.00
     * change : 2.00
     * source_str : 购买课程
     * create_time_str : 2020-07-18
     * change_str : +2.00
     */

    private String id;
    private String user_id;
    private String type;
    private String before;
    private String after;
    private String change;
    private String source_str;
    private String create_time_str;
    private String change_str;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getSource_str() {
        return source_str;
    }

    public void setSource_str(String source_str) {
        this.source_str = source_str;
    }

    public String getCreate_time_str() {
        return create_time_str;
    }

    public void setCreate_time_str(String create_time_str) {
        this.create_time_str = create_time_str;
    }

    public String getChange_str() {
        return change_str;
    }

    public void setChange_str(String change_str) {
        this.change_str = change_str;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.user_id);
        dest.writeString(this.type);
        dest.writeString(this.before);
        dest.writeString(this.after);
        dest.writeString(this.change);
        dest.writeString(this.source_str);
        dest.writeString(this.create_time_str);
        dest.writeString(this.change_str);
    }

    public GoldBean() {
    }

    protected GoldBean(Parcel in) {
        this.id = in.readString();
        this.user_id = in.readString();
        this.type = in.readString();
        this.before = in.readString();
        this.after = in.readString();
        this.change = in.readString();
        this.source_str = in.readString();
        this.create_time_str = in.readString();
        this.change_str = in.readString();
    }

    public static final Parcelable.Creator<GoldBean> CREATOR = new Parcelable.Creator<GoldBean>() {
        @Override
        public GoldBean createFromParcel(Parcel source) {
            return new GoldBean(source);
        }

        @Override
        public GoldBean[] newArray(int size) {
            return new GoldBean[size];
        }
    };
}
