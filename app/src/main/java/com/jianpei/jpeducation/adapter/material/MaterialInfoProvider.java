package com.jianpei.jpeducation.adapter.material;

import android.view.View;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.homedata.MaterialInfoBean;

import org.jetbrains.annotations.NotNull;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/8
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MaterialInfoProvider extends BaseNodeProvider {


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
//        MaterialInfoBean materialInfoBean = (MaterialInfoBean) baseNode;
//        baseViewHolder.setText(R.id.tv_title, materialInfoBean.getTitle());
//        baseViewHolder.setText(R.id.tv_nums, materialInfoBean.getDownload() + "次下载");
//        baseViewHolder.setText(R.id.tv_down, materialInfoBean.getStatus());
//        if ("1".equals(materialInfoBean.getStatus())) {
//            baseViewHolder.setText(R.id.tv_down, "正在下载");
//            ProgressBar progressBar = baseViewHolder.getView(R.id.progressBar);
//            progressBar.setVisibility(View.VISIBLE);
//            progressBar.setProgress(materialInfoBean.getProgress());
//        } else if ("2".equals(materialInfoBean.getStatus())) {
//            baseViewHolder.setText(R.id.tv_down, "下载完成");
//            ProgressBar progressBar = baseViewHolder.getView(R.id.progressBar);
//            progressBar.setVisibility(View.VISIBLE);
//            progressBar.setProgress(materialInfoBean.getProgress());
//        } else {
//            baseViewHolder.setText(R.id.tv_down, "下载");
//            baseViewHolder.setVisible(R.id.progressBar, false);
//
//        }

    }

}
