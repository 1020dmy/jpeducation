package com.jianpei.jpeducation.adapter.mclass;

import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.mclass.DirectoryBean;

import org.jetbrains.annotations.NotNull;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/13
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class DownloadClassChapterProvider extends BaseNodeProvider {

    @Override
    public int getItemViewType() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_download_class_title;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
        DirectoryBean directoryBean = (DirectoryBean) baseNode;

        baseViewHolder.setText(R.id.tv_title, directoryBean.getTitle());
        if (directoryBean.isExpanded()) {
            baseViewHolder.setImageResource(R.id.imageView, R.drawable.material_unfold);
        } else {
            baseViewHolder.setImageResource(R.id.imageView, R.drawable.material_shrink);
        }
    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        getAdapter().expandOrCollapse(position);
    }
}
