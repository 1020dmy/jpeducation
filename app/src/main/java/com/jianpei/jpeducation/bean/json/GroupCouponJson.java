package com.jianpei.jpeducation.bean.json;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/11
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class GroupCouponJson implements Parcelable {

    private String cat_id;
    private String group_id;

    public GroupCouponJson(String cat_id, String group_id) {
        this.cat_id = cat_id;
        this.group_id = group_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cat_id);
        dest.writeString(this.group_id);
    }

    protected GroupCouponJson(Parcel in) {
        this.cat_id = in.readString();
        this.group_id = in.readString();
    }

    public static final Parcelable.Creator<GroupCouponJson> CREATOR = new Parcelable.Creator<GroupCouponJson>() {
        @Override
        public GroupCouponJson createFromParcel(Parcel source) {
            return new GroupCouponJson(source);
        }

        @Override
        public GroupCouponJson[] newArray(int size) {
            return new GroupCouponJson[size];
        }
    };
}
