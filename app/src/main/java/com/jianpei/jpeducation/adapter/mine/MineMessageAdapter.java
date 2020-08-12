package com.jianpei.jpeducation.adapter.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.mine.MessageBean;

import java.util.List;

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


    private List<MessageBean> messageBeans;
    private Context context;

    public MineMessageAdapter(List<MessageBean> messageBeans, Context context) {
        this.messageBeans = messageBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        MessageBean messageBean = messageBeans.get(position);
        Glide.with(context).load(messageBean.getImg()).placeholder(R.drawable.head_icon).into(holder.civHead);
        holder.tvTime.setText(messageBean.getTime_str());
        holder.tvContent.setText(messageBean.getContent());


    }

    @Override
    public int getItemCount() {
        return messageBeans != null ? messageBeans.size() : 0;
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
