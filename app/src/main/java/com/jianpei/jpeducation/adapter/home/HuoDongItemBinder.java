package com.jianpei.jpeducation.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.binder.BaseItemBinder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.homedata.HuoDongDataBean;

import org.jetbrains.annotations.NotNull;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/3
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class HuoDongItemBinder extends BaseItemBinder<HuoDongDataBean, HuoDongItemBinder.MyHolder> {


    private Context context;

    public HuoDongItemBinder(Context context) {
        this.context = context;
    }

    @Override
    public void convert(@NotNull MyHolder myHolder, HuoDongDataBean huoDongDataBean) {
        Glide.with(context).setDefaultRequestOptions(RequestOptions.bitmapTransform(new RoundedCorners( 20))).load(huoDongDataBean.getImg()).into(myHolder.imageView);

    }

    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_one, viewGroup, false);
        return new MyHolder(view);
    }

    class MyHolder extends BaseViewHolder {
        private ImageView imageView;

        public MyHolder(@NotNull View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);

        }
    }


}
