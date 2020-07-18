package com.jianpei.jpeducation.bean.school;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ImagesBean implements Parcelable {


    /**
     * id : 24
     * thread_id : 13
     * url : http://www.jianpei.com.cn/Uploads/image/image/20200623/20200623091741_76533.jpg
     * sort_num : 1
     * width : 440
     * height : 319
     */

    private String id;
    private String thread_id;
    private String url;
    private String sort_num;
    private String width;
    private String height;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSort_num() {
        return sort_num;
    }

    public void setSort_num(String sort_num) {
        this.sort_num = sort_num;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.thread_id);
        dest.writeString(this.url);
        dest.writeString(this.sort_num);
        dest.writeString(this.width);
        dest.writeString(this.height);
    }

    public ImagesBean() {
    }

    protected ImagesBean(Parcel in) {
        this.id = in.readString();
        this.thread_id = in.readString();
        this.url = in.readString();
        this.sort_num = in.readString();
        this.width = in.readString();
        this.height = in.readString();
    }

    public static final Parcelable.Creator<ImagesBean> CREATOR = new Parcelable.Creator<ImagesBean>() {
        @Override
        public ImagesBean createFromParcel(Parcel source) {
            return new ImagesBean(source);
        }

        @Override
        public ImagesBean[] newArray(int size) {
            return new ImagesBean[size];
        }
    };
}
