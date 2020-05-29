package com.jianpei.jpeducation.adapter;


import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/18
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class BannerMainAdapter extends BannerAdapter<Integer, BannerMainAdapter.MyHolder> {

    public BannerMainAdapter(List<Integer> datas) {
        super(datas);
    }

    @Override
    public MyHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new MyHolder(imageView);
    }

    @Override
    public void onBindView(MyHolder holder, Integer data, int position, int size) {
        holder.imageView.setImageResource(data);

    }

    class MyHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyHolder(@NonNull ImageView itemView) {
            super(itemView);
            this.imageView = itemView;

        }
    }
}


