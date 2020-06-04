package com.jianpei.jpeducation.adapter.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.binder.BaseItemBinder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;

import org.jetbrains.annotations.NotNull;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/3
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MateriallTitleTItemBinder extends BaseItemBinder<String, MateriallTitleTItemBinder.MyHodler> {

    @Override
    public void convert(@NotNull MyHodler myHodler, String s) {


        myHodler.tvTitle.setText(s);

    }

    @NotNull
    @Override
    public MyHodler onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_seven, viewGroup, false);
        return new MyHodler(view);
    }

    class MyHodler extends BaseViewHolder {
        private TextView tvTitle;

        public MyHodler(@NotNull View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
        }
    }
}
