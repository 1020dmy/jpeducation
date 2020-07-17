package com.jianpei.jpeducation.adapter.school;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;

import java.io.File;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/14
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class PostNewsPhotoAdapter extends RecyclerView.Adapter<PostNewsPhotoAdapter.MyHolder> {


    private List<File> images;

    private Context context;

    private MyItemOnClickListener myItemOnClickListener;

    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    public PostNewsPhotoAdapter(List<File> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_postnew_photo, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        Luban.with(context).ignoreBy(100).load(images.get(position)).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(File file) {
                Glide.with(context).load(file).into(holder.imageView);
            }

            @Override
            public void onError(Throwable e) {

            }
        }).launch();


    }

    @Override
    public int getItemCount() {
        return images != null ? images.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView, ivDelete;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            ivDelete = itemView.findViewById(R.id.iv_delete);
            ivDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null)
                myItemOnClickListener.onItemClick(getLayoutPosition(), v);

        }
    }

}
