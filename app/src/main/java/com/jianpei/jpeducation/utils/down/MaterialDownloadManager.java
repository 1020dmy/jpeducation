package com.jianpei.jpeducation.utils.down;

import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jianpei.jpeducation.bean.material.MaterialInfoBean;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;

import java.io.File;
import java.util.LinkedHashMap;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/29
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MaterialDownloadManager {

    private static volatile MaterialDownloadManager mInstance = null;

    private File downloadDir = Environment.getDownloadCacheDirectory();


    private LinkedHashMap<DownloadTask, MaterialInfoBean> downloadInfos = new LinkedHashMap<>();
    private MaterialDownloadListener materialDownloadListener;


    public void setMaterialDownloadListener(MaterialDownloadListener materialDownloadListener) {
        this.materialDownloadListener = materialDownloadListener;
    }

    /**
     * 内部监听
     */
    private DownloadListener1 downloadListener1 = new DownloadListener1() {
        @Override
        public void taskStart(@NonNull DownloadTask task, @NonNull Listener1Assist.Listener1Model model) {
            //开始下载
            if (materialDownloadListener != null)
                materialDownloadListener.taskStart(downloadInfos.get(task));

        }

        @Override
        public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {
            //重试
            if (materialDownloadListener != null)
                materialDownloadListener.retry(downloadInfos.get(task));
        }

        @Override
        public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {
            //连接
            if (materialDownloadListener != null)
                materialDownloadListener.connected(downloadInfos.get(task));
        }

        @Override
        public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
            //进度
            if (materialDownloadListener != null)
                materialDownloadListener.progress(downloadInfos.get(task));
        }

        @Override
        public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull Listener1Assist.Listener1Model model) {
            //完成
            if (materialDownloadListener != null)
                materialDownloadListener.taskEnd(downloadInfos.get(task));
            downloadInfos.remove(task);
        }
    };


    private MaterialDownloadManager() {

    }

    public static MaterialDownloadManager getInstance() {
        if (mInstance == null) {
            synchronized (MaterialDownloadManager.class) {
                if (mInstance == null)
                    mInstance = new MaterialDownloadManager();
            }
        }
        return mInstance;
    }


    public File getDownloadDir() {
        return downloadDir;
    }

    public void setDownloadDir(File downloadDir) {
        this.downloadDir = downloadDir;
    }

    public void startDownload(MaterialInfoBean materialInfoBean, String url) {
        DownloadTask task = new DownloadTask.Builder(url, downloadDir)
                .setFilename(materialInfoBean.getTitle())
                .setMinIntervalMillisCallbackProcess(30)
                .setPassIfAlreadyCompleted(false)
                .build();
        task.enqueue(downloadListener1);

    }
}
