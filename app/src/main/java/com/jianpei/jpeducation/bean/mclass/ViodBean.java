package com.jianpei.jpeducation.bean.mclass;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/13
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */

@Entity(tableName = "viod")
public class ViodBean extends BaseNode {


    /**
     * id : 286
     * title : 建筑构造01
     * isfree : 0
     * chapter_id : 146
     * dqtime :
     * totaltime :
     * is_last_read : 0
     * schedule : 0.00
     */
    @NonNull
    @PrimaryKey
    private String id;
    private String title;
    private String isfree;
    private String chapter_id;
    private String dqtime;
    private String totaltime;
    private String is_last_read;
    private String schedule;



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

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }


    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }


}
