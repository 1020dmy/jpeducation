package com.jianpei.jpeducation.adapter.order;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
public class OrderInfoAdapter extends RecyclerView.Adapter<OrderInfoAdapter.MyHolder> {


    private List<GroupBean> group_list;
    private Context mContext;


    private Context context;

    private GroupInfoBean groupInfoBean;
    private String class_name_str, price, is_material, material_des;

    public OrderInfoAdapter(List<GroupBean> group_list, Context mContext) {
        this.group_list = group_list;
        this.mContext = mContext;
    }

    public OrderInfoAdapter(Context context, GroupInfoBean groupInfoBean, String class_name_str, String price, String is_material, String material_des) {
        this.context = context;
        this.groupInfoBean = groupInfoBean;
        this.class_name_str = class_name_str;
        this.price = price;
        this.is_material = is_material;
        this.material_des = material_des;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shoppingcat_commodity, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        if (group_list != null) {
            GroupBean groupBean = group_list.get(position);
            Glide.with(mContext).load(groupBean.getImg()).into(holder.ivHead);
            holder.tvTitle.setText(groupBean.getTitle());
            holder.tvClassinfo.setText(groupBean.getClass_name_str());
            holder.tvPrice.setText("￥" + groupBean.getPrice());
            if ("1".equals(groupBean.getIs_material())) {
                holder.tvMaterialName.setText(groupBean.getMaterial_des());
            } else {
                holder.tvMaterialName.setVisibility(View.GONE);
                holder.tvMaterial.setVisibility(View.GONE);
                holder.tvLine.setVisibility(View.GONE);

            }
        } else {
            Glide.with(context).load(groupInfoBean.getImg()).into(holder.ivHead);
            holder.tvTitle.setText(groupInfoBean.getTitle());
            holder.tvClassinfo.setText(class_name_str);
            holder.tvPrice.setText("￥" + price);
            if ("1".equals(is_material)) {
                holder.tvMaterialName.setText(material_des);
            } else {
                holder.tvMaterialName.setVisibility(View.GONE);
                holder.tvMaterial.setVisibility(View.GONE);
                holder.tvLine.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return group_list != null ? group_list.size() : 1;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private ImageView ivHead;
        private TextView tvTitle, tvClassinfo, tvPrice, tvMaterial, tvMaterialName;
        private ImageButton ibDelete;
        private TextView tvLine;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ivHead = itemView.findViewById(R.id.iv_head);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvClassinfo = itemView.findViewById(R.id.tv_classinfo);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvMaterial = itemView.findViewById(R.id.tv_material);
            tvMaterialName = itemView.findViewById(R.id.tv_material_name);
            ibDelete = itemView.findViewById(R.id.ib_delete);
            tvLine=itemView.findViewById(R.id.tv_line);
            ibDelete.setVisibility(View.GONE);
            tvPrice.setGravity(Gravity.RIGHT);

        }
    }
}
