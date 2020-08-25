package com.jianpei.jpeducation.adapter.mine;

import android.content.Context;
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


/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyHolder> {

    private List<File> images;
    private Context context;

    private MyItemOnClickListener myItemOnClickListener;

    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    public FeedbackAdapter(List<File> images, Context context) {
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


        if (position < images.size()) {
            holder.ivDelete.setVisibility(View.VISIBLE);
            Glide.with(context).load(images.get(position)).into(holder.imageView);

        } else {
            holder.ivDelete.setVisibility(View.GONE);
            if (images.size() == 0) {
                holder.imageView.setImageResource(R.drawable.feedback_add_pict);
            } else {
                holder.imageView.setImageResource(R.drawable.feedback_add_pic);

            }
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myItemOnClickListener != null)
                        myItemOnClickListener.onItemClick(position, v);

                }
            });
        }
    }

    @Override
    public int getItemCount() {

        if (images == null) {
            return 1;
        }
        if (images.size() != 3) {
            return images.size() + 1;

        } else {
            return images.size();
        }
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
