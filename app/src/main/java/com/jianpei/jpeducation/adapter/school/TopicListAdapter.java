package com.jianpei.jpeducation.adapter.school;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.school.TopicBean;

import java.util.ArrayList;
import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class TopicListAdapter extends RecyclerView.Adapter {

    private List<TopicBean> topicBeans;
    private MyCheckBoxClickListener myCheckBoxClickListener;

    public void setMyCheckBoxClickListener(MyCheckBoxClickListener myCheckBoxClickListener) {
        this.myCheckBoxClickListener = myCheckBoxClickListener;
    }

    public TopicListAdapter(List<TopicBean> topicBeans) {
        this.topicBeans = topicBeans;
    }

    @Override
    public int getItemViewType(int position) {
        if (topicBeans.get(position).getType() == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_type, parent, false);
            viewHolder = new MyHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false);
            viewHolder = new MyHolderT(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TopicBean topicBean = topicBeans.get(position);
        if (holder instanceof MyHolder) {
            MyHolder myHolder = (MyHolder) holder;
            myHolder.tvTitle.setText(topicBean.getTitle());
        } else {
            MyHolderT myHolderT = (MyHolderT) holder;
            myHolderT.tvTitle.setText(topicBean.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return topicBeans != null ? topicBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }

    class MyHolderT extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        private TextView tvTitle;
        private CheckBox checkBox;
        private TextView tvLine;

        public MyHolderT(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            checkBox = itemView.findViewById(R.id.checkBox);
            tvLine = itemView.findViewById(R.id.tv_line);
            checkBox.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (myCheckBoxClickListener!=null){
                    myCheckBoxClickListener.onCheckClick(getAdapterPosition(),buttonView,isChecked);
                }
//            if (selectTopicBean.size() == 5) {
//                buttonView.setChecked(false);
//                return;
//            }
//            topicBeans.get(getLayoutPosition()).setSelect(isChecked);
//            if (isChecked == true) {
//                selectTopicBean.add(topicBeans.get(getLayoutPosition()));
//            } else {
//                selectTopicBean.remove(topicBeans.get(getLayoutPosition()));
//            }
//
//            L.e("size:" + selectTopicBean.size());

        }
    }

    public interface MyCheckBoxClickListener {
        void onCheckClick(int position, CompoundButton buttonView, boolean isChecked);
    }
}
