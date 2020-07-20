package com.jianpei.jpeducation.adapter.elective;

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
import com.jianpei.jpeducation.bean.elective.GroupDataBean;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/20
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ElectiveAdapter extends RecyclerView.Adapter<ElectiveAdapter.MyHolder> {


    private List<GroupInfoBean> groupDataBeans;
    private Context context;

    private MyItemOnClickListener myItemOnClickListener;

    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    public ElectiveAdapter(List<GroupInfoBean> groupDataBeans, Context context) {
        this.groupDataBeans = groupDataBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_elective, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        GroupInfoBean groupDataBean = groupDataBeans.get(position);
        Glide.with(context).load(groupDataBean.getImg()).into(holder.imageView);
        holder.tv_title.setText(groupDataBean.getTitle());
        holder.tv_description.setText(groupDataBean.getSub_title());
        if ("0".equals(groupDataBean.getReal_ques_status())) {
            holder.tv_tipt.setVisibility(View.GONE);
        }
        if ("0".equals(groupDataBean.getSimulate_ques_status())) {
            holder.tv_tipo.setVisibility(View.GONE);
        }

        holder.tv_num.setText(groupDataBean.getBuy_num() + "人报名");
        holder.tv_price.setText(groupDataBean.getPrice_str());


    }

    @Override
    public int getItemCount() {
        return groupDataBeans != null ? groupDataBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView tv_title, tv_description, tv_tipo, tv_tipt, tv_num, tv_price, tv_signup;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_tipo = itemView.findViewById(R.id.tv_tipo);
            tv_tipt = itemView.findViewById(R.id.tv_tipt);
            tv_num = itemView.findViewById(R.id.tv_num);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_signup = itemView.findViewById(R.id.tv_signup);
            tv_signup.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null)
                myItemOnClickListener.onItemClick(getLayoutPosition(), v);
        }
    }
}
