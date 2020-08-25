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
import com.jianpei.jpeducation.utils.myclassdown.DownloadClassManager;

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

    public PlayListJIeProvider(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
        addChildClickViewIds(R.id.iv_download, R.id.ll_section,R.id.tv_progress);

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
        //1START,2STOP，3ERROR，4COMPLETE
        if (viodsBean.getStatus() == DownloadClassManager.START) {//开始
            baseViewHolder.setImageResource(R.id.iv_download, R.drawable.download_progress_o);
            baseViewHolder.setVisible(R.id.tv_progress, true);
            baseViewHolder.setGone(R.id.iv_download, true);
            baseViewHolder.setText(R.id.tv_progress, viodsBean.getProgress() + "%");
        } else if (viodsBean.getStatus() == DownloadClassManager.STOP) {//停止下载
            baseViewHolder.setImageResource(R.id.iv_download, R.drawable.download_progress_t);
            baseViewHolder.setVisible(R.id.tv_progress, true);
            baseViewHolder.setGone(R.id.iv_download, true);
            baseViewHolder.setText(R.id.tv_progress, "继续下载");
        } else if (viodsBean.getStatus() == DownloadClassManager.ERROR) {//下载错误
            baseViewHolder.setGone(R.id.iv_download, true);
            baseViewHolder.setVisible(R.id.tv_progress, true);
            baseViewHolder.setText(R.id.tv_progress, "重新下载");

        } else if (viodsBean.getStatus() == DownloadClassManager.COMPLETE) {//下载完成
            baseViewHolder.setImageResource(R.id.iv_download, R.drawable.download_progress_t);
            baseViewHolder.setVisible(R.id.tv_progress, true);
            baseViewHolder.setGone(R.id.iv_download, true);
            baseViewHolder.setText(R.id.tv_progress, "下载完成");
        } else {//未下载
            baseViewHolder.setImageResource(R.id.iv_download, R.drawable.download_progress_o);
            baseViewHolder.setGone(R.id.tv_progress, true);
            baseViewHolder.setVisible(R.id.iv_download, true);
        }
    }


    @Override
    public void onChildClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        if (myItemOnClickListener != null) {
            if (view.getId() == R.id.ll_section) {
                L.e("npositon:" + nPosition + ",position:" + position);
                if (nPosition != position) {
                    ViodBean viodBean = (ViodBean) data;
                    viodBean.setIs_last_read("1");
                    if (nPosition != -1 && nViodBean != null) {
                        nViodBean.setIs_last_read("0");
                        getAdapter().notifyItemChanged(nPosition);
                    }
                    getAdapter().notifyItemChanged(position);
                    //发送通知切换视频
                    myItemOnClickListener.onItemClick(helper, view, data, position);
                }
            } else {
                myItemOnClickListener.onItemClick(helper, view, data, position);
            }
        }
    }
}
