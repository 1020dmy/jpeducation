package com.jianpei.jpeducation.adapter.gold;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.mine.IntegralAndGoldDetailsAdapter;
import com.jianpei.jpeducation.bean.gold.GoldBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/25
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class GoldDetailAdapter extends RecyclerView.Adapter<GoldDetailAdapter.MyHolder> {


    private List<GoldBean> goldBeans;
    private Context context;

    public GoldDetailAdapter(List<GoldBean> goldBeans,Context context) {
        this.goldBeans = goldBeans;
        this.context=context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_integralandgold, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        GoldBean goldBean=goldBeans.get(position);

        holder.tvTitle.setText(goldBean.getSource_str());
        holder.tvTime.setText(goldBean.getCreate_time_str());

        if ("2".equals(goldBean.getType())) {//支出
            holder.tvPrice.setText(goldBean.getChange());
            holder.tvPrice.setTextColor(context.getResources().getColor(R.color.c888B96));

        } else {
            holder.tvPrice.setText("+" + goldBean.getChange());
            holder.tvPrice.setTextColor(context.getResources().getColor(R.color.cE73B30));
        }


    }

    @Override
    public int getItemCount() {
        return goldBeans != null ? goldBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvPrice, tvTime, line;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvTime = itemView.findViewById(R.id.tv_time);
            line = itemView.findViewById(R.id.line);

        }
    }
}
