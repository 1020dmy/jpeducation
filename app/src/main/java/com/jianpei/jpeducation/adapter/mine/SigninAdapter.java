package com.jianpei.jpeducation.adapter.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SigninAdapter extends RecyclerView.Adapter<SigninAdapter.MyHolder> {


    private Context context;

    public SigninAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_integral_signin, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        if (position == 6) {
            holder.tvLine.setVisibility(View.GONE);
        }
        if (position == 3) {
            holder.ivTime.setImageResource(R.drawable.signin_buqian);

        }

    }

    @Override
    public int getItemCount() {
        return 7;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvTime;
        private TextView tvLine;
        private ImageView ivTime;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvLine = itemView.findViewById(R.id.tv_line);
            ivTime = itemView.findViewById(R.id.iv_time);
        }
    }
}
