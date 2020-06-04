package com.jianpei.jpeducation.adapter.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
            materialInfoItemOnClickListener.OnItemClick(view, data.getId());
        }

    }


    class MyHolder extends BaseViewHolder {
        private TextView tvTitle, tvNums, tvDown;

        public MyHolder(@NotNull View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            tvNums = view.findViewById(R.id.tv_nums);
            tvDown = view.findViewById(R.id.tv_down);

            addChildClickViewIds(R.id.tv_down);

        }
    }

}
