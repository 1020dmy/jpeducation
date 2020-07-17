package com.jianpei.jpeducation.adapter.school;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.school.ImagesBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.MyHolder> {


    private List<ImagesBean> imagesBeans;

    private Context context;

    public ImageListAdapter(List<ImagesBean> imagesBeans, Context context) {
        this.imagesBeans = imagesBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_school_image, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Glide.with(context).load(imagesBeans.get(position).getUrl()).placeholder(R.drawable.home_icon_demo).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imagesBeans != null ? imagesBeans.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
