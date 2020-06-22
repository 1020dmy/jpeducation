package com.jianpei.jpeducation.bean.json;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class GroupInfoJson implements Parcelable {

    private String group_id;
    private String regiment_id;

    public GroupInfoJson(String group_id) {
        this.group_id = group_id;
    }

    public GroupInfoJson(String group_id, String regiment_id) {
        this.group_id = group_id;
        this.regiment_id = regiment_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
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
        dest.writeString(this.regiment_id);
    }

    protected GroupInfoJson(Parcel in) {
        this.group_id = in.readString();
        this.regiment_id = in.readString();
    }

    public static final Creator<GroupInfoJson> CREATOR = new Creator<GroupInfoJson>() {
        @Override
        public GroupInfoJson createFromParcel(Parcel source) {
            return new GroupInfoJson(source);
        }

        @Override
        public GroupInfoJson[] newArray(int size) {
            return new GroupInfoJson[size];
        }
    };
}
