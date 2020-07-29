package com.jianpei.jpeducation.utils.classdownload;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;


import com.aliyun.downloader.AliDownloaderFactory;
import com.aliyun.downloader.AliMediaDownloader;
import com.aliyun.player.bean.ErrorCode;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.player.source.VidAuth;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class VideoDownloadManager {


    private Context mContext;

    private static volatile VideoDownloadManager mInstance = null;
    private static final int MAX_NUM = 5;
    private static final int MIN_NUM = 1;

    /**
     * AliyunDownloadMediaInfo和JniDownloader 一一 对应
     */
    private LinkedHashMap<AliyunDownloadMediaInfo, AliMediaDownloader> downloadInfos = new LinkedHashMap<>();

    /**
     * 对外接口回调
     */
    private List<AliyunDownloadInfoListener> outListenerList = new ArrayList<>();


    /**
     * 并行下载最大数量,默认3
     */
    private int mMaxNum = 3;

    /**
     * 下载路径
     */
    private String downloadDir = Environment.getDownloadCacheDirectory().getAbsolutePath() + "/AliPlayerDemoDownload";
    /**
     * 加密文件路径
     */
    private String encryptFilePath = "assets/encryptedApp.dat";

    /**
     * 剩余内存
     */
    private long freshStorageSizeTime = 0;

    /**
     * 内部接口回调
     */
    private AliyunDownloadInfoListener innerDownloadInfoListener = new AliyunDownloadInfoListener() {
        @Override
        public void onPrepared(List<AliyunDownloadMediaInfo> infos) {//准备完成
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (AliyunDownloadInfoListener aliyunDownloadInfoListener : outListenerList) {
                        aliyunDownloadInfoListener.onPrepared(infos);
                    }
                }
            });
        }

        @Override
        public void onAdd(AliyunDownloadMediaInfo info) {//添加

        }

        @Override
        public void onStart(AliyunDownloadMediaInfo info) {//开始

        }

        @Override
        public void onProgress(AliyunDownloadMediaInfo info, int percent) {//进度

        }

        @Override
        public void onStop(AliyunDownloadMediaInfo info) {//停止

        }

        @Override
        public void onCompletion(AliyunDownloadMediaInfo info) {//完成

        }

        @Override
        public void onError(AliyunDownloadMediaInfo info, ErrorCode code, String msg, String requestId) {//错误

        }

        @Override
        public void onWait(AliyunDownloadMediaInfo outMediaInfo) {//等待

        }

        @Override
        public void onDelete(AliyunDownloadMediaInfo info) {//删除

        }

        @Override
        public void onDeleteAll() {//全部删除

        }

        @Override
        public void onFileProgress(AliyunDownloadMediaInfo info) {//失败

        }
    };


    private VideoDownloadManager(Context context) {
        this.mContext = context;

    }

    public static VideoDownloadManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (VideoDownloadManager.class) {
                if (mInstance == null) {
                    mInstance = new VideoDownloadManager(context);
                }
            }

        }
        return mInstance;
    }

    /**
     * 设置最大并行下载行数
     */
    public int getMaxNum() {
        return mMaxNum;
    }

    /**
     * 设置并行下载行数
     * 最小为0,最大为5
     */
    public void setMaxNum(int mMaxNum) {
        if (mMaxNum <= MIN_NUM) {
            mMaxNum = MIN_NUM;
        }
        if (mMaxNum > MAX_NUM) {
            mMaxNum = MAX_NUM;
        }
        this.mMaxNum = mMaxNum;
    }

    public String getDownloadDir() {
        return downloadDir;
    }

    public void setDownloadDir(String downloadDir) {
        this.downloadDir = downloadDir;
    }

    public String getEncryptFilePath() {
        return encryptFilePath;
    }

    public void setEncryptFilePath(String encryptFilePath) {
        if (TextUtils.isEmpty(encryptFilePath)) {
            return;
        }
        this.encryptFilePath = encryptFilePath;
    }

    /**
     * 设置下载对外监听
     */
    public void setDownloadInfoListener(AliyunDownloadInfoListener listener) {
        this.outListenerList.clear();
        if (listener != null) {
            this.outListenerList.add(listener);
        }
    }

    /**
     * 准备下载项目
     *
     * @param vidAuth
     */
    public void prepareDownload(VidAuth vidAuth) {
        if (vidAuth == null || TextUtils.isEmpty(vidAuth.getVid())) {
            return;
        }
        final List<AliyunDownloadMediaInfo> downloadMediaInfos = new ArrayList<>();
        final AliMediaDownloader jniDownloader = AliDownloaderFactory.create(mContext);

        jniDownloader.setOnPreparedListener(new AliMediaDownloader.OnPreparedListener() {
            @Override
            public void onPrepared(MediaInfo mediaInfo) {
                List<TrackInfo> trackInfos = mediaInfo.getTrackInfos();
                for (TrackInfo trackInfo : trackInfos) {
                    TrackInfo.Type type = trackInfo.getType();
                    if (type == TrackInfo.Type.TYPE_VOD) {
                        //一个JniDownloader 对应多个 AliyunDownloaderMediaInfo(同一Vid,不同清晰度)
                        final AliyunDownloadMediaInfo downloadMediaInfo = new AliyunDownloadMediaInfo();
                        downloadMediaInfo.setVid(vidAuth.getVid());
                        downloadMediaInfo.setQuality(trackInfo.getVodDefinition());
                        downloadMediaInfo.setTitle(mediaInfo.getTitle());
                        downloadMediaInfo.setCoverUrl(mediaInfo.getCoverUrl());
                        downloadMediaInfo.setDuration(mediaInfo.getDuration());
                        downloadMediaInfo.setTrackInfo(trackInfo);
                        downloadMediaInfo.setQualityIndex(trackInfo.getIndex());
                        downloadMediaInfo.setFormat(trackInfo.getVodFormat());
                        downloadMediaInfo.setSize(trackInfo.getVodFileSize());
                        downloadMediaInfo.setStatus(AliyunDownloadMediaInfo.Status.Prepare);
                        downloadMediaInfo.setVidAuth(vidAuth);
                        downloadMediaInfos.add(downloadMediaInfo);

                        AliMediaDownloader itemJniDownloader = AliDownloaderFactory.create(mContext);
                        itemJniDownloader.setSaveDir(downloadDir);
                        downloadInfos.put(downloadMediaInfo, itemJniDownloader);
                    }


                }
                if (innerDownloadInfoListener != null) {
                    innerDownloadInfoListener.onPrepared(downloadMediaInfos);
                }
            }
        });
        jniDownloader.setOnErrorListener(new AliMediaDownloader.OnErrorListener() {
            @Override
            public void onError(ErrorInfo errorInfo) {

                if (innerDownloadInfoListener != null) {
                    AliyunDownloadMediaInfo mediaInfo = new AliyunDownloadMediaInfo();
                    mediaInfo.setVidAuth(vidAuth);
                    innerDownloadInfoListener.onError(mediaInfo, errorInfo.getCode(), errorInfo.getMsg(), null);
                }

            }
        });
        jniDownloader.prepare(vidAuth);
    }

}
