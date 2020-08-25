package com.jianpei.jpeducation.adapter.classinfo.directory;

import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.classinfo.DirectorySectionBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;

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

    private MyItemOnClickListener myItemOnClickListener;

    public DirectorySectionProvider(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
        addChildClickViewIds(R.id.tv_try);
    }

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
        ViodBean viodBean = (ViodBean) baseNode;
        baseViewHolder.setText(R.id.tv_title, viodBean.getTitle());
        if ("1".equals(viodBean.getIsfree())) {
            baseViewHolder.setImageResource(R.id.imageView, R.drawable.directory_trylistener);
            baseViewHolder.setTextColorRes(R.id.tv_title, R.color.c161820);
            baseViewHolder.setBackgroundResource(R.id.tv_try, R.drawable.shape_directory_trylistener);
            baseViewHolder.setText(R.id.tv_try, "试听");
            baseViewHolder.setTextColorRes(R.id.tv_try, R.color.cE73B30);
        }
    }

    @Override
    public void onChildClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        if (myItemOnClickListener != null)
            myItemOnClickListener.onItemClick(helper, view, data, position);
    }
}
