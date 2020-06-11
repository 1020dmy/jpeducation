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
public class MaterialDataJson implements Parcelable {

    private String cat_id;

    private int pageIndex;
    private int pageSize;

    public MaterialDataJson(String cat_id, int pageIndex, int pageSize) {
        this.cat_id = cat_id;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cat_id);
        dest.writeInt(this.pageIndex);
        dest.writeInt(this.pageSize);
    }

    protected MaterialDataJson(Parcel in) {
        this.cat_id = in.readString();
        this.pageIndex = in.readInt();
        this.pageSize = in.readInt();
    }

    public static final Parcelable.Creator<MaterialDataJson> CREATOR = new Parcelable.Creator<MaterialDataJson>() {
        @Override
        public MaterialDataJson createFromParcel(Parcel source) {
            return new MaterialDataJson(source);
        }

        @Override
        public MaterialDataJson[] newArray(int size) {
            return new MaterialDataJson[size];
        }
    };
}
