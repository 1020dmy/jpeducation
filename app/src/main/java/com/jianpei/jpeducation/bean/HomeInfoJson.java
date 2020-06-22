package com.jianpei.jpeducation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class HomeInfoJson implements Parcelable {

    private String cat_id;
//    private String uid;

    public HomeInfoJson(String cat_id) {
        this.cat_id = cat_id;
//        this.uid = uid;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

//    public String getUid() {
//        return uid;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cat_id);
//        dest.writeString(this.uid);
    }

    protected HomeInfoJson(Parcel in) {
        this.cat_id = in.readString();
//        this.uid = in.readString();
    }

    public static final Creator<HomeInfoJson> CREATOR = new Creator<HomeInfoJson>() {
        @Override
        public HomeInfoJson createFromParcel(Parcel source) {
            return new HomeInfoJson(source);
        }

        @Override
        public HomeInfoJson[] newArray(int size) {
            return new HomeInfoJson[size];
        }
    };
}
