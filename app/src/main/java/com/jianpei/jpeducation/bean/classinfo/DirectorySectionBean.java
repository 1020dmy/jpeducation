package com.jianpei.jpeducation.bean.classinfo;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class DirectorySectionBean extends BaseNode implements Parcelable {


//    /**
//     * id : 4674
//     * title : 2019备考导学01
//     * isfree : 1
//     */
//
//    private String id;
//    private String title;
//    private String isfree;
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getIsfree() {
//        return isfree;
//    }
//
//    public void setIsfree(String isfree) {
//        this.isfree = isfree;
//    }
//
//    @Nullable
//    @Override
//    public List<BaseNode> getChildNode() {
//        return null;
//    }
    /**
     * id : 10960
     * title : 2020建筑结构02
     * isfree : 0
     * chapter_id : 1013
     * dqtime : 1042
     * totaltime : 2328
     * is_last_read : 0
     */

    private String id;
    private String title;
    private String isfree;
    private String chapter_id;
    private String dqtime;
    private String totaltime;
    private String is_last_read;

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

    public String getIsfree() {
        return isfree;
    }

    public void setIsfree(String isfree) {
        this.isfree = isfree;
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getDqtime() {
        return dqtime;
    }

    public void setDqtime(String dqtime) {
        this.dqtime = dqtime;
    }

    public String getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(String totaltime) {
        this.totaltime = totaltime;
    }

    public String getIs_last_read() {
        return is_last_read;
    }

    public void setIs_last_read(String is_last_read) {
        this.is_last_read = is_last_read;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.isfree);
        dest.writeString(this.chapter_id);
        dest.writeString(this.dqtime);
        dest.writeString(this.totaltime);
        dest.writeString(this.is_last_read);
    }

    public DirectorySectionBean() {
    }

    protected DirectorySectionBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.isfree = in.readString();
        this.chapter_id = in.readString();
        this.dqtime = in.readString();
        this.totaltime = in.readString();
        this.is_last_read = in.readString();
    }

    public static final Creator<DirectorySectionBean> CREATOR = new Creator<DirectorySectionBean>() {
        @Override
        public DirectorySectionBean createFromParcel(Parcel source) {
            return new DirectorySectionBean(source);
        }

        @Override
        public DirectorySectionBean[] newArray(int size) {
            return new DirectorySectionBean[size];
        }
    };

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }
}
