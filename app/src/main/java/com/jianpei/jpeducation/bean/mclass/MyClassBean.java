package com.jianpei.jpeducation.bean.mclass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MyClassBean implements Parcelable {


    /**
     * id : 13532
     * title : 2020建筑材料与构造
     * img : http://www.jianpei.com.cn/Uploads/img/201812051647571430.jpeg
     * cid : 24
     * total_count : 321
     * video_count : 29
     * study_count : 0
     */

    private String id;
    private String title;
    private String img;
    private String cid;
    private String total_count;
    private String video_count;
    private String study_count;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public String getVideo_count() {
        return video_count;
    }

    public void setVideo_count(String video_count) {
        this.video_count = video_count;
    }

    public String getStudy_count() {
        return study_count;
    }

    public void setStudy_count(String study_count) {
        this.study_count = study_count;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.img);
        dest.writeString(this.cid);
        dest.writeString(this.total_count);
        dest.writeString(this.video_count);
        dest.writeString(this.study_count);
    }

    public MyClassBean() {
    }

    protected MyClassBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.img = in.readString();
        this.cid = in.readString();
        this.total_count = in.readString();
        this.video_count = in.readString();
        this.study_count = in.readString();
    }

    public static final Parcelable.Creator<MyClassBean> CREATOR = new Parcelable.Creator<MyClassBean>() {
        @Override
        public MyClassBean createFromParcel(Parcel source) {
            return new MyClassBean(source);
        }

        @Override
        public MyClassBean[] newArray(int size) {
            return new MyClassBean[size];
        }
    };
}
