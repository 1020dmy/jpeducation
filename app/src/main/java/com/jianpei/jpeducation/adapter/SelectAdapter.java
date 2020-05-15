package com.jianpei.jpeducation.adapter;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.jianpei.jpeducation.adapter.provider.RootNodeProvider;
import com.jianpei.jpeducation.adapter.provider.SecondNodeProvider;
import com.jianpei.jpeducation.bean.selectclass.ClassName;
import com.jianpei.jpeducation.bean.selectclass.ClassType;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SelectAdapter extends BaseNodeAdapter {


    public SelectAdapter() {
        super();
        addFullSpanNodeProvider(new RootNodeProvider());
        addNodeProvider(new SecondNodeProvider());
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {
        BaseNode node = list.get(i);
        if (node instanceof ClassType) {
            return 0;
        } else if (node instanceof ClassName) {
            return 1;
        }
        return -1;
    }
}
