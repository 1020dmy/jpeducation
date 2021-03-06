package com.jianpei.jpeducation.adapter.classinfo.directory;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.classinfo.DirectoryProfessionBean;

import org.jetbrains.annotations.NotNull;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class DirectoryProfessionProvider extends BaseNodeProvider {

    @Override
    public int getItemViewType() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_directory_profession;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {

        DirectoryProfessionBean directoryProfessionBean = (DirectoryProfessionBean) baseNode;
        baseViewHolder.setText(R.id.tv_title, directoryProfessionBean.getTitle());

    }
}
