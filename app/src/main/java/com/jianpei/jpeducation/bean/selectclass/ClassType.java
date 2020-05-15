package com.jianpei.jpeducation.bean.selectclass;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/13
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ClassType extends BaseExpandNode {
    private List<BaseNode> childNode;
    private String title;

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }

    public ClassType(List<BaseNode> childNode, String title) {
        this.childNode = childNode;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
