package com.jianpei.jpeducation.adapter.order;

import android.content.Context;
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
import com.jianpei.jpeducation.bean.order.GroupInfoBean;
import com.jianpei.jpeducation.bean.shop.GroupBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/3
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class OrderClassAdapter extends RecyclerView.Adapter<OrderClassAdapter.MyHolder> {


    private List<GroupBean> group_list;


    private Context context;

    private GroupInfoBean groupInfoBean;
    private String class_name_str, price;

    public OrderClassAdapter(List<GroupBean> group_list, Context context) {
        this.group_list = group_list;
        this.context = context;
    }

    public OrderClassAdapter(Context context, GroupInfoBean groupInfoBean, String class_name_str, String price) {
        this.context = context;
        this.groupInfoBean = groupInfoBean;
        this.class_name_str = class_name_str;
        this.price = price;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_class_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if (position == 0)
            holder.line.setVisibility(View.GONE);
        if (group_list != null) {
            GroupBean groupBean = group_list.get(position);
            Glide.with(context).load(groupBean.getImg()).into(holder.iv_head);
            holder.tv_title.setText(groupBean.getTitle());
            holder.tv_classinfo.setText(groupBean.getClass_name_str());
            holder.tv_price.setText("￥" + groupBean.getPrice());
        } else {
            if (groupInfoBean != null) {
                Glide.with(context).load(groupInfoBean.getImg()).into(holder.iv_head);
                holder.tv_title.setText(groupInfoBean.getTitle());
                holder.tv_classinfo.setText(class_name_str);
                holder.tv_price.setText("￥" + price);
            }
        }
    }

    @Override
    public int getItemCount() {
        return group_list != null ? group_list.size() : 1;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout llGroupInfo;
        private ImageView iv_head;
        private TextView tv_title, tv_classinfo, tv_price;
        private TextView line;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            llGroupInfo = itemView.findViewById(R.id.ll_groupInfo);
            iv_head = itemView.findViewById(R.id.iv_head);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_classinfo = itemView.findViewById(R.id.tv_classinfo);
            tv_price = itemView.findViewById(R.id.tv_price);
            line = itemView.findViewById(R.id.line);


        }

        @Override
        public void onClick(View v) {

        }
    }
}
