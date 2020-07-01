package com.jianpei.jpeducation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;

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

    }

    @Override
    public int getItemCount() {
        return 3;
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
                myItemDeleteListener.onDeleteClick(getLayoutPosition(), "");
            }

        }
    }

    public interface MyItemDeleteListener {
        void onDeleteClick(int position, String aaa);
    }
}
