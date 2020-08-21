package com.jianpei.jpeducation.adapter.school;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.school.TopicBean;
import com.jianpei.jpeducation.utils.L;

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


    private ArrayList<TopicBean> selectTopicBeans;

    private MyItemClickListener myItemClickListener;

    public void setMyItemClickListener(MyItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;
    }

    public ArrayList<TopicBean> getSelectTopicBeans() {
        return selectTopicBeans;
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
            myHolderT.checkBox.setChecked(topicBean.isSelect());
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

    class MyHolderT extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle;
        private CheckBox checkBox;
        private TextView tvLine;

        private RelativeLayout relativeLayout;

        public MyHolderT(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            checkBox = itemView.findViewById(R.id.checkBox);
            tvLine = itemView.findViewById(R.id.tv_line);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            relativeLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (selectTopicBeans == null)
                selectTopicBeans = new ArrayList<>();
            TopicBean topicBean = topicBeans.get(getLayoutPosition());
            if (!topicBean.isSelect() && selectTopicBeans.size() >= 5) {
                if (myItemClickListener != null)
                    myItemClickListener.onClick("最多可选择5个话题");
                return;
            }
            topicBean.setSelect(!topicBean.isSelect());
            notifyItemChanged(getLayoutPosition());
            if (topicBean.isSelect()) {
                selectTopicBeans.add(topicBean);
            } else {
                selectTopicBeans.remove(topicBean);
            }

        }
    }

    public interface MyItemClickListener {
        void onClick(String message);
    }

}
