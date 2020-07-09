package com.jianpei.jpeducation.adapter.home;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.material.MaterialInfoBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/1
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ZiliaoAdapter extends BaseQuickAdapter<MaterialInfoBean, ZiliaoAdapter.MyHolder> {
    public ZiliaoAdapter(@Nullable List<MaterialInfoBean> data) {
        super(R.layout.item_home_six, data);
    }

    @Override
    protected void convert(@NotNull MyHolder myHolder,MaterialInfoBean materialInfoBean) {

        myHolder.tvTitle.setText(materialInfoBean.getTitle());
        myHolder.tvNums.setText(materialInfoBean.getDownload() + "次下载");
    }



    class MyHolder extends BaseViewHolder {
        private TextView tvTitle, tvNums, tvDown;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvNums = itemView.findViewById(R.id.tv_nums);
            tvDown = itemView.findViewById(R.id.tv_down);
        }
    }
}
