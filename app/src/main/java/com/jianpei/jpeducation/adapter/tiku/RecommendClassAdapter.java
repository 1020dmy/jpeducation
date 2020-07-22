package com.jianpei.jpeducation.adapter.tiku;

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
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/20
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class RecommendClassAdapter extends RecyclerView.Adapter<RecommendClassAdapter.MyHolder> {


    //    private List<RecommendClassBean> recommendClassBeans;
    private Context context;
    private List<GroupInfoBean> groupDataBeans;


    private MyItemOnClickListener myItemOnClickListener;


    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    public RecommendClassAdapter(List<GroupInfoBean> groupDataBeans, Context context) {
        this.groupDataBeans = groupDataBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tiku_select, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        GroupInfoBean groupInfoBean = groupDataBeans.get(position);

        Glide.with(context).load(groupInfoBean.getImg()).placeholder(R.drawable.home_icon_demo).into(holder.imageView);

        holder.tv_title.setText(groupInfoBean.getTitle());
        holder.tv_price.setText(groupInfoBean.getMin_price() + "起");
        holder.tv_nums.setText(groupInfoBean.getBuy_num() + "人报名");
        if (TextUtils.isEmpty(groupInfoBean.getCoupon_str())) {
            holder.ll_zhekou.setVisibility(View.GONE);
        } else {
            holder.tv_zhekou.setText(groupInfoBean.getCoupon_str());
        }


    }

    @Override
    public int getItemCount() {
        return groupDataBeans != null ? groupDataBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;

        private TextView tv_title, tv_price, tv_nums, tv_zhekou, tv_submit;
        private LinearLayout ll_zhekou;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tv_title = itemView.findViewById(R.id.tv_title);

            tv_price = itemView.findViewById(R.id.tv_price);
            tv_nums = itemView.findViewById(R.id.tv_nums);
            tv_zhekou = itemView.findViewById(R.id.tv_zhekou);
            tv_submit = itemView.findViewById(R.id.tv_submit);
            ll_zhekou = itemView.findViewById(R.id.ll_zhekou);

            tv_submit.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null)
                myItemOnClickListener.onItemClick(getLayoutPosition(), v);
        }
    }
}
