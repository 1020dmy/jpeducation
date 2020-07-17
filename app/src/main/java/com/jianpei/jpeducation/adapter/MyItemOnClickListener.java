package com.jianpei.jpeducation.adapter;

import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/7
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface MyItemOnClickListener {

    void onItemClick(int position, View view);

    void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position);
}
