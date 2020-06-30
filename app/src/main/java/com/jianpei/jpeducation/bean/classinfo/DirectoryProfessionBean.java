package com.jianpei.jpeducation.bean.classinfo;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class DirectoryProfessionBean extends BaseNode implements Parcelable {
    private String id;

    private String title;

    private List<DirectoryChapterBean> class_directory;

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

    public List<DirectoryChapterBean> getClass_directory() {
        return class_directory;
    }

    public void setClass_directory(ArrayList<DirectoryChapterBean> class_directory) {
        this.class_directory = class_directory;
    }


    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        List<BaseNode> baseNodes = new ArrayList<>();
        baseNodes.addAll(class_directory);
        return baseNodes;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeTypedList(this.class_directory);
    }

    public DirectoryProfessionBean() {
    }

    protected DirectoryProfessionBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.class_directory = in.createTypedArrayList(DirectoryChapterBean.CREATOR);
    }

    public static final Parcelable.Creator<DirectoryProfessionBean> CREATOR = new Parcelable.Creator<DirectoryProfessionBean>() {
        @Override
        public DirectoryProfessionBean createFromParcel(Parcel source) {
            return new DirectoryProfessionBean(source);
        }

        @Override
        public DirectoryProfessionBean[] newArray(int size) {
            return new DirectoryProfessionBean[size];
        }
    };
}
