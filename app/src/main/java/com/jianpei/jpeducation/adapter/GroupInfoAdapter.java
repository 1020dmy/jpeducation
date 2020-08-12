package com.jianpei.jpeducation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.classinfo.RegimentBean;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/11
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class GroupInfoAdapter extends RecyclerView.Adapter<GroupInfoAdapter.MyHolder> {


    private RegimentBean mRegimentBean;
    private Context context;

    public GroupInfoAdapter(RegimentBean mRegimentBean, Context context) {
        this.mRegimentBean = mRegimentBean;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_head, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if (position <= mRegimentBean.getRegiment_data().size() - 1) {
            RegimentBean data = mRegimentBean.getRegiment_data().get(position);
            if ("1".equals(data.getIs_source())) {
                holder.tvLeader.setVisibility(View.VISIBLE);
            }
            Glide.with(context).load(data.getImg()).placeholder(R.drawable.icon_no_member).into(holder.circleImageView);
        }
    }

    @Override
    public int getItemCount() {
        return Integer.valueOf(mRegimentBean.getNum_people());
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private CircleImageView circleImageView;
        private TextView tvLeader;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.civ_head);
            tvLeader = itemView.findViewById(R.id.tv_leader);
        }
    }
}
