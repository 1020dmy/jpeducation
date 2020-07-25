package com.jianpei.jpeducation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/7
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class PageDataBean implements Parcelable {


    /**
     * page_index : 1
     * page_size : 20
     * total_page : 1
     */

    private int page_index;
    private int page_size;
    private int total_page;

    public int getPage_index() {
        return page_index;
    }

    public void setPage_index(int page_index) {
        this.page_index = page_index;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page_index);
        dest.writeInt(this.page_size);
        dest.writeInt(this.total_page);
    }

    public PageDataBean() {
    }

    protected PageDataBean(Parcel in) {
        this.page_index = in.readInt();
        this.page_size = in.readInt();
        this.total_page = in.readInt();
    }

    public static final Parcelable.Creator<PageDataBean> CREATOR = new Parcelable.Creator<PageDataBean>() {
        @Override
        public PageDataBean createFromParcel(Parcel source) {
            return new PageDataBean(source);
        }

        @Override
        public PageDataBean[] newArray(int size) {
            return new PageDataBean[size];
        }
    };
}
