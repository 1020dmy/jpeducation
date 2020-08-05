package com.jianpei.jpeducation.adapter.offlineclass;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.player.TryPlayerActivity;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.bean.offlineclass.OfflineClassContentBean;

import org.jetbrains.annotations.NotNull;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/29
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class OfflineClassContentProvider extends BaseNodeProvider {


//    private Context context;

    private MyItemOnClickListener myItemOnClickListener;

    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

//    public OfflineClassContentProvider(Context context) {
//        this.context = context;
//    }

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_offline_class_conten;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
        ViodBean viodBean = (ViodBean) baseNode;
        baseViewHolder.setText(R.id.tv_title, viodBean.getTitle());

    }

    @Override
    public void onChildClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        if (myItemOnClickListener!=null){
            myItemOnClickListener.onItemClick(helper, view, data, position);
        }

    }

//    @Override
//    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
//        ViodBean viodBean = (ViodBean) data;
//
//        context.startActivity(new Intent(context, TryPlayerActivity.class).putExtra("localUrl", viodBean.getSavePath()).putExtra("title", viodBean.getTitle()));
//    }
}
