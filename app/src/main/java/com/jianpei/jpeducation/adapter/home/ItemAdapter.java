package com.jianpei.jpeducation.adapter.home;

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
import com.jianpei.jpeducation.bean.homedata.RegimentInfoBean;

import java.util.ArrayList;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/1
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyHolder> {


    private ArrayList<RegimentInfoBean> dataBeans;
    private Context context;

    public ItemAdapter(ArrayList<RegimentInfoBean> dataBeans,Context context) {
        this.dataBeans = dataBeans;
        this.context=context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_three, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Glide.with(context).load(dataBeans.get(position).getImg()).placeholder(R.drawable.home_icon_demo).into(holder.imageView);
        holder.tvTitle.setText(dataBeans.get(position).getTitle());
        holder.tvPrice.setText("￥"+dataBeans.get(position).getPrice());
        holder.tvNums.setText(dataBeans.get(position).getNum_people()+"人团");

    }

    @Override
    public int getItemCount() {
        return dataBeans != null ? dataBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tvTitle, tvPrice, tvNums, tvBaoming;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvNums = itemView.findViewById(R.id.tv_nums);
            tvBaoming = itemView.findViewById(R.id.tv_baoming);
        }
    }
}
