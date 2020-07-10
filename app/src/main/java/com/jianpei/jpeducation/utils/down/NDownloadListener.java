package com.jianpei.jpeducation.utils.down;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.material.MaterialInfoBean;
import com.jianpei.jpeducation.room.MyRoomDatabase;
import com.jianpei.jpeducation.utils.L;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;


/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class NDownloadListener extends DownloadListener1 {


    private SparseArray<BaseViewHolder> holderMap = new SparseArray<>();
    private SparseArray<MaterialInfoBean> dataMap = new SparseArray<>();

    public void bind(DownloadTask downloadTask, BaseViewHolder myHolder, MaterialInfoBean materialInfoBean) {
        for (int i = 0; i < holderMap.size(); i++) {
            if (holderMap.valueAt(i) == myHolder) {
                holderMap.remove(i);
                break;
            }
        }
        for (int i = 0; i < dataMap.size(); i++) {
            if (materialInfoBean.getId().equals(dataMap.valueAt(i).getId())) {
                dataMap.remove(i);
                break;
            }
        }
        holderMap.put(downloadTask.getId(), myHolder);
        dataMap.put(downloadTask.getId(), materialInfoBean);

    }


    @Override
    public void taskStart(@NonNull DownloadTask task, @NonNull Listener1Assist.Listener1Model model) {//开始
        BaseViewHolder viewHolder = holderMap.get(task.getId());
        if (viewHolder != null) {
            viewHolder.setText(R.id.tv_down, "正在下载");
            viewHolder.setVisible(R.id.progressBar, true);
        }

    }

    @Override
    public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {//重试

    }

    @Override
    public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {//连接


        BaseViewHolder viewHolder = holderMap.get(task.getId());
        if (viewHolder != null) {
            ProgressUtil.calcProgressToViewAndMark(viewHolder.getView(R.id.progressBar), currentOffset, totalLength, false);
        }

    }

    @Override
    public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {//进度

        BaseViewHolder viewHolder = holderMap.get(task.getId());
        if (viewHolder != null)
            ProgressUtil.updateProgressToViewWithMark(viewHolder.getView(R.id.progressBar), currentOffset, false);

    }

    @Override
    public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull Listener1Assist.Listener1Model model) {//完成




        BaseViewHolder viewHolder = holderMap.get(task.getId());
        if (viewHolder != null && cause == EndCause.COMPLETED) {
            MaterialInfoBean materialInfoBean = dataMap.get(task.getId());
            materialInfoBean.setStatus("2");
            viewHolder.setText(R.id.tv_down,"下载完成");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MyRoomDatabase.getInstance().materialInfoDao().insertMaterialInfo(materialInfoBean);
                }
            }).start();

        }

    }
}
