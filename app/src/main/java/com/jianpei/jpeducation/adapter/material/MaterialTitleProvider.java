package com.jianpei.jpeducation.adapter.material;

import android.view.View;
import android.widget.ImageView;


import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.MaterialDataBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/8
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MaterialTitleProvider extends BaseNodeProvider {

    @Override
    public int getItemViewType() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_material_title;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
//        MaterialDataBean.MaterialTitle materialTitleBean = (MaterialDataBean.MaterialTitle) baseNode;
//        baseViewHolder.setText(R.id.tv_title, materialTitleBean.getTitle());
////        setArrowSpin(baseViewHolder, materialTitleBean);
//
//
//        if (materialTitleBean.isExpanded()) {
//            baseViewHolder.setImageResource(R.id.imageView, R.drawable.material_unfold);
//        } else {
//            baseViewHolder.setImageResource(R.id.imageView, R.drawable.material_shrink);
//
//        }

    }
//    @Override
//    public void convert(@NotNull BaseViewHolder helper, @NotNull BaseNode data, @NotNull List<?> payloads) {
//        for (Object payload : payloads) {
//            if (payload instanceof Integer && (int) payload == MaterialAdapter.EXPAND_COLLAPSE_PAYLOAD) {
//                // 增量刷新，使用动画变化箭头
//                MaterialTitleBean materialTitleBean = (MaterialTitleBean) data;
//
//                setArrowSpin(helper, materialTitleBean);
//            }
//        }
//    }





    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

    }


//    private void setArrowSpin(BaseViewHolder helper, MaterialDataBean.MaterialTitle entity) {
//
//        ImageView imageView = helper.getView(R.id.imageView);
//        if (entity.isExpanded()) {
//            imageView.setImageResource(R.drawable.material_unfold);
//        } else {
//            imageView.setImageResource(R.drawable.material_shrink);
//
//        }
//    }


}
