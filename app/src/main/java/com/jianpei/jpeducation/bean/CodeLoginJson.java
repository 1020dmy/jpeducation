package com.jianpei.jpeducation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/25
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CodeLoginJson implements Parcelable {

    private String phone;
    private String code;

    public CodeLoginJson(String phone, String code) {
        this.phone = phone;
        this.code = code;
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
        dest.writeString(this.phone);
        dest.writeString(this.code);
    }

    protected CodeLoginJson(Parcel in) {
        this.phone = in.readString();
        this.code = in.readString();
    }

    public static final Parcelable.Creator<CodeLoginJson> CREATOR = new Parcelable.Creator<CodeLoginJson>() {
        @Override
        public CodeLoginJson createFromParcel(Parcel source) {
            return new CodeLoginJson(source);
        }

        @Override
        public CodeLoginJson[] newArray(int size) {
            return new CodeLoginJson[size];
        }
    };
}
