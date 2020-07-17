package com.jianpei.jpeducation.adapter.school;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.school.ThreadDataBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/14
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.MyHolder> {


    private List<ThreadDataBean> threadDataBeans;
    private Context context;

    private MyItemOnClickListener myItemOnClickListener;


    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    public SchoolAdapter(List<ThreadDataBean> threadDataBeans, Context context) {
        this.threadDataBeans = threadDataBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_school, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ThreadDataBean threadDataBean = threadDataBeans.get(position);
        Glide.with(context).load(threadDataBean.getUser_img()).into(holder.civ_head);
        holder.tv_name.setText(threadDataBean.getUser_name());
        holder.tv_time.setText(threadDataBean.getCreate_time_str());
        holder.tv_content.setText(threadDataBean.getContent());
        if ("0".equals(threadDataBean.getPost_num()))
            holder.tv_message.setText("");
        else {
            holder.tv_message.setText("+" + threadDataBean.getPost_num());
        }
        if ("0".equals(threadDataBean.getLike_num()))
            holder.tv_dianzan.setText("");
        else {
            holder.tv_dianzan.setText(threadDataBean.getLike_num());
        }
        if ("1".equals(threadDataBean.getIs_my_thread())) {//是否我的帖子1是，0否
            holder.btn_status.setVisibility(View.GONE);
        }
        if ("1".equals(threadDataBean.getIs_praise())) {//是否点赞1是，0否
            holder.iv_dianzan.setImageResource(R.drawable.school_undianzan_icon);
        } else {
            holder.iv_dianzan.setImageResource(R.drawable.school_dianzan_icon);
        }
        if ("1".equals(threadDataBean.getIs_attention())) {//是否关注1是，0否
            holder.btn_status.setText("取消关注");
            holder.btn_status.setTextColor(context.getResources().getColor(R.color.cA5A7B0));
            holder.btn_status.setBackgroundResource(R.drawable.shape_selectzhuanye_item);
        } else {
            holder.btn_status.setText("关注+");
            holder.btn_status.setTextColor(context.getResources().getColor(R.color.cE73B30));
            holder.btn_status.setBackgroundResource(R.drawable.shape_selectzhuanye_itemt);
        }
        if (threadDataBean.getImages() != null && threadDataBean.getImages().size() > 0) {
            ImageListAdapter imageListAdapter = new ImageListAdapter(threadDataBean.getImages(), context);
            holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
            holder.recyclerView.setAdapter(imageListAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return threadDataBeans != null ? threadDataBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView civ_head;
        private TextView tv_name, tv_time;
        private Button btn_status;
        private TextView tv_content;
        private ImageView iv_share;
        private TextView tv_message, tv_dianzan;
        private RecyclerView recyclerView;
        private ImageView iv_dianzan;
        private LinearLayout ll_dianzan;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            civ_head = itemView.findViewById(R.id.civ_head);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            btn_status = itemView.findViewById(R.id.btn_status);
            tv_content = itemView.findViewById(R.id.tv_content);
            iv_share = itemView.findViewById(R.id.iv_share);
            tv_message = itemView.findViewById(R.id.tv_message);
            tv_dianzan = itemView.findViewById(R.id.tv_dianzan);
            recyclerView = itemView.findViewById(R.id.recyclerView);

            iv_dianzan = itemView.findViewById(R.id.iv_dianzan);
            ll_dianzan = itemView.findViewById(R.id.ll_dianzan);

            btn_status.setOnClickListener(this);
            iv_share.setOnClickListener(this);
            ll_dianzan.setOnClickListener(this);
            tv_message.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null) {
                myItemOnClickListener.onItemClick(getLayoutPosition(), v);
            }
        }
    }
}
