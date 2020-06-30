package com.jianpei.jpeducation.bean.classinfo;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
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
public class DirectoryChapterBean extends BaseExpandNode implements Parcelable {

    private String id;
    private String title;

    private List<BaseNode> baseNodes;

    public DirectoryChapterBean() {
        setExpanded(false);
    }

    //    private List<DirectorySectionBean> dir;


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

//    public List<DirectorySectionBean> getDir() {
//        return dir;
//    }

//    public void setDir(List<DirectorySectionBean> dir) {
//        this.dir = dir;
//    }


    public List<BaseNode> getBaseNodes() {
        if(baseNodes==null){
            baseNodes=new ArrayList<>();
        }
        return baseNodes;
    }

    public void setBaseNodes(List<BaseNode> baseNodes) {
        this.baseNodes = baseNodes;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return getBaseNodes();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeList(this.baseNodes);
    }

    protected DirectoryChapterBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.baseNodes = new ArrayList<BaseNode>();
        in.readList(this.baseNodes, BaseNode.class.getClassLoader());
    }

    public static final Parcelable.Creator<DirectoryChapterBean> CREATOR = new Parcelable.Creator<DirectoryChapterBean>() {
        @Override
        public DirectoryChapterBean createFromParcel(Parcel source) {
            return new DirectoryChapterBean(source);
        }

        @Override
        public DirectoryChapterBean[] newArray(int size) {
            return new DirectoryChapterBean[size];
        }
    };
}
