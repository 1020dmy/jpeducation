package com.jianpei.jpeducation.adapter.school;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.school.TopicBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/20
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class HotTopicAdapter extends RecyclerView.Adapter<HotTopicAdapter.MyHolder> {
    private List<TopicBean> topicBeans;

    public HotTopicAdapter(List<TopicBean> topicBeans) {
        this.topicBeans = topicBeans;
    }

    private MyItemOnClickListener myItemOnClickListener;

    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topictitle, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        TopicBean topicBean = topicBeans.get(position);

        holder.tv_topic.setText(topicBean.getTitle());

    }

    @Override
    public int getItemCount() {
        return topicBeans != null ? topicBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout ll_topic;
        private TextView tv_topic;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ll_topic = itemView.findViewById(R.id.ll_topic);
            tv_topic = itemView.findViewById(R.id.tv_topic);
            ll_topic.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null)
                myItemOnClickListener.onItemClick(getLayoutPosition(), v);

        }
    }
}
