package com.jianpei.jpeducation.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.sunfusheng.marqueeview.IMarqueeItem;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/3
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class NoticeDataBean implements Parcelable, IMarqueeItem {


    /**
     * id : 6
     * type : other
     * title : 公告6
     * url :
     * point_id :
     * create_time :
     * update_time :
     */

    private String id;
    private String type;
    private String title;
    private String url;
    private String point_id;
    private String create_time;
    private String update_time;

    @Override
    public CharSequence marqueeMessage() {
        return title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPoint_id() {
        return point_id;
    }

    public void setPoint_id(String point_id) {
        this.point_id = point_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.type);
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeString(this.point_id);
        dest.writeString(this.create_time);
        dest.writeString(this.update_time);
    }

    public NoticeDataBean() {
    }

    protected NoticeDataBean(Parcel in) {
        this.id = in.readString();
        this.type = in.readString();
        this.title = in.readString();
        this.url = in.readString();
        this.point_id = in.readString();
        this.create_time = in.readString();
        this.update_time = in.readString();
    }

    public static final Creator<NoticeDataBean> CREATOR = new Creator<NoticeDataBean>() {
        @Override
        public NoticeDataBean createFromParcel(Parcel source) {
            return new NoticeDataBean(source);
        }

        @Override
        public NoticeDataBean[] newArray(int size) {
            return new NoticeDataBean[size];
        }
    };
}

