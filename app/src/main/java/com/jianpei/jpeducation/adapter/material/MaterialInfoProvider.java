package com.jianpei.jpeducation.adapter.material;

import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.material.MaterialInfoBean;

import org.jetbrains.annotations.NotNull;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/8
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MaterialInfoProvider extends BaseNodeProvider {


    private MyItemOnClickListener myItemOnClickListener;

    public MaterialInfoProvider(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
        addChildClickViewIds(R.id.tv_down);
    }

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_material_info;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
        MaterialInfoBean materialInfoBean = (MaterialInfoBean) baseNode;
        baseViewHolder.setText(R.id.tv_title, materialInfoBean.getTitle());
        baseViewHolder.setText(R.id.tv_nums, materialInfoBean.getTotal() + "人报名");
//        if ("2".equals(materialInfoBean.getStatus()))
//            baseViewHolder.setText(R.id.tv_down, "查看");
//        else if ("3".equals(materialInfoBean.getStatus())) {
//            baseViewHolder.setText(R.id.tv_down, "重新下载");
//        }

    }


    @Override
    public void onChildClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        if (myItemOnClickListener != null)
            myItemOnClickListener.onItemClick(helper, view, data, position);
    }

}
