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
import com.jianpei.jpeducation.activitys.classinfo.ClassInfoActivity;
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
        myHolder.tv_price.setText(groupInfoBean.getPrice_str());
    }

    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_five, viewGroup, false);
        return new MyHolder(view);
    }

    class MyHolder extends BaseViewHolder {
        private ImageView imageView;
        private TextView tvTitle, tvZhekou, tvNums, tvSubmit;
        private LinearLayout llZhekou;

        private TextView tv_price;

        public MyHolder(@NotNull View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            tvTitle = view.findViewById(R.id.tv_title);
            tvZhekou = view.findViewById(R.id.tv_zhekou);
            tvNums = view.findViewById(R.id.tv_nums);
            tvSubmit = view.findViewById(R.id.tv_submit);
            llZhekou = view.findViewById(R.id.ll_zhekou);
            tv_price = view.findViewById(R.id.tv_price);
            addChildClickViewIds(R.id.tv_submit);

        }
    }

    @Override
    public void onChildClick(@NotNull MyHolder holder, @NotNull View view, GroupInfoBean data, int position) {
        context.startActivity(new Intent(context, ClassInfoActivity.class)
                .putExtra("groupId", data.getId())
                .putExtra("catId", data.getCat_id()));

    }
}
