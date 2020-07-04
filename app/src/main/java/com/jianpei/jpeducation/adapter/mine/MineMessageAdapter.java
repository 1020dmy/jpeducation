package com.jianpei.jpeducation.adapter.mine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MineMessageAdapter extends RecyclerView.Adapter<MineMessageAdapter.MyHolder> {


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvTime, tvContent, tvBtn;

        private CircleImageView civHead;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvTime = itemView.findViewById(R.id.tv_time);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvBtn = itemView.findViewById(R.id.tv_btn);
            civHead = itemView.findViewById(R.id.civ_head);

        }
    }
}
