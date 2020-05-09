package com.jianpei.jpeducation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/8
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class LoginJson implements Parcelable {
    private String name;
    private String password;

    public LoginJson(String name, String password) {
        this.name = name;
        this.password = password;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.password);
    }

    protected LoginJson(Parcel in) {
        this.name = in.readString();
        this.password = in.readString();
    }

    public static final Parcelable.Creator<LoginJson> CREATOR = new Parcelable.Creator<LoginJson>() {
        @Override
        public LoginJson createFromParcel(Parcel source) {
            return new LoginJson(source);
        }

        @Override
        public LoginJson[] newArray(int size) {
            return new LoginJson[size];
        }
    };
}
