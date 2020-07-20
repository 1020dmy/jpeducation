package com.jianpei.jpeducation.adapter.school;

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
import com.jianpei.jpeducation.bean.school.ReplyDataBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/20
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ReplyListAdapter extends RecyclerView.Adapter<ReplyListAdapter.MyHolder> {


    private List<ReplyDataBean> mReplyDataBeans;

    private Context context;
//    private MyItemOnClickListener myItemOnClickListener;

    public ReplyListAdapter(List<ReplyDataBean> mReplyDataBeans, Context context) {
        this.mReplyDataBeans = mReplyDataBeans;
        this.context = context;
    }

//    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
//        this.myItemOnClickListener = myItemOnClickListener;
//    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ReplyDataBean replyDataBean=mReplyDataBeans.get(position);

        Glide.with(context).load(replyDataBean.getUser_img()).into(holder.civHead);
        holder.tvName.setText(replyDataBean.getUser_name());
        holder.tvTime.setText(replyDataBean.getCreated_at_str());
//        if ("1".equals(replyDataBean.getIs_praise())) {//是否点赞1是，0否
//            holder.ivDianzan.setImageResource(R.drawable.school_undianzan_icon);
//        } else {
//            holder.ivDianzan.setImageResource(R.drawable.school_dianzan_icon);
//        }
//        if ("0".equals(replyDataBean.getLike_num()))
//            holder.tvDianzanNum.setText("");
//        else {
//            holder.tvDianzanNum.setText(replyDataBean.getLike_num());
//        }
        holder.tvContent.setText(replyDataBean.getContent());

    }

    @Override
    public int getItemCount() {
        return mReplyDataBeans != null ? mReplyDataBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private CircleImageView civHead;
        private TextView tvName, tvTime;
        private ImageView ivDianzan;
        private TextView tvDianzanNum;
        private TextView tvContent;

        private LinearLayout linearLayout;
//        private TextView tv_reply;

//        private LinearLayout ll_reply;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            civHead = itemView.findViewById(R.id.civ_head);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            ivDianzan = itemView.findViewById(R.id.iv_dianzan);
            tvDianzanNum = itemView.findViewById(R.id.tv_dianzan_num);
            tvContent = itemView.findViewById(R.id.tv_content);
            linearLayout = itemView.findViewById(R.id.linearLayout);
//            tv_reply = itemView.findViewById(R.id.tv_reply);

//            ll_reply = itemView.findViewById(R.id.ll_reply);


            linearLayout.setVisibility(View.GONE);
            ivDianzan.setVisibility(View.GONE);
            tvDianzanNum.setVisibility(View.GONE);
//            ivDianzan.setOnClickListener(this);
//            ll_reply.setOnClickListener(this);
//            linearLayout.setOnClickListener(this);


        }

//        @Override
//        public void onClick(View v) {
//            if (myItemOnClickListener != null)
//                myItemOnClickListener.onItemClick(getLayoutPosition(), v);
//
//
//        }
    }
}
