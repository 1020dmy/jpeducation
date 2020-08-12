package com.jianpei.jpeducation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ShareDataBean implements Parcelable {


    /**
     * share_img :
     * share_title :
     * share_content :
     * share_url :
     */

    private String share_img;
    private String share_title;
    private String share_content;
    private String share_url;

    public String getShare_img() {
        return share_img;
    }

    public void setShare_img(String share_img) {
        this.share_img = share_img;
    }

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    public String getShare_content() {
        return share_content;
    }

    public void setShare_content(String share_content) {
        this.share_content = share_content;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.share_img);
        dest.writeString(this.share_title);
        dest.writeString(this.share_content);
        dest.writeString(this.share_url);
    }

    public ShareDataBean() {
    }

    protected ShareDataBean(Parcel in) {
        this.share_img = in.readString();
        this.share_title = in.readString();
        this.share_content = in.readString();
        this.share_url = in.readString();
    }

    public static final Parcelable.Creator<ShareDataBean> CREATOR = new Parcelable.Creator<ShareDataBean>() {
        @Override
        public ShareDataBean createFromParcel(Parcel source) {
            return new ShareDataBean(source);
        }

        @Override
        public ShareDataBean[] newArray(int size) {
            return new ShareDataBean[size];
        }
    };
}
