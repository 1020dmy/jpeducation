package com.jianpei.jpeducation.adapter.material;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.material.MaterialInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/10
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MaterialInfoAdapter extends RecyclerView.Adapter<MaterialInfoAdapter.MyHolder> {

    private List<MaterialInfoBean> materialInfoBeans;

    private MyItemChildClickListener myItemChildClickListener;

    public void setMyItemChildClickListener(MyItemChildClickListener myItemChildClickListener) {
        this.myItemChildClickListener = myItemChildClickListener;
    }

    public MaterialInfoAdapter(List<MaterialInfoBean> materialInfoBeans) {
        this.materialInfoBeans = materialInfoBeans;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_material_info, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        MaterialInfoBean materialInfoBean = materialInfoBeans.get(position);

        holder.tvTitle.setText(materialInfoBean.getTitle());

        holder.tvNums.setText(materialInfoBean.getDownload() + "次下载");

        if (materialInfoBean.getStatus().equals("2")) {
            holder.tvDown.setText("下载完成");
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.progressBar.setProgress(100);
        }

    }

    @Override
    public int getItemCount() {
        return materialInfoBeans != null ? materialInfoBeans.size() : 0;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvTitle, tvNums;
        public ProgressBar progressBar;
        public TextView tvDown;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvNums = itemView.findViewById(R.id.tv_nums);
            tvDown = itemView.findViewById(R.id.tv_down);
            progressBar = itemView.findViewById(R.id.progressBar);
            tvDown.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (myItemChildClickListener != null) {
                myItemChildClickListener.onClick(materialInfoBeans.get(getAdapterPosition()), this);
            }

        }

        public MaterialInfoBean getData() {
            return materialInfoBeans.get(getLayoutPosition());
        }
    }
}
