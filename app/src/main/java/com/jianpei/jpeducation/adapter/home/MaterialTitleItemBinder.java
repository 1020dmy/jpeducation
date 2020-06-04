package com.jianpei.jpeducation.adapter.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.binder.BaseItemBinder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.homedata.MaterialTitleBean;

import org.jetbrains.annotations.NotNull;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/3
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MaterialTitleItemBinder extends BaseItemBinder<MaterialTitleBean, MaterialTitleItemBinder.MyHolder> {

    @Override
    public void convert(@NotNull MyHolder myHolder, MaterialTitleBean materialTitleBean) {
        myHolder.tvTitle.setText(materialTitleBean.getTitle());
        myHolder.tvTip.setText(materialTitleBean.getSubtitle());
    }

    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_four, viewGroup, false);
        return new MyHolder(view);
    }

    class MyHolder extends BaseViewHolder {
        private TextView tvTitle, tvTip, tvMore;

        public MyHolder(@NotNull View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            tvTip = view.findViewById(R.id.tv_tip);
            tvMore = view.findViewById(R.id.tv_more);
        }
    }
}
