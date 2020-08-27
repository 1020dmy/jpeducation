package com.jianpei.jpeducation.adapter.tiku;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.tiku.QuestionBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/25
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class WrongListAdapter extends RecyclerView.Adapter<WrongListAdapter.MyHolder> {

    private List<QuestionBean> questionBeans;

    private MyItemOnClickListener myItemOnClickListener;

    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    public WrongListAdapter(List<QuestionBean> questionBeans) {
        this.questionBeans = questionBeans;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wrong_list, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.tvName.setText(questionBeans.get(position).getQuestion_name());

    }

    @Override
    public int getItemCount() {
        return questionBeans != null ? questionBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName;
        private TextView tv_enter, tv_jiexi;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tv_enter = itemView.findViewById(R.id.tv_enter);
            tv_jiexi = itemView.findViewById(R.id.tv_jiexi);

            tv_enter.setOnClickListener(this);
            tv_jiexi.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null) {
                myItemOnClickListener.onItemClick(getLayoutPosition(), v);
            }

        }
    }

}
