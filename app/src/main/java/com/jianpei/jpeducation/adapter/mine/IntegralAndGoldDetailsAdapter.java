package com.jianpei.jpeducation.adapter.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.integral.IntegralDataBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/7
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class IntegralAndGoldDetailsAdapter extends RecyclerView.Adapter<IntegralAndGoldDetailsAdapter.MyHolder> {


    private List<IntegralDataBean.DataBean> list;

    private Context context;

    public IntegralAndGoldDetailsAdapter(List<IntegralDataBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_integralandgold, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        IntegralDataBean.DataBean dataBean = list.get(position);

        holder.tvTitle.setText(dataBean.getSource());
        holder.tvTime.setText(dataBean.getCreate_time_str());

        if ("2".equals(dataBean.getType())) {//支出
            holder.tvPrice.setText(dataBean.getIntegral());
            holder.tvPrice.setTextColor(context.getResources().getColor(R.color.c888B96));

        } else {
            holder.tvPrice.setText("+" + dataBean.getIntegral());
            holder.tvPrice.setTextColor(context.getResources().getColor(R.color.cE73B30));
        }

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
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
