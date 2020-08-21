package com.jianpei.jpeducation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/20
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class StartingInfoBean implements Parcelable {
    private int countdown;
    private String image_path;

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.countdown);
        dest.writeString(this.image_path);
    }

    public StartingInfoBean() {
    }

    protected StartingInfoBean(Parcel in) {
        this.countdown = in.readInt();
        this.image_path = in.readString();
    }

    public static final Parcelable.Creator<StartingInfoBean> CREATOR = new Parcelable.Creator<StartingInfoBean>() {
        @Override
        public StartingInfoBean createFromParcel(Parcel source) {
            return new StartingInfoBean(source);
        }

        @Override
        public StartingInfoBean[] newArray(int size) {
            return new StartingInfoBean[size];
        }
    };
}
