package com.jianpei.jpeducation.utils.down;

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jianpei.jpeducation.adapter.material.MaterialInfoAdapter;
import com.jianpei.jpeducation.bean.homedata.MaterialInfoBean;
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
 * Created by sjl on 2020/6/10
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class QueueListener extends DownloadListener1 {


    private SparseArray<MaterialInfoAdapter.MyHolder> holderMap = new SparseArray<>();


    public void bind(DownloadTask downloadTask, MaterialInfoAdapter.MyHolder myHolder) {
        for (int i = 0; i < holderMap.size(); i++) {
            if (holderMap.valueAt(i) == myHolder) {
                holderMap.remove(i);
                break;
            }
        }
        holderMap.put(downloadTask.getId(), myHolder);

    }

    @Override
    public void taskStart(@NonNull DownloadTask task, @NonNull Listener1Assist.Listener1Model model) {

        MaterialInfoAdapter.MyHolder myHolder = holderMap.get(task.getId());
        if (myHolder != null) {
            myHolder.tvDown.setText("正在下载");
            myHolder.progressBar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {

    }

    @Override
    public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {
        MaterialInfoAdapter.MyHolder myHolder = holderMap.get(task.getId());
        if (myHolder != null) {
            ProgressUtil.calcProgressToViewAndMark(
                    myHolder.progressBar,
                    currentOffset,
                    totalLength,
                    false
            );
        }

    }

    @Override
    public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
        MaterialInfoAdapter.MyHolder myHolder = holderMap.get(task.getId());
        if (myHolder != null) {
            L.e("progress:" + currentOffset + "," + totalLength);
            ProgressUtil.updateProgressToViewWithMark(myHolder.progressBar, currentOffset, false);
        }
    }

    @Override
    public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull Listener1Assist.Listener1Model model) {
        MaterialInfoAdapter.MyHolder myHolder = holderMap.get(task.getId());
        if (myHolder != null) {
            if (cause == EndCause.COMPLETED) {
                myHolder.progressBar.setProgress(myHolder.progressBar.getMax());
                myHolder.tvDown.setText("下载完成");
                //存入数据库
                MaterialInfoBean materialInfoBean = myHolder.getData();
                materialInfoBean.setStatus("2");
                MyRoomDatabase.getInstance().materialInfoDao().insertMaterialInfo(materialInfoBean);

            }
        }
    }
}
