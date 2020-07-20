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
public class TopicBean implements Parcelable {


    /**
     * id : 11
     * title : 坚持了17年的梦想22222
     */

    private String id;
    private String title;
    private int type;

    private String view_num;
    private String post_num;
    private String content;

    public String getView_num() {
        return view_num;
    }

    public void setView_num(String view_num) {
        this.view_num = view_num;
    }

    public String getPost_num() {
        return post_num;
    }

    public void setPost_num(String post_num) {
        this.post_num = post_num;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private boolean isSelect;

    public TopicBean() {
    }

    public TopicBean(String id, String title, int type) {
        this.id = id;
        this.title = title;
        this.type = type;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeInt(this.type);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
    }

    protected TopicBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.type = in.readInt();
        this.isSelect = in.readByte() != 0;
    }

    public static final Parcelable.Creator<TopicBean> CREATOR = new Parcelable.Creator<TopicBean>() {
        @Override
        public TopicBean createFromParcel(Parcel source) {
            return new TopicBean(source);
        }

        @Override
        public TopicBean[] newArray(int size) {
            return new TopicBean[size];
        }
    };
}
