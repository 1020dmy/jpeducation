package com.jianpei.jpeducation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.jianpei.jpeducation.R;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.MyHolder> {
    private List<Integer> imageUrls;

    public GuideAdapter(List<Integer> imageUrls) {
        this.imageUrls = imageUrls;
    }



    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.imageView.setImageResource(imageUrls.get(position));

    }


    @Override
    public int getItemCount() {
        return imageUrls == null ? 0 : imageUrls.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
