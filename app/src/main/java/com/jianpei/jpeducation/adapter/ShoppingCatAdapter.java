package com.jianpei.jpeducation.adapter;

import android.content.Context;
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
import com.jianpei.jpeducation.bean.shop.GroupBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/1
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ShoppingCatAdapter extends RecyclerView.Adapter<ShoppingCatAdapter.MyHolder> {


    private MyItemDeleteListener myItemDeleteListener;

    private List<GroupBean> groupBeans;

    private Context mContext;

    public ShoppingCatAdapter(List<GroupBean> groupBeans,Context context) {
        this.groupBeans = groupBeans;
        this.mContext=context;
    }

    public void setMyItemDeleteListener(MyItemDeleteListener myItemDeleteListener) {
        this.myItemDeleteListener = myItemDeleteListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shoppingcat_commodity, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        GroupBean groupBean=groupBeans.get(position);

        Glide.with(mContext).load(groupBean.getImg()).into(holder.ivHead);
        holder.tvTitle.setText(groupBean.getTitle());
        holder.tvClassinfo.setText(groupBean.getClass_name_str());
        holder.tvPrice.setText("￥"+groupBean.getPrice());
        if("1".equals(groupBean.getIs_material())){
            holder.tvMaterialName.setText(groupBean.getMaterial_des());
        }else{
            holder.tvMaterialName.setVisibility(View.GONE);
            holder.tvMaterial.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        return groupBeans!=null ? groupBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivHead;
        private TextView tvTitle, tvClassinfo, tvPrice, tvMaterial, tvMaterialName;
        private ImageButton ibDelete;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ivHead = itemView.findViewById(R.id.iv_head);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvClassinfo = itemView.findViewById(R.id.tv_classinfo);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvMaterial = itemView.findViewById(R.id.tv_material);
            tvMaterialName = itemView.findViewById(R.id.tv_material_name);
            ibDelete = itemView.findViewById(R.id.ib_delete);
            ibDelete.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (myItemDeleteListener != null) {
                myItemDeleteListener.onDeleteClick(getLayoutPosition(), groupBeans.get(getLayoutPosition()).getCar_id());
            }

        }
    }

    public interface MyItemDeleteListener {
        void onDeleteClick(int position, String carId);
    }
}
