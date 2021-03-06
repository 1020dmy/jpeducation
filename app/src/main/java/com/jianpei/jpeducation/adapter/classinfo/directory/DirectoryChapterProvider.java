package com.jianpei.jpeducation.adapter.classinfo.directory;


import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.classinfo.DirectoryChapterBean;
import com.jianpei.jpeducation.utils.L;

import org.jetbrains.annotations.NotNull;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class DirectoryChapterProvider extends BaseNodeProvider {


    private MyItemOnClickListener myItemOnClickListener;

    public DirectoryChapterProvider(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_directory_chapter;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {

        DirectoryChapterBean directoryChapterBean = (DirectoryChapterBean) baseNode;

        baseViewHolder.setText(R.id.tv_title, directoryChapterBean.getTitle());

        if (directoryChapterBean.isExpanded()) {
            baseViewHolder.setImageResource(R.id.imageView, R.drawable.material_unfold);
        } else {
            baseViewHolder.setImageResource(R.id.imageView, R.drawable.material_shrink);
        }

    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        getAdapter().expandOrCollapse(position);
        if (myItemOnClickListener != null) {
            myItemOnClickListener.onItemClick(helper, view, data, position);
        }
    }
}
