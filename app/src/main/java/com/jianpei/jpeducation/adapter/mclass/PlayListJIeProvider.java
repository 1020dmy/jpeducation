package com.jianpei.jpeducation.adapter.mclass;

import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.GroupInfoAdapter;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.mclass.MClassInfoBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.utils.L;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;

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

    public int nPosition;
    public ViodBean nViodBean;


    public LinkedHashMap<String, BaseViewHolder> downloadingInfos;

    public PlayListJIeProvider(LinkedHashMap<String, BaseViewHolder> downloadingInfos) {
        this.downloadingInfos = downloadingInfos;
    }

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
        if ("1".equals(viodsBean.getIs_last_read())) {
            nPosition = baseViewHolder.getLayoutPosition();
            nViodBean = (ViodBean) baseNode;
            baseViewHolder.setTextColorRes(R.id.tv_title, R.color.cE73B30);
            baseViewHolder.setTextColorRes(R.id.tv_play_progress, R.color.cE73B30);
            baseViewHolder.setImageResource(R.id.iv_play_progress, R.drawable.palyer_progerss_o);
            baseViewHolder.setText(R.id.tv_play_progress, "正在学习");
        } else {
            baseViewHolder.setTextColorRes(R.id.tv_title, R.color.c666666);
            baseViewHolder.setTextColorRes(R.id.tv_play_progress, R.color.c999999);
            baseViewHolder.setImageResource(R.id.iv_play_progress, R.drawable.palyer_progerss_t);
            if (schedule > 0) {
                baseViewHolder.setText(R.id.tv_play_progress, viodsBean.getSchedule() + "%");
            } else {
                baseViewHolder.setText(R.id.tv_play_progress, "开始学习");
            }
        }
        //1.准备，3.下载状态，4.停止，5.完成
        if (viodsBean.getStatus() == 1) {
            baseViewHolder.setImageResource(R.id.iv_download, R.drawable.download_progress_o);
            baseViewHolder.setVisible(R.id.tv_progress, true);
            baseViewHolder.setVisible(R.id.iv_download, true);
            baseViewHolder.setText(R.id.tv_progress, "0%");
        } else if (viodsBean.getStatus() == 3) {//下载中

            baseViewHolder.setImageResource(R.id.iv_download, R.drawable.download_progress_o);
            baseViewHolder.setVisible(R.id.tv_progress, true);
            baseViewHolder.setVisible(R.id.iv_download, true);
                baseViewHolder.setText(R.id.tv_progress, viodsBean.getProgress() + "%");
            if (downloadingInfos != null)
                downloadingInfos.put(viodsBean.getId(), baseViewHolder);
        } else if (viodsBean.getStatus() == 4) {
            baseViewHolder.setImageResource(R.id.iv_download, R.drawable.download_progress_t);
            baseViewHolder.setVisible(R.id.tv_progress, true);
            baseViewHolder.setVisible(R.id.iv_download, true);
            baseViewHolder.setText(R.id.tv_progress, viodsBean.getProgress() + "%");

        } else if (viodsBean.getStatus() == 5) {
            baseViewHolder.setGone(R.id.iv_download, true);
            baseViewHolder.setVisible(R.id.tv_progress, true);
            baseViewHolder.setText(R.id.tv_progress, viodsBean.getProgress() + "%");

        } else {
            baseViewHolder.setImageResource(R.id.iv_download, R.drawable.download_progress_t);
            baseViewHolder.setGone(R.id.tv_progress, true);
            baseViewHolder.setVisible(R.id.iv_download, true);
        }
    }


    @Override
    public void onChildClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        if (myItemOnClickListener != null) {
            if (view.getId() == R.id.linearLayout) {
                if (nPosition != position) {
                    ViodBean viodBean = (ViodBean) data;
                    viodBean.setIs_last_read("1");
                    if (nPosition != -1 && nViodBean!=null) {
                        nViodBean.setIs_last_read("0");
                        getAdapter().notifyItemChanged(nPosition);
                    }
                    getAdapter().notifyItemChanged(position);
                    nPosition = position;
                    //发送通知切换视频
                    myItemOnClickListener.onItemClick(helper, view, data, position);
                }
            } else {
                myItemOnClickListener.onItemClick(helper, view, data, position);
            }
        }
    }
}
