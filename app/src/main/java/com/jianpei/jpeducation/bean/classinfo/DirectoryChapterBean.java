package com.jianpei.jpeducation.bean.classinfo;

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
public class DirectoryChapterBean extends BaseExpandNode {

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
}
