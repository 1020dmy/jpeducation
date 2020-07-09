package com.jianpei.jpeducation.adapter.mine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.integral.IntegralInfoBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SigninAdapter extends RecyclerView.Adapter<SigninAdapter.MyHolder> {


    private List<IntegralInfoBean.RegistrationInfoBean> list;

    private MyItemOnClickListener myItemOnClickListener;

    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    public SigninAdapter(List<IntegralInfoBean.RegistrationInfoBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_integral_signin, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        IntegralInfoBean.RegistrationInfoBean registrationInfoBean = list.get(position);
        if (list.size() - 1 == position) {
            holder.tvLine.setVisibility(View.GONE);
        }else{
            holder.tvLine.setVisibility(View.VISIBLE);

        }

        holder.tvTime.setText(registrationInfoBean.getDate_str());
        if (1 == registrationInfoBean.getIs_sign()) {
            holder.ivTime.setImageResource(R.drawable.signin_complete);
        } else if (2 == registrationInfoBean.getIs_sign()) {
            holder.ivTime.setImageResource(R.drawable.signin_buqian);
        } else {
            holder.ivTime.setImageResource(R.drawable.signin_weiqian);
            holder.tvLine.setBackgroundResource(R.color.ce9e9e9);
        }


    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTime;
        private TextView tvLine;
        private ImageView ivTime;
        private LinearLayout linearLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvLine = itemView.findViewById(R.id.tv_line);
            ivTime = itemView.findViewById(R.id.iv_time);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            linearLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null) {
                myItemOnClickListener.onItemClick(getLayoutPosition(), v);
            }

        }
    }
}
