package com.jianpei.jpeducation.bean.material;

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
public class MaterialTitle extends BaseExpandNode {


    private String title;
    private String id;
    private String cat_id;

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

    private List<MaterialInfoBean> materialInfoBeans;

    public List<MaterialInfoBean> getMaterialInfoBeans() {
        return materialInfoBeans;
    }

    public void setMaterialInfoBeans(List<MaterialInfoBean> materialInfoBeans) {
        this.materialInfoBeans = materialInfoBeans;
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

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }


    private List<BaseNode> list;

    public List<BaseNode> getList() {
        return list;
    }

    public void setList(List<BaseNode> list) {
        this.list = list;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        if (list == null)
            list = new ArrayList<>();
        return list;
    }
}
