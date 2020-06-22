package com.jianpei.jpeducation.adapter.classinfo.directory;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.classinfo.DirectorySectionBean;

import org.jetbrains.annotations.NotNull;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class DirectorySectionProvider extends BaseNodeProvider {

    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_directory_section;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
        DirectorySectionBean directorySectionBean = (DirectorySectionBean) baseNode;
        baseViewHolder.setText(R.id.tv_title, directorySectionBean.getTitle());
        if ("1".equals(directorySectionBean.getIsfree())) {
            baseViewHolder.setImageResource(R.id.imageView, R.drawable.directory_trylistener);
            baseViewHolder.setTextColorRes(R.id.tv_title, R.color.c161820);
            baseViewHolder.setBackgroundResource(R.id.tv_try, R.drawable.shape_directory_trylistener);
            baseViewHolder.setText(R.id.tv_try, "试听");
            baseViewHolder.setTextColorRes(R.id.tv_try, R.color.cE73B30);
        }
    }
}
