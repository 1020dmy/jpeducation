package com.jianpei.jpeducation.bean.material;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
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
 * Created by sjl on 2020/7/8
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
@Entity(tableName = "materialtitle")
public class MaterialTitle extends BaseExpandNode {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;
    private String cat_id;
    private String title;

    @Ignore
    private boolean isUnfold;


    public MaterialTitle() {
        setExpanded(false);
    }

    public boolean isUnfold() {
        return isUnfold;
    }

    public void setUnfold(boolean unfold) {
        isUnfold = unfold;
    }

//    private List<MaterialInfoBean> materialInfoBeans;
//
//    public List<MaterialInfoBean> getMaterialInfoBeans() {
//        return materialInfoBeans;
//    }
//
//    public void setMaterialInfoBeans(List<MaterialInfoBean> materialInfoBeans) {
//        this.materialInfoBeans = materialInfoBeans;
//    }

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

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    @Ignore
    private List<BaseNode> list;

    public List<BaseNode> getList() {
        if (list == null)
            list = new ArrayList<>();
        return list;
    }

    public void setList(List<BaseNode> list) {
        this.list = list;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return list;
    }
}
