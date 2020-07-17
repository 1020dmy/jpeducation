package com.jianpei.jpeducation.adapter.mclass;

import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.mclass.MClassInfoBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.utils.L;

import org.jetbrains.annotations.NotNull;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class PlayListJIeProvider extends BaseNodeProvider {


    public MyItemOnClickListener myItemOnClickListener;

    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_playerlist_t;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
        ViodBean viodsBean = (ViodBean) baseNode;
        baseViewHolder.setText(R.id.tv_title, viodsBean.getTitle());
        double schedule = Double.parseDouble(viodsBean.getSchedule());
        if (schedule > 0) {
            baseViewHolder.setText(R.id.tv_play_progress, viodsBean.getSchedule() + "%");
        } else {
            baseViewHolder.setText(R.id.tv_play_progress, "开始学习");
        }
        if ("1".equals(viodsBean.getIs_last_read())) {
            baseViewHolder.setTextColorRes(R.id.tv_title, R.color.cE73B30);
            baseViewHolder.setTextColorRes(R.id.tv_play_progress, R.color.cE73B30);
            baseViewHolder.setImageResource(R.id.iv_play_progress, R.drawable.palyer_progerss_o);
        } else {
            baseViewHolder.setTextColorRes(R.id.tv_title, R.color.c666666);
            baseViewHolder.setTextColorRes(R.id.tv_play_progress, R.color.c999999);
            baseViewHolder.setImageResource(R.id.iv_play_progress, R.drawable.palyer_progerss_t);
        }
    }


//    @Override
//    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
//        if (myItemOnClickListener != null) {
//            myItemOnClickListener.onItemClick(helper, view, data, position);
//        }
//    }

    @Override
    public void onChildClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        if (myItemOnClickListener != null) {
            myItemOnClickListener.onItemClick(helper, view, data, position);
        }
    }
}
