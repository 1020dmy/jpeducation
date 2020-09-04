package com.jianpei.jpeducation.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.activitys.TryListenerListActivity;
import com.jianpei.jpeducation.activitys.classinfo.ClassInfoActivity;
import com.jianpei.jpeducation.activitys.web.GuiZeActivity;
import com.jianpei.jpeducation.bean.homedata.BannerDataBean;
import com.jianpei.jpeducation.bean.homedata.HomeDataBean;
import com.jianpei.jpeducation.viewmodel.MainModel;
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
public class BannerMainAdapter extends BannerAdapter<BannerDataBean, BannerMainAdapter.MyHolder> {

    private Context context;

    private MainModel mainModel;

    public BannerMainAdapter(List<BannerDataBean> datas, Context context) {
        super(datas);
        this.context = context;
    }

    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    @Override
    public MyHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setAdjustViewBounds(true);
        return new MyHolder(imageView);
    }

    @Override
    public void onBindView(MyHolder holder, BannerDataBean data, int position, int size) {

        Glide.with(context).load(data.getImg()).into(holder.imageView);

    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;

        public MyHolder(@NonNull ImageView itemView) {
            super(itemView);
            this.imageView = itemView;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            BannerDataBean data=getData(getLayoutPosition()-1);
            if (data==null)
                return;
            if ("url".equals(data.getApp_jump_type())){
                context.startActivity(new Intent(context, GuiZeActivity.class)
                        .putExtra("title", data.getTitle())
                        .putExtra("webUrl", data.getUrl()));
            }else if ("group".equals(data.getApp_jump_type())){
                context.startActivity(new Intent(context, ClassInfoActivity.class)
                        .putExtra("groupId", data.getApp_point_id())
                        .putExtra("catId", data.getCat_id()));
            }else if ("try_listen".equals(data.getApp_jump_type())){
                        context.startActivity(new Intent(context, TryListenerListActivity.class));
            }else if ("course_selection".equals(data.getApp_jump_type()) && mainModel!=null){
                mainModel.getChangeBottomLiveData().setValue(2);
            }

        }
    }
}


