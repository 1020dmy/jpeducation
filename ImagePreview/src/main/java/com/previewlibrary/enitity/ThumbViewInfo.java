package com.previewlibrary.enitity;

import android.graphics.Rect;
import android.os.Parcel;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/17
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ThumbViewInfo implements IThumbViewInfo {


    private String url;  //图片地址
    private Rect mBounds; // 记录坐标
    private String user = "用户字段";

    public ThumbViewInfo(String url, Rect mBounds) {
        this.url = url;
        this.mBounds = mBounds;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Rect getBounds() {
        return mBounds;
    }

    public void setBounds(Rect mBounds) {
        this.mBounds = mBounds;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String getUrl() {
        return url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeParcelable(this.mBounds, flags);
        dest.writeString(this.user);
    }

    public ThumbViewInfo() {
    }

    protected ThumbViewInfo(Parcel in) {
        this.url = in.readString();
        this.mBounds = in.readParcelable(Rect.class.getClassLoader());
        this.user = in.readString();
    }

    public static final Creator<ThumbViewInfo> CREATOR = new Creator<ThumbViewInfo>() {
        @Override
        public ThumbViewInfo createFromParcel(Parcel source) {
            return new ThumbViewInfo(source);
        }

        @Override
        public ThumbViewInfo[] newArray(int size) {
            return new ThumbViewInfo[size];
        }
    };
}
