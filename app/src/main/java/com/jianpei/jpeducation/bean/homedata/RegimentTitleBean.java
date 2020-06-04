package com.jianpei.jpeducation.bean.homedata;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/2
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class RegimentTitleBean extends ProviderMultiEntity implements Parcelable {

    private String title;
    private String Subtitle;

    public RegimentTitleBean(String title, String subtitle) {
        this.title = title;
        Subtitle = subtitle;
    }

    @Override
    public int getItemType() {
        return RGT;
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



    protected RegimentTitleBean(Parcel in) {
        this.title = in.readString();
        this.Subtitle = in.readString();
    }

    public static final Parcelable.Creator<RegimentTitleBean> CREATOR = new Parcelable.Creator<RegimentTitleBean>() {
        @Override
        public RegimentTitleBean createFromParcel(Parcel source) {
            return new RegimentTitleBean(source);
        }

        @Override
        public RegimentTitleBean[] newArray(int size) {
            return new RegimentTitleBean[size];
        }
    };
}
