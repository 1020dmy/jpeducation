package com.jianpei.jpeducation.adapter.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.integral.IntegralTaskBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/6
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class IntegralTaskAdapter extends RecyclerView.Adapter<IntegralTaskAdapter.MyHolder> {


    private List<IntegralTaskBean> list;
    private Context context;


    private MyItemOnClickListener myItemOnClickListener;

    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    public IntegralTaskAdapter(List<IntegralTaskBean> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_integraltask, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        IntegralTaskBean integralTaskBean = list.get(position);
        Glide.with(context).load(integralTaskBean.getIcon()).into(holder.imageView);
        holder.tvTitle.setText(integralTaskBean.getTitle());
        holder.tvTip.setText(integralTaskBean.getDes());
        holder.tvPrice.setText("积分+" + integralTaskBean.getScore());
        if (0 == integralTaskBean.getIs_finish()) {
            holder.tvSubmit.setText("领取任务");
        } else {
            holder.tvSubmit.setText("已完成");
        }

        if (position == list.size() - 1) {
            holder.line.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView tvTitle, tvTip, tvPrice;
        private TextView tvSubmit, line;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTip = itemView.findViewById(R.id.tv_tip);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvSubmit = itemView.findViewById(R.id.tv_submit);
            line = itemView.findViewById(R.id.line);
//            tvSubmit.setEnabled(false);
            tvSubmit.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null) {
                myItemOnClickListener.onItemClick(getLayoutPosition(), v);
            }

        }
    }
}
