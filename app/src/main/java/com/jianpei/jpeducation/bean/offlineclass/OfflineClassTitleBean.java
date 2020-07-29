package com.jianpei.jpeducation.bean.offlineclass;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/29
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class OfflineClassTitleBean extends BaseExpandNode {

    private String title;
    private String id;

    //    private List<OfflineClassContentBean> offlineClassContentBeans;
    private List<BaseNode> baseNodes;

    public OfflineClassTitleBean(String title, String id) {
        this.title = title;
        this.id = id;
        setExpanded(false);

    }
//
//    public List<OfflineClassContentBean> getOfflineClassContentBeans() {
//        return offlineClassContentBeans;
//    }
//
//    public void setOfflineClassContentBeans(List<OfflineClassContentBean> offlineClassContentBeans) {
//        this.offlineClassContentBeans = offlineClassContentBeans;
//    }


    public List<BaseNode> getBaseNodes() {
        if (baseNodes == null)
            baseNodes = new ArrayList<>();
        return baseNodes;
    }

    public void setBaseNodes(List<BaseNode> baseNodes) {
        this.baseNodes = baseNodes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return baseNodes;
    }
}
