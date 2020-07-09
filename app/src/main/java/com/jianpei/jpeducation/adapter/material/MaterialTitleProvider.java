package com.jianpei.jpeducation.adapter.material;


import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.material.MaterialTitle;

import org.jetbrains.annotations.NotNull;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/8
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MaterialTitleProvider extends BaseNodeProvider {


    private MaterialTitleOnClickListener materialTitleOnClickListener;

    public void setMaterialTitleOnClickListener(MaterialTitleOnClickListener materialTitleOnClickListener) {
        this.materialTitleOnClickListener = materialTitleOnClickListener;
    }

    @Override
    public int getItemViewType() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_material_title_two;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
        MaterialTitle materialTitle = (MaterialTitle) baseNode;
        baseViewHolder.setText(R.id.tv_title, materialTitle.getTitle());

        if (materialTitle.isExpanded()) {
            baseViewHolder.setImageResource(R.id.imageView, R.drawable.material_unfold);
        } else {
            baseViewHolder.setImageResource(R.id.imageView, R.drawable.material_shrink);
        }
    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        getAdapter().expandOrCollapse(position);
        if (materialTitleOnClickListener != null) {
            materialTitleOnClickListener.materialTitleOnClick(helper, view, data, position);
        }

    }

    public interface MaterialTitleOnClickListener {
        void materialTitleOnClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position);
    }

}
