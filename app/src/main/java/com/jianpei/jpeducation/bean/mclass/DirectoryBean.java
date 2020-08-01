package com.jianpei.jpeducation.bean.mclass;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/13
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
@Entity(tableName = "directory")
public class DirectoryBean extends BaseExpandNode {
    @NonNull
    @PrimaryKey
    private String id;
    private String title;
    private String schedule;
    @Ignore
    private List<ViodBean> viods;
    @Ignore
    private List<BaseNode> baseNodes;

    public DirectoryBean() {
        setExpanded(false);
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
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

    public List<ViodBean> getViods() {
        return viods;
    }

    public void setViods(List<ViodBean> viods) {
        this.viods = viods;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        if (baseNodes == null) {
            baseNodes = new ArrayList<>();
            baseNodes.addAll(viods);
        }
        return baseNodes;
    }
}
