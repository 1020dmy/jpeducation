package com.jianpei.jpeducation.bean.classinfo;

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
public class DirectoryProfessionBean extends BaseNode {
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
}
