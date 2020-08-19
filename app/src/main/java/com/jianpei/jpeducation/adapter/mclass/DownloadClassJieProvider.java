package com.jianpei.jpeducation.adapter.mclass;

import android.view.View;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.utils.myclassdown.DownloadClassManager;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/13
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class DownloadClassJieProvider extends BaseNodeProvider {


    private MyItemOnClickListener myItemOnClickListener;


    public DownloadClassJieProvider(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
        addChildClickViewIds(R.id.tv_delete, R.id.tv_status);
    }

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_download_class_jie;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) {
        ViodBean viodBean = (ViodBean) baseNode;
        baseViewHolder.setText(R.id.tv_title, viodBean.getTitle());
        ProgressBar progressBar = baseViewHolder.getView(R.id.progressBar);
        //1START,2STOP，3ERROR，4COMPLETE
        if (viodBean.getStatus() == DownloadClassManager.START) {
            baseViewHolder.setText(R.id.tv_status, "正在下载");
        } else if (viodBean.getStatus() == DownloadClassManager.STOP) {
            baseViewHolder.setText(R.id.tv_status, "继续下载");
        } else if (viodBean.getStatus() == DownloadClassManager.ERROR) {
            baseViewHolder.setText(R.id.tv_status, "重新下载");
        } else if (viodBean.getStatus() == DownloadClassManager.COMPLETE) {
            baseViewHolder.setText(R.id.tv_status, "下载完成");
        }
        progressBar.setProgress(viodBean.getProgress());
    }

    @Override
    public void onChildClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        if (myItemOnClickListener != null) {
            myItemOnClickListener.onItemClick(helper, view, data, position);
        }
    }
}
