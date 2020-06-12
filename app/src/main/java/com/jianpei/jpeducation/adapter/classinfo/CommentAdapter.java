package com.jianpei.jpeducation.adapter.classinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.CommentBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/11
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyHolder> {

    private List<CommentBean> commentBeans;
    private Context context;

    public CommentAdapter(List<CommentBean> commentBeans, Context context) {
        this.commentBeans = commentBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_infoclass_comment, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        CommentBean commentBean = commentBeans.get(position);

        Glide.with(context).load(commentBean.getImg()).into(holder.cimHead);
        holder.tvName.setText(commentBean.getUser_name());
        holder.tvContent.setText(commentBean.getContent());
        holder.tvTime.setText(commentBean.getCreate_time_str());

    }

    @Override
    public int getItemCount() {
        return commentBeans != null ? commentBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private CircleImageView cimHead;
        private TextView tvName, tvContent, tvTime;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            cimHead = itemView.findViewById(R.id.cim_head);

            tvName = itemView.findViewById(R.id.tv_name);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvTime = itemView.findViewById(R.id.tv_time);

        }
    }
}
