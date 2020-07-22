package com.jianpei.jpeducation.adapter.tiku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.tiku.CardBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/22
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class AnswerCardAdapter extends RecyclerView.Adapter<AnswerCardAdapter.MyHolder> {

    private List<CardBean> cardBeans;
    private Context context;

    public AnswerCardAdapter(List<CardBean> cardBeans, Context context) {
        this.cardBeans = cardBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer_card, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        CardBean cardBean = cardBeans.get(position);

        holder.tv_num.setText(position + 1 + "");
        if (cardBean.getAnswer_status() == 0) {//未答
            holder.linearLayout.setBackgroundResource(R.drawable.shape_answer_card_three);
            holder.tv_num.setTextColor(context.getResources().getColor(R.color.c0A0C14));
        } else if (cardBean.getAnswer_status() == 1) {//已答

        } else if (cardBean.getAnswer_status() == 2) {//答错
            holder.linearLayout.setBackgroundResource(R.drawable.shape_answer_card_one);
            holder.tv_num.setTextColor(context.getResources().getColor(R.color.white));
        } else if (cardBean.getAnswer_status() == 3) {//答对
            holder.linearLayout.setBackgroundResource(R.drawable.shape_answer_card_two);
            holder.tv_num.setTextColor(context.getResources().getColor(R.color.white));
        }


    }

    @Override
    public int getItemCount() {
        return cardBeans != null ? cardBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private TextView tv_num;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            tv_num = itemView.findViewById(R.id.tv_num);
        }
    }
}
