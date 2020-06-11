package com.jianpei.jpeducation.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.binder.BaseItemBinder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.ClassInfoActivity;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;

import org.jetbrains.annotations.NotNull;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/3
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class GroupInfoItemBinder extends BaseItemBinder<GroupInfoBean, GroupInfoItemBinder.MyHolder> {


    private Context context;

    public GroupInfoItemBinder(Context context) {
        this.context = context;
    }

    @Override
    public void convert(@NotNull MyHolder myHolder, GroupInfoBean groupInfoBean) {
        Glide.with(context).load(groupInfoBean.getImg()).placeholder(R.drawable.home_icon_demo).into(myHolder.imageView);
        myHolder.tvTitle.setText(groupInfoBean.getTitle());
        if (TextUtils.isEmpty(groupInfoBean.getCoupon_str())) {
            myHolder.llZhekou.setVisibility(View.GONE);
        } else {
            myHolder.tvZhekou.setText(groupInfoBean.getCoupon_str());
        }
        myHolder.tvNums.setText(groupInfoBean.getBuy_num() + "人报名");
    }

    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_five, viewGroup, false);
        return new MyHolder(view);
    }

    class MyHolder extends BaseViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView tvTitle, tvZhekou, tvNums, tvSubmit;
        private LinearLayout llZhekou;

        public MyHolder(@NotNull View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            tvTitle = view.findViewById(R.id.tv_title);
            tvZhekou = view.findViewById(R.id.tv_zhekou);
            tvNums = view.findViewById(R.id.tv_nums);
            tvSubmit = view.findViewById(R.id.tv_submit);
            llZhekou = view.findViewById(R.id.ll_zhekou);
            tvSubmit.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(context, ClassInfoActivity.class));

        }
    }
}