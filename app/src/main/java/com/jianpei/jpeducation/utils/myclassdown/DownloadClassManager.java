package com.jianpei.jpeducation.utils.myclassdown;

import android.content.Context;
import android.text.TextUtils;

import com.aliyun.downloader.AliDownloaderFactory;
import com.aliyun.downloader.AliMediaDownloader;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.player.source.VidAuth;
import com.jianpei.jpeducation.base.MyApplication;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.utils.classdownload.DownloadMediaInfo;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

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

    private Context mContext;
    private String downloadDir;//下载路径

    private LinkedHashMap<String, AliMediaDownloader> downloadInfos = new LinkedHashMap<>();


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

    public static volatile DownloadClassManager mInstance;

    public DownloadClassManager getInstance() {
        if (mInstance == null) {
            synchronized (DownloadClassManager.class) {
                if (mInstance == null)
                    mInstance = new DownloadClassManager();
            }
        }
        return mInstance;
    }

    public DownloadClassListener outClassDownloadListener;//对外监听

    public DownloadClassListener getOutClassDownloadListener() {
        return outClassDownloadListener;
    }

    public void setOutClassDownloadListener(DownloadClassListener outClassDownloadListener) {
        this.outClassDownloadListener = outClassDownloadListener;
    }

    //内部监听
    public DownloadClassListener classDownloadListener = new DownloadClassListener() {
        @Override
        public void onPrepared(List<TrackInfo> trackInfos, ViodBean viodBean) {
            if (outClassDownloadListener != null) {
                outClassDownloadListener.onPrepared(trackInfos, viodBean);
            }

        }

        @Override
        public void onStart(ViodBean viodBean) {
            if (outClassDownloadListener != null) {
                outClassDownloadListener.onStart(viodBean);
            }
        }

        @Override
        public void onStop(ViodBean viodBean) {
            if (outClassDownloadListener != null) {
                outClassDownloadListener.onStop(viodBean);
            }
        }

        @Override
        public void onProgress(ViodBean viodBean, int progress) {
            if (outClassDownloadListener != null) {
                outClassDownloadListener.onProgress(viodBean, progress);
            }
        }

        @Override
        public void onFileProgress(ViodBean viodBean, int progress) {
            if (outClassDownloadListener != null) {
                outClassDownloadListener.onFileProgress(viodBean, progress);
            }
        }

        @Override
        public void onError(ViodBean viodBean, ErrorInfo errorInfo) {
            if (outClassDownloadListener != null) {
                outClassDownloadListener.onError(viodBean, errorInfo);
            }
        }

        @Override
        public void onCompletion(ViodBean viodBean) {
            if (outClassDownloadListener != null) {
                outClassDownloadListener.onCompletion(viodBean);
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
        AliMediaDownloader aliMediaDownloader = downloadInfos.get(viodBean.getId());
        if (aliMediaDownloader != null) {//下载任务已经存在，请勿重复下载
            return;
        }
        AliMediaDownloader jniDownloader = AliDownloaderFactory.create(mContext);
        jniDownloader.prepare(vidAuth);
        jniDownloader.setOnPreparedListener(new AliMediaDownloader.OnPreparedListener() {
            @Override
            public void onPrepared(MediaInfo mediaInfo) {
                viodBean.setVidAuth(vidAuth);
                //返回清晰度
                classDownloadListener.onPrepared(mediaInfo.getTrackInfos(), viodBean);
                //
                jniDownloader.setSaveDir(downloadDir);
                downloadInfos.put(viodBean.getId(), jniDownloader);

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
        if (trackInfo == null || viodBean == null) {
            return;
        }
        viodBean.setQuality(trackInfo.getVodDefinition());
        viodBean.setmTrackInfo(trackInfo);
        viodBean.setQualityIndex(trackInfo.getIndex());
        viodBean.setFormat(trackInfo.getVodFormat());
        viodBean.setSize(trackInfo.getVodFileSize());
        viodBean.setmStatus(DownloadMediaInfo.Status.Prepare);
        AliMediaDownloader aliMediaDownloader = downloadInfos.get(viodBean.getId());
        if (aliMediaDownloader == null) {
            return;
        }
        setListener(viodBean, aliMediaDownloader);
        aliMediaDownloader.selectItem(trackInfo.getIndex());
        aliMediaDownloader.updateSource(viodBean.getVidAuth());
        aliMediaDownloader.start();
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
                if (classDownloadListener != null) {
                    classDownloadListener.onCompletion(viodBean);
                }

            }
        });

        aliMediaDownloader.setOnErrorListener(new AliMediaDownloader.OnErrorListener() {
            @Override
            public void onError(ErrorInfo errorInfo) { //下载出错
                if (classDownloadListener != null) {
                    classDownloadListener.onError(viodBean, errorInfo);
                }

            }
        });


    }


}
