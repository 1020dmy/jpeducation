package com.jianpei.jpeducation.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class TryListenerListAdapter extends RecyclerView.Adapter<TryListenerListAdapter.MyHolder> {


    private List<GroupInfoBean> groupInfoBeans;
    private Context context;

    private MyItemOnClickListener myItemOnClickListener;

    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    public TryListenerListAdapter(List<GroupInfoBean> groupInfoBeans, Context context) {
        this.groupInfoBeans = groupInfoBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_five, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        GroupInfoBean groupInfoBean = groupInfoBeans.get(position);
        Glide.with(context).load(groupInfoBean.getImg()).placeholder(R.drawable.home_icon_demo).into(holder.imageView);
        holder.tvTitle.setText(groupInfoBean.getTitle());
        if (TextUtils.isEmpty(groupInfoBean.getCoupon_str())) {
            holder.llZhekou.setVisibility(View.GONE);
        } else {
            holder.tvZhekou.setText(groupInfoBean.getCoupon_str());
        }
        holder.tvNums.setText(groupInfoBean.getBuy_num() + "人报名");

    }

    @Override
    public int getItemCount() {
        return groupInfoBeans != null ? groupInfoBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView tvTitle, tvZhekou, tvNums, tvSubmit;
        private LinearLayout llZhekou;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvZhekou = itemView.findViewById(R.id.tv_zhekou);
            tvNums = itemView.findViewById(R.id.tv_nums);
            tvSubmit = itemView.findViewById(R.id.tv_submit);
            llZhekou = itemView.findViewById(R.id.ll_zhekou);
            tvSubmit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null) {
                myItemOnClickListener.onItemClick(getLayoutPosition(), v);
            }

        }
    }
}
