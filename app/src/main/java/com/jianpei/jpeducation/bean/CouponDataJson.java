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
public class CouponDataJson implements Parcelable {
    private int pageIndex;
    private int pageSize;
    private int type;

    private String cat_id;

    private String group_id;


    public CouponDataJson(int pageIndex, int pageSize, int type, String cat_id, String group_id) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.type = type;
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

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pageIndex);
        dest.writeInt(this.pageSize);
        dest.writeInt(this.type);
    }

    protected CouponDataJson(Parcel in) {
        this.pageIndex = in.readInt();
        this.pageSize = in.readInt();
        this.type = in.readInt();
    }

    public static final Creator<CouponDataJson> CREATOR = new Creator<CouponDataJson>() {
        @Override
        public CouponDataJson createFromParcel(Parcel source) {
            return new CouponDataJson(source);
        }

        @Override
        public CouponDataJson[] newArray(int size) {
            return new CouponDataJson[size];
        }
    };
}
