package com.jianpei.jpeducation.adapter.offlineclass;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.offlineclass.OfflineClassContentBean;

import org.jetbrains.annotations.NotNull;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/29
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class OfflineClassContentProvider extends BaseNodeProvider {

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_offline_class_conten;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
        OfflineClassContentBean offlineClassContentBean = (OfflineClassContentBean) baseNode;
        baseViewHolder.setText(R.id.tv_title, offlineClassContentBean.getContent());

    }
}
