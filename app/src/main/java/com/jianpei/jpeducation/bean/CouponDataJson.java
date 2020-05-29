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
    private String pageIndex;
    private String pageSize;
    private String type;


    public CouponDataJson(String pageIndex, String pageSize, String type) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.type = type;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pageIndex);
        dest.writeString(this.pageSize);
        dest.writeString(this.type);
    }

    protected CouponDataJson(Parcel in) {
        this.pageIndex = in.readString();
        this.pageSize = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<CouponDataJson> CREATOR = new Parcelable.Creator<CouponDataJson>() {
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
