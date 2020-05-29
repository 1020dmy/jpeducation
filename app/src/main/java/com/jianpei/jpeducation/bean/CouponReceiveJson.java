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
public class CouponReceiveJson implements Parcelable {

    private String couponId;

    private String shareUserId;

    public CouponReceiveJson(String couponId, String shareUserId) {
        this.couponId = couponId;
        this.shareUserId = shareUserId;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(String shareUserId) {
        this.shareUserId = shareUserId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.couponId);
        dest.writeString(this.shareUserId);
    }

    protected CouponReceiveJson(Parcel in) {
        this.couponId = in.readString();
        this.shareUserId = in.readString();
    }

    public static final Parcelable.Creator<CouponReceiveJson> CREATOR = new Parcelable.Creator<CouponReceiveJson>() {
        @Override
        public CouponReceiveJson createFromParcel(Parcel source) {
            return new CouponReceiveJson(source);
        }

        @Override
        public CouponReceiveJson[] newArray(int size) {
            return new CouponReceiveJson[size];
        }
    };
}
