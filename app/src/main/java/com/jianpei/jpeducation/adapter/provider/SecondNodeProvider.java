package com.jianpei.jpeducation.adapter.provider;

import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;

import org.jetbrains.annotations.NotNull;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/13
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SecondNodeProvider extends BaseNodeProvider {

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_selectadapter_second;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {

    }


}
