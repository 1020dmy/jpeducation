package com.jianpei.jpeducation.adapter.offlineclass;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.module.DraggableModule;
import com.jianpei.jpeducation.bean.offlineclass.OfflineClassContentBean;
import com.jianpei.jpeducation.bean.offlineclass.OfflineClassTitleBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/29
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class OfflieClassAdapter extends BaseNodeAdapter implements DraggableModule {



    public OfflieClassAdapter() {
        super();
        addNodeProvider(new OfflineClassTitleProvider());
        addNodeProvider(new OfflineClassContentProvider());

    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {
        BaseNode baseNode = list.get(i);
        if (baseNode instanceof OfflineClassTitleBean) {
            return 0;
        } else if (baseNode instanceof OfflineClassContentBean) {
            return 1;
        }
        return -1;
    }
}
