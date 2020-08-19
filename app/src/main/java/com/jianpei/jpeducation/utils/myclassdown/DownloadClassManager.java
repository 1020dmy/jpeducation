package com.jianpei.jpeducation.utils.myclassdown;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.aliyun.downloader.AliDownloaderFactory;
import com.aliyun.downloader.AliMediaDownloader;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.player.source.VidAuth;
import com.jianpei.jpeducation.base.MyApplication;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.room.MyRoomDatabase;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.classdownload.DownloadMediaInfo;
import com.jianpei.jpeducation.utils.classdownload.ThreadUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/18
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class DownloadClassManager {
    //准备下载（验证是否重复下载，返回下载清晰度选择，存入数据库）
    //开始下载（进度监听，数据库存储）
    //暂停下载（状态监听，数据库存储）
    //下载失败（状态监听，数据存储）
    //下载完成（状态监听，数据存储）

    //下载状态
    public static int START = 1;//开始
    public static int STOP = 2;//停止
    public static int ERROR = 3;//错误
    public static int COMPLETE = 4;//完成
    public static int DELETE = 0;//删除完成


    private int mMaxNum = 3;//最大下载数量


    private Context mContext;
    private String downloadDir;//下载路径

    private long freshStorageSizeTime = 0;//最近一次的存储时间


    private LinkedHashMap<String, AliMediaDownloader> downloadInfos = new LinkedHashMap<>();

    private ConcurrentLinkedQueue<ViodBean> downloading = new ConcurrentLinkedQueue<>();


    private DownloadClassManager() {
        mContext = MyApplication.getInstance();
        downloadDir = mContext.getExternalCacheDir() + "/AliPlayerDemoDownload";
        if (!TextUtils.isEmpty(downloadDir)) {
            File file = new File(downloadDir);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    private static volatile DownloadClassManager mInstance;

    public static DownloadClassManager getInstance() {
        if (mInstance == null) {
            synchronized (DownloadClassManager.class) {
                if (mInstance == null)
                    mInstance = new DownloadClassManager();
            }
        }
        return mInstance;
    }

    //对外监听
    public List<DownloadClassListener> outClassDownloadListeners = new ArrayList<>();


    //内部监听
    public DownloadClassListener classDownloadListener = new DownloadClassListener() {
        @Override
        public void onPrepared(List<TrackInfo> trackInfos, ViodBean viodBean) {//准备完成
            for (DownloadClassListener classDownloadListener : outClassDownloadListeners) {
                classDownloadListener.onPrepared(trackInfos, viodBean);
            }


        }

        @Override
        public void onStart(ViodBean viodBean) {//更新数据库
            viodBean.setStatus(START);
            addDownloadingQueue(viodBean);//添加到下载队列中
            //更新数据库
            ThreadUtils.runOnSubThread(new Runnable() {
                @Override
                public void run() {
                    upDateViodBean(viodBean);
                }
            });
            for (DownloadClassListener classDownloadListener : outClassDownloadListeners) {
                classDownloadListener.onStart(viodBean);
            }
        }

        @Override
        public void onStop(ViodBean viodBean) {//更新数据库
            viodBean.setStatus(STOP);
            //更新数据库
            ThreadUtils.runOnSubThread(new Runnable() {
                @Override
                public void run() {
                    upDateViodBean(viodBean);
                }
            });

            for (DownloadClassListener classDownloadListener : outClassDownloadListeners) {
                classDownloadListener.onStop(viodBean);
            }
        }

        @Override
        public void onProgress(ViodBean viodBean, int progress) {//更新数据库
            //在子线程中更新数据库
            ThreadUtils.runOnSubThread(new Runnable() {
                @Override
                public void run() {
                    if (freshStorageSizeTime == 0 || ((new Date()).getTime() - freshStorageSizeTime) > 2 * 1000) {
                        MyRoomDatabase.getInstance().viodBeanDao().upDateViodBean(viodBean);
                        freshStorageSizeTime = (new Date()).getTime();
                    }
                }
            });

            if (outClassDownloadListeners == null)
                return;
            for (DownloadClassListener classDownloadListener : outClassDownloadListeners) {
                classDownloadListener.onProgress(viodBean, progress);
            }
        }

        @Override
        public void onFileProgress(ViodBean viodBean, int progress) {

            for (DownloadClassListener classDownloadListener : outClassDownloadListeners) {
                classDownloadListener.onFileProgress(viodBean, progress);
            }
        }

        @Override
        public void onError(ViodBean viodBean, ErrorInfo errorInfo) {//更新数据库
            L.e("=====onError:" + errorInfo.getCode() + "," + errorInfo.getMsg());
            viodBean.setStatus(ERROR);
            ThreadUtils.runOnSubThread(new Runnable() {
                @Override
                public void run() {
                    upDateViodBean(viodBean);
                }
            });

            for (DownloadClassListener classDownloadListener : outClassDownloadListeners) {
                classDownloadListener.onError(viodBean, errorInfo);
            }

        }

        @Override
        public void onCompletion(ViodBean viodBean) {//更新数据库

            AliMediaDownloader jniDownloader = downloadInfos.get(viodBean.getId());
            if (jniDownloader == null) {
                return;
            }
            viodBean.setStatus(COMPLETE);
            viodBean.setSavePath(jniDownloader.getFilePath());
            ThreadUtils.runOnSubThread(new Runnable() {
                @Override
                public void run() {
                    upDateViodBean(viodBean);
                }
            });

            for (DownloadClassListener classDownloadListener : outClassDownloadListeners) {
                classDownloadListener.onCompletion(viodBean);
            }
        }

        @Override
        public void againStart(ViodBean viodBean) {//重新获取vid
            for (DownloadClassListener classDownloadListener : outClassDownloadListeners) {
                classDownloadListener.againStart(viodBean);
            }
        }

        @Override
        public void deleteFile(ViodBean viodBean) {
            viodBean.setStatus(DELETE);
            viodBean.setProgress(0);
            for (DownloadClassListener classDownloadListener : outClassDownloadListeners) {
                classDownloadListener.deleteFile(viodBean);
            }
        }
    };

    /**
     * 准备下载，判断是否重复下载
     *
     * @param viodBean
     */
    public void paperDownload(VidAuth vidAuth, ViodBean viodBean) {
        //判断是否重复下载
        if (downloading != null && downloading.size() >= mMaxNum) {
            L.e("超出下载量" + mMaxNum);
            return;
        }
        if (viodBean.getStatus() == START) {
            return;
        }
        AliMediaDownloader jniDownloader = AliDownloaderFactory.create(mContext);
        jniDownloader.prepare(vidAuth);
        jniDownloader.setOnPreparedListener(new AliMediaDownloader.OnPreparedListener() {
            @Override
            public void onPrepared(MediaInfo mediaInfo) {
                viodBean.setCoverUrl(mediaInfo.getCoverUrl());
                viodBean.setDuration(mediaInfo.getDuration());
                viodBean.setVidAuth(vidAuth);
                if (viodBean.getStatus() == STOP || viodBean.getStatus() == ERROR) {//直接下载
                    startDownload(null, viodBean);
                } else {//返回清晰度
                    if (classDownloadListener != null)
                        classDownloadListener.onPrepared(mediaInfo.getTrackInfos(), viodBean);
                    jniDownloader.setSaveDir(downloadDir);
                    downloadInfos.put(viodBean.getId(), jniDownloader);
                }


            }
        });
        jniDownloader.prepare(vidAuth);
    }

    /**
     * 开始下载
     *
     * @param trackInfo
     * @param viodBean
     */
    public void startDownload(TrackInfo trackInfo, ViodBean viodBean) {

        if (viodBean == null)
            return;
        AliMediaDownloader aliMediaDownloader = downloadInfos.get(viodBean.getId());
        if (aliMediaDownloader == null) {
            aliMediaDownloader = AliDownloaderFactory.create(mContext);
            aliMediaDownloader.setSaveDir(downloadDir);
            downloadInfos.put(viodBean.getId(), aliMediaDownloader);

        }
        if (classDownloadListener != null)
            classDownloadListener.onStart(viodBean);//开始监听回调
        if (trackInfo == null) {//直接下载
            setListener(viodBean, aliMediaDownloader);
            aliMediaDownloader.selectItem(viodBean.getQualityIndex());
            aliMediaDownloader.updateSource(viodBean.getVidAuth());
            aliMediaDownloader.start();
        } else {
            viodBean.setQuality(trackInfo.getVodDefinition());
            viodBean.setmTrackInfo(trackInfo);
            viodBean.setQualityIndex(trackInfo.getIndex());
            viodBean.setFormat(trackInfo.getVodFormat());
            viodBean.setSize(trackInfo.getVodFileSize());
            viodBean.setmStatus(DownloadMediaInfo.Status.Prepare);

            setListener(viodBean, aliMediaDownloader);
            aliMediaDownloader.selectItem(trackInfo.getIndex());
            aliMediaDownloader.updateSource(viodBean.getVidAuth());
            aliMediaDownloader.start();

        }


    }

    /**
     * 设置阿里监听
     *
     * @param viodBean
     * @param aliMediaDownloader
     */
    public void setListener(ViodBean viodBean, AliMediaDownloader aliMediaDownloader) {
        aliMediaDownloader.setOnProgressListener(new AliMediaDownloader.OnProgressListener() {
            @Override
            public void onDownloadingProgress(int i) {////下载进度百分比
                L.e("progress:" + i);
                String downloadFilePath = aliMediaDownloader.getFilePath();
                if (!TextUtils.isEmpty(downloadFilePath)) {
                    viodBean.setSavePath(downloadFilePath);
                } else {
                    viodBean.setSavePath("");
                }
                if (classDownloadListener != null) {
                    viodBean.setProgress(i);
                    classDownloadListener.onProgress(viodBean, i);
                }
            }

            @Override
            public void onProcessingProgress(int i) {////处理进度百分比
                if (classDownloadListener != null) {
                    viodBean.setmFileHandleProgress(i);
                    classDownloadListener.onFileProgress(viodBean, i);
                }

            }
        });
        aliMediaDownloader.setOnCompletionListener(new AliMediaDownloader.OnCompletionListener() {
            @Override
            public void onCompletion() {//下载成功
                release(aliMediaDownloader);//释放资源
                if (downloading != null)
                    downloading.remove(viodBean);
                if (classDownloadListener != null) {
                    classDownloadListener.onCompletion(viodBean);
                }

            }
        });

        aliMediaDownloader.setOnErrorListener(new AliMediaDownloader.OnErrorListener() {
            @Override
            public void onError(ErrorInfo errorInfo) { //下载出错
                release(aliMediaDownloader);//释放资源
                if (classDownloadListener != null) {
                    classDownloadListener.onError(viodBean, errorInfo);
                }

            }
        });
    }


    private void addDownloadingQueue(ViodBean viodBean) {
        if (downloading.size() >= mMaxNum) {//超出最大下载数量
            return;
        }

        for (ViodBean viodBean1 : downloading) {
            if (viodBean.equals(viodBean1)) {//已经存在了
                return;
            }
        }
        downloading.add(viodBean);
    }

    //更新数据库
    private void upDateViodBean(ViodBean viodBean) {
        List<ViodBean> downloadMediaInfos = MyRoomDatabase.getInstance().viodBeanDao().getAllViodBeans();
        boolean hasContains = false;
        for (ViodBean viodBean1 : downloadMediaInfos) {
            hasContains = viodBean.equals(viodBean1);
            if (hasContains) {
                break;
            }
        }
        if (hasContains) {
            MyRoomDatabase.getInstance().viodBeanDao().upDateViodBean(viodBean);
        } else {
            MyRoomDatabase.getInstance().viodBeanDao().insertViodBean(viodBean);
        }
    }


    /**
     * 设置下载对外监听
     */
    public void setDownloadInfoListener(DownloadClassListener listener) {
        outClassDownloadListeners.clear();
        if (listener != null) {
            outClassDownloadListeners.add(listener);
        }
    }

    /**
     * 添加下载对外监听
     */
    public void addDownloadInfoListener(DownloadClassListener listener) {
        if (outClassDownloadListeners == null) {
            outClassDownloadListeners = new ArrayList<>();
        }
        if (listener != null) {
            outClassDownloadListeners.add(listener);
        }
    }


    /**
     * 移除单个监听
     *
     * @param classDownloadListener
     */
    public void clearListener(DownloadClassListener classDownloadListener) {
        if (outClassDownloadListeners != null) {
            outClassDownloadListeners.remove(classDownloadListener);
        }
    }

    /**
     * 移除全部监听
     *
     * @param classDownloadListener
     */
    public void clearAllListener(DownloadClassListener classDownloadListener) {
        if (outClassDownloadListeners != null) {
            outClassDownloadListeners.clear();
        }
    }


    /**
     * 继续下载
     *
     * @param viodBean
     */
    public void continueDownload(ViodBean viodBean) {
        if (viodBean == null)
            return;
        AliMediaDownloader aliMediaDownloader = downloadInfos.get(viodBean.getId());
        if (aliMediaDownloader != null) {//存在直接下载
            if (classDownloadListener != null)
                classDownloadListener.onStart(viodBean);//开始监听回调
            aliMediaDownloader.start();
        } else {//不存在则创建后在下载
            if (viodBean.getVidAuth() == null) {
                if (classDownloadListener != null)
                    classDownloadListener.againStart(viodBean);//重新获取vid
                return;
            }
            aliMediaDownloader = AliDownloaderFactory.create(mContext);
            aliMediaDownloader.setSaveDir(downloadDir);
            //
            downloadInfos.put(viodBean.getId(), aliMediaDownloader);
            if (classDownloadListener != null)
                classDownloadListener.onStart(viodBean);//开始监听回调
            setListener(viodBean, aliMediaDownloader);
            aliMediaDownloader.selectItem(viodBean.getQualityIndex());
            aliMediaDownloader.updateSource(viodBean.getVidAuth());
            aliMediaDownloader.start();
        }

    }

    /**
     * 停止全部下载
     */
    public void stopAllDownloads() {
        if (downloading != null) {
            for (ViodBean viodBean : downloading) {
                AliMediaDownloader aliMediaDownloader = downloadInfos.get(viodBean.getId());
                if (aliMediaDownloader != null)
                    aliMediaDownloader.stop();
                classDownloadListener.onStop(viodBean);
            }
        }
    }

    /**
     * 停止某个任务
     *
     * @param viodBean
     */
    public void stopDownload(ViodBean viodBean) {
        if (viodBean == null || downloadInfos == null) {
            return;
        }
        AliMediaDownloader aliMediaDownloader = downloadInfos.get(viodBean.getId());
        if (aliMediaDownloader != null)
            aliMediaDownloader.stop();
        classDownloadListener.onStop(viodBean);
    }

    /**
     * 下载成功后，调用release释放下载器。在onCompletion或者onError回调中：
     *
     * @param aliMediaDownloader
     */
    public void release(AliMediaDownloader aliMediaDownloader) {
        aliMediaDownloader.stop();
        aliMediaDownloader.release();
    }


    public void deleteFile(ViodBean viodBean) {
        AliMediaDownloader aliMediaDownloader = downloadInfos.get(viodBean.getId());
        if (aliMediaDownloader != null) {
            aliMediaDownloader.stop();
        }
        if (downloading != null)
            downloading.remove(viodBean);
        if (classDownloadListener != null)
            classDownloadListener.deleteFile(viodBean);

    }


    /**
     * 重置
     */
    public void release() {
        if (outClassDownloadListeners != null) {
            outClassDownloadListeners.clear();
            outClassDownloadListeners = null;
        }
        if (downloading != null) {
            downloading.clear();
            downloading = null;
        }
        if (downloadInfos != null) {
            downloadInfos.clear();
            downloadInfos = null;
        }
    }
}
