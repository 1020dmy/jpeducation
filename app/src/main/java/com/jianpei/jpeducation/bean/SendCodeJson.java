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
public class SendCodeJson implements Parcelable {

   private String phone;
   private String type;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public SendCodeJson(String phone, String type) {
        this.phone = phone;
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.phone);
        dest.writeString(this.type);
    }

    protected SendCodeJson(Parcel in) {
        this.phone = in.readString();
        this.type = in.readString();
    }

    public static final Creator<SendCodeJson> CREATOR = new Creator<SendCodeJson>() {
        @Override
        public SendCodeJson createFromParcel(Parcel source) {
            return new SendCodeJson(source);
        }

        @Override
        public SendCodeJson[] newArray(int size) {
            return new SendCodeJson[size];
        }
    };
}
