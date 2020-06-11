package com.jianpei.jpeducation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SubMaterialDataJson implements Parcelable {

    private String cat_id;

    private String class_id;

    public SubMaterialDataJson(String cat_id, String class_id) {
        this.cat_id = cat_id;
        this.class_id = class_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cat_id);
        dest.writeString(this.class_id);
    }

    protected SubMaterialDataJson(Parcel in) {
        this.cat_id = in.readString();
        this.class_id = in.readString();
    }

    public static final Parcelable.Creator<SubMaterialDataJson> CREATOR = new Parcelable.Creator<SubMaterialDataJson>() {
        @Override
        public SubMaterialDataJson createFromParcel(Parcel source) {
            return new SubMaterialDataJson(source);
        }

        @Override
        public SubMaterialDataJson[] newArray(int size) {
            return new SubMaterialDataJson[size];
        }
    };
}
