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
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.school.EvaluationDataBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/18
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyHolder> {
    private List<EvaluationDataBean> evaluationDataBeans;

    private Context context;

    private MyItemOnClickListener myItemOnClickListener;

    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    public CommentAdapter(List<EvaluationDataBean> evaluationDataBeans, Context context) {
        this.evaluationDataBeans = evaluationDataBeans;
        this.context = context;
    }

    @NonNull
    @Override

    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        EvaluationDataBean evaluationDataBean = evaluationDataBeans.get(position);

        Glide.with(context).load(evaluationDataBean.getUser_img()).into(holder.civHead);
        holder.tvName.setText(evaluationDataBean.getUser_name());
        holder.tvTime.setText(evaluationDataBean.getCreated_at_str());
        if ("1".equals(evaluationDataBean.getIs_praise())) {//是否点赞1是，0否
            holder.ivDianzan.setImageResource(R.drawable.school_undianzan_icon);
        } else {
            holder.ivDianzan.setImageResource(R.drawable.school_dianzan_icon);
        }
        if ("0".equals(evaluationDataBean.getLike_num()))
            holder.tvDianzanNum.setText("");
        else {
            holder.tvDianzanNum.setText(evaluationDataBean.getLike_num());
        }
        holder.tvContent.setText(evaluationDataBean.getContent());
        if ("0".equals(evaluationDataBean.getEvaluation_count())) {
            holder.linearLayout.setVisibility(View.GONE);
        } else {
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.tv_reply.setText("共" + evaluationDataBean.getEvaluation_count() + "条回复");
        }

    }

    @Override
    public int getItemCount() {
        return evaluationDataBeans != null ? evaluationDataBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView civHead;
        private TextView tvName, tvTime;
        private ImageView ivDianzan;
        private TextView tvDianzanNum;
        private TextView tvContent;

        private LinearLayout linearLayout;
        private TextView tv_reply;

        private LinearLayout ll_reply;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            civHead = itemView.findViewById(R.id.civ_head);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            ivDianzan = itemView.findViewById(R.id.iv_dianzan);
            tvDianzanNum = itemView.findViewById(R.id.tv_dianzan_num);
            tvContent = itemView.findViewById(R.id.tv_content);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            tv_reply = itemView.findViewById(R.id.tv_reply);

            ll_reply = itemView.findViewById(R.id.ll_reply);

            ivDianzan.setOnClickListener(this);
            ll_reply.setOnClickListener(this);
            linearLayout.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null)
                myItemOnClickListener.onItemClick(getLayoutPosition(), v);


        }
    }
}
