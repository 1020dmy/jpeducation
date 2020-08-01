package com.jianpei.jpeducation.adapter.material;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.material.MaterialInfoBean;
import com.jianpei.jpeducation.bean.material.MaterialTitle;

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

    public MaterialAdapter(MyItemOnClickListener myItemOnClickListener) {
        super();
        addNodeProvider(new MaterialTitleProvider(myItemOnClickListener));
        addNodeProvider(new MaterialInfoProvider(myItemOnClickListener));

    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {
        BaseNode baseNode = list.get(i);
        if (baseNode instanceof MaterialTitle) {
            return 0;
        } else if (baseNode instanceof MaterialInfoBean)
            return 1;
        return -1;
    }
}
