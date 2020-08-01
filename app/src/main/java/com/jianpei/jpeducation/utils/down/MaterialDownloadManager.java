package com.jianpei.jpeducation.utils.down;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jianpei.jpeducation.bean.material.MaterialInfoBean;
import com.jianpei.jpeducation.room.MyRoomDatabase;
import com.jianpei.jpeducation.utils.L;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;

import java.io.File;
import java.util.Collection;
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

    private File downloadDir;


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
            if (materialDownloadListener != null) {
                MaterialInfoBean materialInfoBean = downloadInfos.get(task);
                materialInfoBean.setStatus("1");
                insert(materialInfoBean);//插入数据库
                materialDownloadListener.taskStart(materialInfoBean);
            }

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
                materialDownloadListener.progress(downloadInfos.get(task), currentOffset, totalLength);
        }

        @Override
        public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull Listener1Assist.Listener1Model model) {
            //完成
            MaterialInfoBean materialInfoBean = downloadInfos.get(task);

            if (materialDownloadListener != null) {
                if (cause == EndCause.COMPLETED) {//成功
                    materialInfoBean.setStatus("2");
                    materialDownloadListener.onSuccess(materialInfoBean);
                } else {//失败
                    L.e("==========onError:" + cause + "," + realCause.getMessage());
                    materialInfoBean.setStatus("3");
                    materialDownloadListener.onError(cause.name());
                }
            }
            insert(materialInfoBean);
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

    public void startDownload(MaterialInfoBean materialInfoBean) {

        Collection<MaterialInfoBean> materialInfoBeans = downloadInfos.values();
        for (MaterialInfoBean materialInfoBean1 : materialInfoBeans) {
            if (materialInfoBean.getId().equals(materialInfoBean1.getId())) {
                if (materialDownloadListener != null) {
                    materialDownloadListener.retry(materialInfoBean);
                }
                return;
            }
        }
        DownloadTask task = new DownloadTask.Builder(materialInfoBean.getDownloadUrl(), downloadDir)
                .setFilename(materialInfoBean.getTitle())
                .setMinIntervalMillisCallbackProcess(30)
                .setPassIfAlreadyCompleted(false)
                .build();
        downloadInfos.put(task, materialInfoBean);//添加进队列
        task.enqueue(downloadListener1);//开始下载
    }

    private void insert(MaterialInfoBean materialInfoBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyRoomDatabase.getInstance().materialInfoDao().insertMaterialInfo(materialInfoBean);
            }
        }).start();
    }

    public void freed() {
        materialDownloadListener = null;
    }
}
