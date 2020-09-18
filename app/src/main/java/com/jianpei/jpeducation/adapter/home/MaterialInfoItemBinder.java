package com.jianpei.jpeducation.adapter.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.binder.BaseItemBinder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.material.MaterialInfoBean;
import com.jianpei.jpeducation.utils.listener.MaterialInfoItemOnClickListener;

import org.jetbrains.annotations.NotNull;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/3
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MaterialInfoItemBinder extends BaseItemBinder<MaterialInfoBean, MaterialInfoItemBinder.MyHolder> {

    private MaterialInfoItemOnClickListener materialInfoItemOnClickListener;

    public void setMaterialInfoItemOnClickListener(MaterialInfoItemOnClickListener materialInfoItemOnClickListener) {
        this.materialInfoItemOnClickListener = materialInfoItemOnClickListener;
    }

    @Override
    public void convert(@NotNull MaterialInfoItemBinder.MyHolder holder, MaterialInfoBean materialInfoBean) {

        holder.tvTitle.setText(materialInfoBean.getTitle());

        holder.tvNums.setText(materialInfoBean.getDownload() + "人兑换");

//        if (materialInfoBean.getStatus().equals("2")) {
//            holder.tvDown.setText("下载完成");
//            holder.progressBar.setVisibility(View.VISIBLE);
//            holder.progressBar.setProgress(100);
//        }
    }

    @NotNull
    @Override
    public MaterialInfoItemBinder.MyHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_six, viewGroup, false);
        return new MyHolder(view);
    }


    @Override
    public void onChildClick(@NotNull MaterialInfoItemBinder.MyHolder holder, @NotNull View view, MaterialInfoBean data, int position) {
        if (materialInfoItemOnClickListener != null) {
            materialInfoItemOnClickListener.OnItemClick(holder, data);
        }

    }


    public class MyHolder extends BaseViewHolder {
        public TextView tvTitle, tvNums, tvDown;
//        public ProgressBar progressBar;

        public MyHolder(@NotNull View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            tvNums = view.findViewById(R.id.tv_nums);
            tvDown = view.findViewById(R.id.tv_down);

//            progressBar = view.findViewById(R.id.progressBar);
//
            addChildClickViewIds(R.id.tv_down);

        }


        public MaterialInfoBean getData() {

            return (MaterialInfoBean) MaterialInfoItemBinder.this.getData().get(getLayoutPosition());
        }


    }

}
