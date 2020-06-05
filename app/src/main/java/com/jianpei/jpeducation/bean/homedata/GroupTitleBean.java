package com.jianpei.jpeducation.bean.homedata;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/2
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class GroupTitleBean  implements Parcelable {


    private String title;
    private String Subtitle;


    public GroupTitleBean(String title, String subtitle) {
        this.title = title;
        Subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return Subtitle;
    }

    public void setSubtitle(String subtitle) {
        Subtitle = subtitle;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.Subtitle);
    }

    public GroupTitleBean() {
    }

    protected GroupTitleBean(Parcel in) {
        this.title = in.readString();
        this.Subtitle = in.readString();
    }

    public static final Parcelable.Creator<GroupTitleBean> CREATOR = new Parcelable.Creator<GroupTitleBean>() {
        @Override
        public GroupTitleBean createFromParcel(Parcel source) {
            return new GroupTitleBean(source);
        }

        @Override
        public GroupTitleBean[] newArray(int size) {
            return new GroupTitleBean[size];
        }
    };
}
