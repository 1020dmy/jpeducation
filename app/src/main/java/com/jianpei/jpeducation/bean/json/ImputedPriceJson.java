package com.jianpei.jpeducation.bean.json;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ImputedPriceJson implements Parcelable {
    private String group_id;
    private String class_ids;
    private String suites_ids;
    private String regiment_id;

    public ImputedPriceJson(String group_id, String class_ids, String suites_ids, String regiment_id) {
        this.group_id = group_id;
        this.class_ids = class_ids;
        this.suites_ids = suites_ids;
        this.regiment_id = regiment_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getClass_ids() {
        return class_ids;
    }

    public void setClass_ids(String class_ids) {
        this.class_ids = class_ids;
    }

    public String getSuites_ids() {
        return suites_ids;
    }

    public void setSuites_ids(String suites_ids) {
        this.suites_ids = suites_ids;
    }

    public String getRegiment_id() {
        return regiment_id;
    }

    public void setRegiment_id(String regiment_id) {
        this.regiment_id = regiment_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.group_id);
        dest.writeString(this.class_ids);
        dest.writeString(this.suites_ids);
        dest.writeString(this.regiment_id);
    }

    protected ImputedPriceJson(Parcel in) {
        this.group_id = in.readString();
        this.class_ids = in.readString();
        this.suites_ids = in.readString();
        this.regiment_id = in.readString();
    }

    public static final Parcelable.Creator<ImputedPriceJson> CREATOR = new Parcelable.Creator<ImputedPriceJson>() {
        @Override
        public ImputedPriceJson createFromParcel(Parcel source) {
            return new ImputedPriceJson(source);
        }

        @Override
        public ImputedPriceJson[] newArray(int size) {
            return new ImputedPriceJson[size];
        }
    };
}
