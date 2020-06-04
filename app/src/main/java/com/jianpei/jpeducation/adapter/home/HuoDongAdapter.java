package com.jianpei.jpeducation.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.homedata.HuoDongDataBean;

import java.util.ArrayList;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/1
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class HuoDongAdapter extends RecyclerView.Adapter<HuoDongAdapter.MyHolder> {

    private ArrayList<HuoDongDataBean> huoDongDataBeans;

    private Context context;

    public HuoDongAdapter(ArrayList<HuoDongDataBean> huoDongDataBeans, Context context) {
        this.huoDongDataBeans = huoDongDataBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_one, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
//        RequestOptions.bitmapTransform(new RoundedCorners( 5));
        Glide.with(context).setDefaultRequestOptions(RequestOptions.bitmapTransform(new RoundedCorners( 20))).load(huoDongDataBeans.get(position).getImg()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return huoDongDataBeans == null ? 0 : huoDongDataBeans.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}
