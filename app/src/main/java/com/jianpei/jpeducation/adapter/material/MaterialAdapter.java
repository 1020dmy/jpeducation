package com.jianpei.jpeducation.adapter.material;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.jianpei.jpeducation.bean.MaterialDataBean;
import com.jianpei.jpeducation.bean.homedata.MaterialInfoBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/8
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MaterialAdapter extends BaseNodeAdapter {

    public MaterialAdapter() {
        super();
//        addNodeProvider(new MaterialTitleProvider());
//        addNodeProvider(new MaterialInfoProvider());
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {
//        BaseNode node = list.get(i);
//        if (node instanceof MaterialDataBean.MaterialTitle) {
//            return 0;
//        } else if (node instanceof MaterialInfoBean) {
//            return 1;
//        }
        return -1;
    }
}
