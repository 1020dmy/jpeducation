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
public class AttentionBean implements Parcelable {

    /**
     * id : 16
     * user_name : 葛恒良
     * img : http://www.jianpei.com.cn/Source/pc/images/shiimg/a15.png
     */

    private String id;
    private String user_name;
    private String img;

    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.user_name);
        dest.writeString(this.img);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
    }

    public AttentionBean() {
    }

    protected AttentionBean(Parcel in) {
        this.id = in.readString();
        this.user_name = in.readString();
        this.img = in.readString();
        this.isSelect = in.readByte() != 0;
    }

    public static final Parcelable.Creator<AttentionBean> CREATOR = new Parcelable.Creator<AttentionBean>() {
        @Override
        public AttentionBean createFromParcel(Parcel source) {
            return new AttentionBean(source);
        }

        @Override
        public AttentionBean[] newArray(int size) {
            return new AttentionBean[size];
        }
    };
}
