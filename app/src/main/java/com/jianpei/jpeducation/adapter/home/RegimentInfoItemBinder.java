package com.jianpei.jpeducation.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.binder.BaseItemBinder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.classinfo.GroupInfoActivity;
import com.jianpei.jpeducation.bean.homedata.RegimentInfoBean;

import org.jetbrains.annotations.NotNull;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/3
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class RegimentInfoItemBinder extends BaseItemBinder<RegimentInfoBean, RegimentInfoItemBinder.MyHolder> {


    private Context context;

    public RegimentInfoItemBinder(Context context) {
        this.context = context;
    }

    @Override
    public void convert(@NotNull MyHolder myHolder, RegimentInfoBean regimentInfoBean) {
        Glide.with(context).load(regimentInfoBean.getImg()).placeholder(R.drawable.home_icon_demo).into(myHolder.imageView);
        myHolder.tvTitle.setText(regimentInfoBean.getTitle());
        myHolder.tvPrice.setText("￥" + regimentInfoBean.getPrice());
        myHolder.tvNums.setText(regimentInfoBean.getNum_people() + "人团");

    }

    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_three, viewGroup, false);
        return new MyHolder(view);
    }

    class MyHolder extends BaseViewHolder {
        private ImageView imageView;
        private TextView tvTitle, tvPrice, tvNums;

        public MyHolder(@NotNull View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            tvTitle = view.findViewById(R.id.tv_title);
            tvPrice = view.findViewById(R.id.tv_price);
            tvNums = view.findViewById(R.id.tv_nums);
        }
    }

    @Override
    public void onClick(@NotNull MyHolder holder, @NotNull View view, RegimentInfoBean data, int position) {
        context.startActivity(new Intent(context, GroupInfoActivity.class)
                .putExtra("pointId", data.getPoint_id())
                .putExtra("id", data.getId()));
    }


}
