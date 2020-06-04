package com.jianpei.jpeducation.adapter.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.binder.BaseItemBinder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.homedata.RegimentTitleBean;

import org.jetbrains.annotations.NotNull;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/3
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class RegimentTitleItemBinder extends BaseItemBinder<RegimentTitleBean, RegimentTitleItemBinder.MyHolder> {

    @Override
    public void convert(@NotNull MyHolder myHolder, RegimentTitleBean regimentTitleBean) {

        myHolder.tvTitle.setText(regimentTitleBean.getTitle());
        myHolder.tvTip.setText(regimentTitleBean.getSubtitle());


    }

    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_home_two, viewGroup, false);
        return new MyHolder(view);
    }

    class MyHolder extends BaseViewHolder {
        private TextView tvTitle, tvTip, tvTime;

        public MyHolder(@NotNull View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            tvTip = view.findViewById(R.id.tv_tip);
            tvTime = view.findViewById(R.id.tv_time);
        }
    }
}
