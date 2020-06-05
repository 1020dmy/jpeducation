package com.jianpei.jpeducation.adapter.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.binder.BaseItemBinder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.homedata.MaterialInfoBean;
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
    public void convert(@NotNull MyHolder myHolder, MaterialInfoBean materialInfoBean) {

        if ("3".equals(materialInfoBean.getStatus())) {
            myHolder.tvDown.setText("下载失败");
        } else if ("1".equals(materialInfoBean.getStatus())) {
            myHolder.tvDown.setText("正在下载");
            myHolder.progressBar.setVisibility(View.VISIBLE);
        } else if ("2".equals(materialInfoBean.getStatus())) {
            myHolder.tvDown.setText("下载完成");
            myHolder.progressBar.setVisibility(View.VISIBLE);
        } else {
            myHolder.tvDown.setText("下载");
            myHolder.progressBar.setVisibility(View.INVISIBLE);
        }
        myHolder.progressBar.setProgress(materialInfoBean.getProgress());
        myHolder.tvTitle.setText(materialInfoBean.getTitle());
        myHolder.tvNums.setText(materialInfoBean.getDownload() + "次下载");

    }

    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_six, viewGroup, false);
        return new MyHolder(view);
    }


    @Override
    public void onChildClick(@NotNull MyHolder holder, @NotNull View view, MaterialInfoBean data, int position) {
        if (materialInfoItemOnClickListener != null) {
            materialInfoItemOnClickListener.OnItemClick(view, position);
        }

    }


    class MyHolder extends BaseViewHolder {
        private TextView tvTitle, tvNums, tvDown;
        private ProgressBar progressBar;

        public MyHolder(@NotNull View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            tvNums = view.findViewById(R.id.tv_nums);
            tvDown = view.findViewById(R.id.tv_down);

            progressBar = view.findViewById(R.id.progressBar);

            addChildClickViewIds(R.id.tv_down);

        }
    }

}
