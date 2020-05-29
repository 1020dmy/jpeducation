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
public class BindPhoneJson implements Parcelable {


    private String uid;
    private String phone;
    private String code;

    public BindPhoneJson(String uid, String phone, String code) {
        this.uid = uid;
        this.phone = phone;
        this.code = code;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.phone);
        dest.writeString(this.code);
    }

    protected BindPhoneJson(Parcel in) {
        this.uid = in.readString();
        this.phone = in.readString();
        this.code = in.readString();
    }

    public static final Parcelable.Creator<BindPhoneJson> CREATOR = new Parcelable.Creator<BindPhoneJson>() {
        @Override
        public BindPhoneJson createFromParcel(Parcel source) {
            return new BindPhoneJson(source);
        }

        @Override
        public BindPhoneJson[] newArray(int size) {
            return new BindPhoneJson[size];
        }
    };
}
