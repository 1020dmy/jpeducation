package com.jianpei.jpeducation.utils.classdownload;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.aliyun.downloader.AliDownloaderFactory;
import com.aliyun.downloader.AliMediaDownloader;
import com.aliyun.player.bean.ErrorCode;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.player.source.VidAuth;
import com.aliyun.vodplayerview.listener.RefreshStsCallback;
import com.jianpei.jpeducation.base.MyApplication;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.room.MyRoomDatabase;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class VideoDownloadManager {


    public static final String TAG = "AliyunDownloadManager";

    public static final String MEMORY_LESS_MSG = "memory_less";

    public static final int INTENT_STATE_START = 0;
    public static final int INTENT_STATE_STOP = 1;
    public static final int INTENT_STATE_ADD = 2;

    private static final int MAX_NUM = 5;
    private static final int MIN_NUM = 1;
    /**
     * 并行下载最大数量,默认3
     */
    private int mMaxNum = 3;
    /**
     * 下载路径
     */
    private String downloadDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AliPlayerDemoDownload";
//    /**
//     * 加密文件路径
//     */
//    private String encryptFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aliyunPlayer/encryptedApp.dat";

    /**
     * AliyunDownloadManager 单例
     */
    private static volatile VideoDownloadManager mInstance = null;

    /**
     * ViodBean和JniDownloader 一一 对应
     */
    private LinkedHashMap<ViodBean, AliMediaDownloader> downloadInfos = new LinkedHashMap<>();

    /**
     * 用于保存处于准备状态的数据
     */
    private ConcurrentLinkedQueue<ViodBean> preparedList = new ConcurrentLinkedQueue<>();

    /**
     * 用于保存处于下载中的状态的数据
     */
    private ConcurrentLinkedQueue<ViodBean> downloadingList = new ConcurrentLinkedQueue<>();

    /**
     * 用于保存下载完成状态的数据
     */
    private ConcurrentLinkedQueue<ViodBean> completedList = new ConcurrentLinkedQueue<>();

    /**
     * 用于保存暂停状态的数据
     */
    private ConcurrentLinkedQueue<ViodBean> waitedList = new ConcurrentLinkedQueue<>();

    /**
     * 用于保存停止状态的数据
     */
    private ConcurrentLinkedQueue<ViodBean> stopedList = new ConcurrentLinkedQueue<>();

    /**
     * 对外接口回调
     */
    private List<ClassDownloadListener> outListenerList = new ArrayList<>();

    /**
     * 数据库管理类
     */
//    private DatabaseManager mDatabaseManager;

    /**
     * 剩余内存
     */
    private long freshStorageSizeTime = 0;

    private Context mContext;

    /**
     * 内部接口回调
     */
    private ClassDownloadListener innerDownloadInfoListener = new ClassDownloadListener() {

        @Override
        public void onPrepared(final List<ViodBean> infos) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (ClassDownloadListener aliyunDownloadInfoListener : outListenerList) {
                        aliyunDownloadInfoListener.onPrepared(infos);
                    }
                }
            });

        }

        @Override
        public void onAdd(final ViodBean info) {
            ThreadUtils.runOnSubThread(new Runnable() {
                @Override
                public void run() {
                    prepareMediaInfo(info);
                    List<ViodBean> downloadMediaInfos = MyRoomDatabase.getInstance().viodBeanDao().getAllViodBeans();
                    if (downloadMediaInfos.contains(info)) {
                        MyRoomDatabase.getInstance().viodBeanDao().upDateViodBean(info);
                    } else {
                        MyRoomDatabase.getInstance().viodBeanDao().insertViodBean(info);
                    }

                }
            });
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (ClassDownloadListener aliyunDownloadInfoListener : outListenerList) {
                        aliyunDownloadInfoListener.onAdd(info);
                    }
                }
            });
        }

        @Override
        public void onStart(final ViodBean info) {
            info.setStatus(3);
            startMediaInfo(info);
            //在子线程中更新数据库
            ThreadUtils.runOnSubThread(new Runnable() {
                @Override
                public void run() {
                    List<ViodBean> downloadMediaInfos = MyRoomDatabase.getInstance().viodBeanDao().getAllViodBeans();
                    boolean hasContains = false;
                    for (ViodBean downloadMediaInfo : downloadMediaInfos) {
                        hasContains = judgeEquals(downloadMediaInfo, info);
                        if (hasContains) {
                            break;
                        }
                    }
                    if (hasContains) {
                        MyRoomDatabase.getInstance().viodBeanDao().upDateViodBean(info);
                    } else {
                        MyRoomDatabase.getInstance().viodBeanDao().insertViodBean(info);
                    }

                }
            });
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (ClassDownloadListener aliyunDownloadInfoListener : outListenerList) {
                        aliyunDownloadInfoListener.onStart(info);
                    }
                }
            });

        }

        @Override
        public void onProgress(final ViodBean info, final int percent) {
            //在子线程中更新数据库
            ThreadUtils.runOnSubThread(new Runnable() {
                @Override
                public void run() {
                    if (freshStorageSizeTime == 0 || ((new Date()).getTime() - freshStorageSizeTime) > 2 * 1000) {
                        MyRoomDatabase.getInstance().viodBeanDao().upDateViodBean(info);
                        if (com.aliyun.vodplayerview.utils.download.DownloadUtils.isStorageAlarm(mContext)) {
                            ThreadUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    stopDownloads(downloadingList);
                                    stopDownloads(waitedList);
                                    for (ClassDownloadListener aliyunDownloadInfoListener : outListenerList) {
                                        aliyunDownloadInfoListener.onError(info, ErrorCode.ERROR_UNKNOWN_ERROR, MEMORY_LESS_MSG, null);
                                    }
                                }
                            });
                        }
                        freshStorageSizeTime = (new Date()).getTime();
                    }
                }
            });

            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (ClassDownloadListener aliyunDownloadInfoListener : outListenerList) {
                        info.setmStatus(DownloadMediaInfo.Status.Start);
                        aliyunDownloadInfoListener.onProgress(info, percent);
                    }
                }
            });

        }

        @Override
        public void onWait(final ViodBean outMediaInfo) {
            waitMediaInfo(outMediaInfo);
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (ClassDownloadListener aliyunDownloadInfoListener : outListenerList) {
                        aliyunDownloadInfoListener.onWait(outMediaInfo);
                    }
                }
            });

        }

        @Override
        public void onDelete(final ViodBean info) {
            deleteMediaInfo(info);
            MyRoomDatabase.getInstance().viodBeanDao().delete(info);
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (ClassDownloadListener aliyunDownloadInfoListener : outListenerList) {
                        aliyunDownloadInfoListener.onDelete(info);
                    }
                }
            });

        }

        @Override
        public void onDeleteAll() {
            deleteAllMediaInfo();
//            MyRoomDatabase.getInstance().viodBeanDao().deleteAll();
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (ClassDownloadListener aliyunDownloadInfoListener : outListenerList) {
                        aliyunDownloadInfoListener.onDeleteAll();
                    }
                }
            });

        }

        @Override
        public void onFileProgress(final ViodBean mediaInfo) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (ClassDownloadListener aliyunDownloadInfoListener : outListenerList) {
                        mediaInfo.setmStatus(DownloadMediaInfo.Status.File);
                        aliyunDownloadInfoListener.onFileProgress(mediaInfo);
                    }
                }
            });
        }

        @Override
        public void onStop(final ViodBean info) {
            stopMediaInfo(info);
            ThreadUtils.runOnSubThread(new Runnable() {
                @Override
                public void run() {
                    MyRoomDatabase.getInstance().viodBeanDao().upDateViodBean(info);
                }
            });
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (ClassDownloadListener aliyunDownloadInfoListener : outListenerList) {
                        aliyunDownloadInfoListener.onStop(info);
                    }
                }
            });

        }

        @Override
        public void onCompletion(final ViodBean info) {
            completedMediaInfo(info);
            AliMediaDownloader jniDownloader = downloadInfos.get(info);
            if (jniDownloader == null) {
                return;
            }
            info.setSavePath(jniDownloader.getFilePath());
            info.setStatus(5);
            ThreadUtils.runOnSubThread(new Runnable() {
                @Override
                public void run() {
                    MyRoomDatabase.getInstance().viodBeanDao().upDateViodBean(info);
                }
            });
            for (ClassDownloadListener aliyunDownloadInfoListener : outListenerList) {
                aliyunDownloadInfoListener.onCompletion(info);
            }
        }

        @Override
        public void onError(final ViodBean info, final ErrorCode code, final String msg, final String requestId) {
            errorMediaInfo(info, code, msg);
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (ClassDownloadListener aliyunDownloadInfoListener : outListenerList) {
                        aliyunDownloadInfoListener.onError(info, code, msg, requestId);
                    }
                }
            });
        }

    };

    private VideoDownloadManager() {
        this.mContext = MyApplication.getInstance();
//        mDatabaseManager = DatabaseManager.getInstance();
        if (!TextUtils.isEmpty(downloadDir)) {
            File file = new File(downloadDir);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    public static VideoDownloadManager getInstance() {
        if (mInstance == null) {
            synchronized (VideoDownloadManager.class) {
                if (mInstance == null) {
                    mInstance = new VideoDownloadManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 设置下载对外监听
     */
    public void setDownloadInfoListener(ClassDownloadListener listener) {
//        this.outListenerList.clear();
        if (listener != null) {
            this.outListenerList.add(listener);
        }
    }

    /**
     * 添加下载对外监听
     */
    public void addDownloadInfoListener(ClassDownloadListener listener) {
        if (this.outListenerList == null) {
            this.outListenerList = new ArrayList<>();
        }
        if (listener != null) {
            this.outListenerList.add(listener);
        }
    }

    /**
     * 判断两个MediaInfo是否属于同一资源
     */
    private boolean judgeEquals(ViodBean mediaInfo1, ViodBean mediaInfo2) {
        if (mediaInfo1.getId().equals(mediaInfo2.getId()) && mediaInfo1.getQuality().equals(mediaInfo2.getQuality())
                && mediaInfo1.getFormat().equals(mediaInfo2.getFormat())) {
            return true;
        } else {
            return false;
        }
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

//    public String getEncryptFilePath() {
//        return encryptFilePath;
//    }
//
//    public void setEncryptFilePath(String encryptFilePath) {
//        if (TextUtils.isEmpty(encryptFilePath)) {
//            return;
//        }
//        this.encryptFilePath = encryptFilePath;
//    }

    /**
     * 准备下载项
     * 用于从数据库查询出数据后，恢复数据展示
     */
    private void prepareDownload(VidAuth vidAuth, final List<ViodBean> mediaInfos) {
        if (vidAuth == null || mediaInfos == null) {
            return;
        }
        for (final ViodBean aliyunViodBean : mediaInfos) {
            vidAuth.setVid(aliyunViodBean.getVid());
            aliyunViodBean.setVidAuth(vidAuth);
            //修改成wait状态
            if (aliyunViodBean.getmStatus() == DownloadMediaInfo.Status.Start ||
                    aliyunViodBean.getmStatus() == DownloadMediaInfo.Status.Prepare) {
                aliyunViodBean.setmStatus(DownloadMediaInfo.Status.Stop);
            }
            final AliMediaDownloader jniDownloader = AliDownloaderFactory.create(mContext);
            jniDownloader.setSaveDir(downloadDir);
            jniDownloader.setOnPreparedListener(new AliMediaDownloader.OnPreparedListener() {
                @Override
                public void onPrepared(MediaInfo mediaInfo) {
                    if (downloadInfos != null && mediaInfo.getVideoId().equals(aliyunViodBean.getVid())) {
                        List<TrackInfo> trackInfos = mediaInfo.getTrackInfos();
                        for (TrackInfo trackInfo : trackInfos) {
                            if (trackInfo != null &&
                                    trackInfo.getVodDefinition().equals(aliyunViodBean.getQuality())) {
                                //ViodBean 与 AliMediaDownloader 相对应
                                aliyunViodBean.setmTrackInfo(trackInfo);
                                downloadInfos.put(aliyunViodBean, jniDownloader);
                            }
                        }

                    }
                }
            });

            jniDownloader.setOnErrorListener(new AliMediaDownloader.OnErrorListener() {
                @Override
                public void onError(ErrorInfo errorInfo) {
                    if (innerDownloadInfoListener != null) {
                        innerDownloadInfoListener.onError(aliyunViodBean, errorInfo.getCode(), errorInfo.getMsg(), errorInfo.getExtra());
                    }
                }
            });

            jniDownloader.prepare(vidAuth);
        }
    }

    /**
     * 添加下载项
     */
    public void addDownload(VidAuth vidAuth, final ViodBean aliyunViodBean) {
        if (vidAuth == null || aliyunViodBean == null) {
            return;
        }
        if (preparedList.contains(aliyunViodBean) || stopedList.contains(aliyunViodBean)
                || waitedList.contains(aliyunViodBean) || downloadingList.contains(aliyunViodBean)
                || completedList.contains(aliyunViodBean)) {
            return;
        }
        vidAuth.setVid(aliyunViodBean.getVid());
        aliyunViodBean.setVidAuth(vidAuth);
        AliMediaDownloader jniDownloader = downloadInfos.get(aliyunViodBean);
        if (jniDownloader == null || aliyunViodBean.getmTrackInfo() == null) {
            prepareDownloadByQuality(aliyunViodBean, INTENT_STATE_ADD);
        } else {
            jniDownloader.setSaveDir(downloadDir);
            jniDownloader.selectItem(aliyunViodBean.getmTrackInfo().getIndex());
            if (innerDownloadInfoListener != null) {
                innerDownloadInfoListener.onAdd(aliyunViodBean);
            }

            jniDownloader.setOnErrorListener(new AliMediaDownloader.OnErrorListener() {
                @Override
                public void onError(ErrorInfo errorInfo) {
                    if (innerDownloadInfoListener != null) {
                        innerDownloadInfoListener.onError(aliyunViodBean, errorInfo.getCode(), errorInfo.getMsg(), errorInfo.getExtra());
                    }
                }
            });
        }
    }

    /**
     * 准备下载项目
     */
    public void prepareDownload(final VidAuth vidAuth, ViodBean viodBean) {
        if (vidAuth == null || TextUtils.isEmpty(vidAuth.getVid())) {
            return;
        }
        final List<ViodBean> downloadMediaInfos = new ArrayList<>();
        final AliMediaDownloader jniDownloader = AliDownloaderFactory.create(mContext);
        //调用prepared监听,获取该vid下所有的清晰度
        jniDownloader.setOnPreparedListener(new AliMediaDownloader.OnPreparedListener() {
            @Override
            public void onPrepared(MediaInfo mediaInfo) {
                List<TrackInfo> trackInfos = mediaInfo.getTrackInfos();
                for (TrackInfo trackInfo : trackInfos) {
                    TrackInfo.Type type = trackInfo.getType();
                    if (type == TrackInfo.Type.TYPE_VOD) {
//                        //一个JniDownloader 对应多个 AliyunDownloaderMediaInfo(同一Vid,不同清晰度)
                        final ViodBean downloadMediaInfo = new ViodBean();
                        downloadMediaInfo.setId(viodBean.getId());
                        downloadMediaInfo.setTitle(viodBean.getTitle());
                        downloadMediaInfo.setIsfree(viodBean.getIsfree());
                        downloadMediaInfo.setChapter_id(viodBean.getChapter_id());
                        downloadMediaInfo.setDqtime(viodBean.getDqtime());
                        downloadMediaInfo.setTotaltime(viodBean.getTotaltime());
                        downloadMediaInfo.setIs_last_read(viodBean.getIs_last_read());
                        downloadMediaInfo.setSchedule(viodBean.getSchedule());


                        downloadMediaInfo.setVid(vidAuth.getVid());
                        downloadMediaInfo.setQuality(trackInfo.getVodDefinition());
                        downloadMediaInfo.setTitle(mediaInfo.getTitle());
                        downloadMediaInfo.setCoverUrl(mediaInfo.getCoverUrl());
                        downloadMediaInfo.setDuration(mediaInfo.getDuration());
                        downloadMediaInfo.setmTrackInfo(trackInfo);
                        downloadMediaInfo.setQualityIndex(trackInfo.getIndex());
                        downloadMediaInfo.setFormat(trackInfo.getVodFormat());
                        downloadMediaInfo.setSize(trackInfo.getVodFileSize());
                        downloadMediaInfo.setmStatus(DownloadMediaInfo.Status.Prepare);
                        downloadMediaInfo.setVidAuth(vidAuth);
                        downloadMediaInfos.add(downloadMediaInfo);

                        AliMediaDownloader itemJniDownloader = AliDownloaderFactory.create(mContext);
                        itemJniDownloader.setSaveDir(downloadDir);
                        downloadInfos.put(downloadMediaInfo, itemJniDownloader);
                    }
                }
                if (innerDownloadInfoListener != null) {
                    //这里回调只为了展示可下载的选项
                    innerDownloadInfoListener.onPrepared(downloadMediaInfos);
                }
            }
        });
        jniDownloader.setOnErrorListener(new AliMediaDownloader.OnErrorListener() {
            @Override
            public void onError(ErrorInfo errorInfo) {
                if (innerDownloadInfoListener != null) {
                    ViodBean mediaInfo = new ViodBean();
                    mediaInfo.setVidAuth(vidAuth);
                    innerDownloadInfoListener.onError(mediaInfo, errorInfo.getCode(), errorInfo.getMsg(), null);
                }
            }
        });

        jniDownloader.prepare(vidAuth);
    }

    /**
     * 准备下载项(指定清晰度)
     */
    public void prepareDownloadByQuality(final ViodBean downloadMediaInfo, final int intentState) {
        if (downloadMediaInfo == null || downloadMediaInfo.getVidAuth() == null) {
            return;
        }
        final List<ViodBean> downloadMediaInfos = new ArrayList<>();
        final AliMediaDownloader jniDownloader = AliDownloaderFactory.create(mContext);
        jniDownloader.setSaveDir(downloadDir);
        //调用prepared监听,获取该vid下所有的清晰度
        jniDownloader.setOnPreparedListener(new AliMediaDownloader.OnPreparedListener() {
            @Override
            public void onPrepared(MediaInfo mediaInfo) {
                List<TrackInfo> trackInfos = mediaInfo.getTrackInfos();
                for (TrackInfo trackInfo : trackInfos) {
                    TrackInfo.Type type = trackInfo.getType();
                    if (type == TrackInfo.Type.TYPE_VOD && trackInfo.getVodDefinition().equals(downloadMediaInfo.getQuality())) {
//                        //一个JniDownloader 对应多个 AliyunDownloaderMediaInfo(同一Vid,不同清晰度)
                        downloadMediaInfo.setQuality(trackInfo.getVodDefinition());
                        downloadMediaInfo.setTitle(mediaInfo.getTitle());
                        downloadMediaInfo.setCoverUrl(mediaInfo.getCoverUrl());
                        downloadMediaInfo.setDuration(mediaInfo.getDuration());
                        downloadMediaInfo.setmTrackInfo(trackInfo);
                        downloadMediaInfo.setQualityIndex(trackInfo.getIndex());
                        downloadMediaInfo.setFormat(trackInfo.getVodFormat());
                        downloadMediaInfo.setSize(trackInfo.getVodFileSize());
                        downloadMediaInfo.setmStatus(DownloadMediaInfo.Status.Prepare);
                        downloadMediaInfos.add(downloadMediaInfo);

                        downloadInfos.put(downloadMediaInfo, jniDownloader);

                        jniDownloader.selectItem(trackInfo.getIndex());
                        if (intentState == INTENT_STATE_START) {
                            if (downloadingList.size() <= mMaxNum) {
                                //开始下载
                                setListener(downloadMediaInfo, jniDownloader);
                                jniDownloader.start();
                                if (innerDownloadInfoListener != null) {
                                    innerDownloadInfoListener.onStart(downloadMediaInfo);
                                }
                            } else {
                                if (innerDownloadInfoListener != null) {
                                    innerDownloadInfoListener.onWait(downloadMediaInfo);
                                }
                            }

                        } else if (intentState == INTENT_STATE_STOP) {
                            //删除下载
                            executeDelete(downloadMediaInfo);
                        } else {
                            //添加下载项
                            jniDownloader.setSaveDir(downloadDir);
                            jniDownloader.selectItem(downloadMediaInfo.getmTrackInfo().getIndex());
                            if (innerDownloadInfoListener != null) {
                                innerDownloadInfoListener.onAdd(downloadMediaInfo);
                            }

                            jniDownloader.setOnErrorListener(new AliMediaDownloader.OnErrorListener() {
                                @Override
                                public void onError(ErrorInfo errorInfo) {
                                    if (innerDownloadInfoListener != null) {
                                        innerDownloadInfoListener.onError(downloadMediaInfo, errorInfo.getCode(), errorInfo.getMsg(), errorInfo.getExtra());
                                    }
                                }
                            });
                        }

                    }
                }
            }
        });
        jniDownloader.setOnErrorListener(new AliMediaDownloader.OnErrorListener() {
            @Override
            public void onError(ErrorInfo errorInfo) {
                if (innerDownloadInfoListener != null) {
                    innerDownloadInfoListener.onError(null, errorInfo.getCode(), errorInfo.getMsg(), null);
                }
            }
        });

        jniDownloader.prepare(downloadMediaInfo.getVidAuth());
    }

    /**
     * 开始下载
     */
    public void startDownload(final ViodBean downloadMediaInfo) {
        if (downloadMediaInfo == null) {
            return;
        }
        DownloadMediaInfo.Status status = downloadMediaInfo.getmStatus();
        if (status == DownloadMediaInfo.Status.Start
                || downloadingList.contains(downloadMediaInfo)) {
            return;
        }
        if (downloadMediaInfo.getmStatus() == DownloadMediaInfo.Status.Complete) {
            String savePath = downloadMediaInfo.getSavePath();
            File file = new File(savePath);
            if (file.exists()) {
                Toast.makeText(mContext.getApplicationContext(), mContext.getResources().getString(com.aliyun.vodplayer.R.string.alivc_video_download_finish_tips), Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (innerDownloadInfoListener != null) {
            innerDownloadInfoListener.onStart(downloadMediaInfo);
        }

        //如果没有sts，则是恢复数据的操作，需要重新请求sts
//        if (downloadMediaInfo.getVidAuth() == null) {
//            getVidSts(downloadMediaInfo, INTENT_STATE_START);
//        } else {
        //直接开始下载
        //判断磁盘空间是否足够
        if (DownloadUtils.isStorageAlarm(mContext, downloadMediaInfo)) {
            //判断要下载的mediaInfo的当前状态
            if (downloadingList.size() <= mMaxNum) {
                TrackInfo trackInfo = downloadMediaInfo.getmTrackInfo();
                AliMediaDownloader jniDownloader = downloadInfos.get(downloadMediaInfo);
                if (jniDownloader == null || trackInfo == null) {
                    if (innerDownloadInfoListener != null) {
                        innerDownloadInfoListener.onError(downloadMediaInfo, ErrorCode.ERROR_UNKNOWN_ERROR, mContext.getResources().getString(com.aliyun.vodplayer.R.string.alivc_player_redownload), null);
                    }
                    return;
                }
                jniDownloader.selectItem(trackInfo.getIndex());
                setListener(downloadMediaInfo, jniDownloader);
                jniDownloader.updateSource(downloadMediaInfo.getVidAuth());
                jniDownloader.start();
            } else {
                //防止重复添加
                if (!waitedList.contains(downloadMediaInfo) && innerDownloadInfoListener != null) {
                    innerDownloadInfoListener.onWait(downloadMediaInfo);
                }
            }
        } else {
            if (innerDownloadInfoListener != null) {
                innerDownloadInfoListener.onError(downloadMediaInfo, ErrorCode.ERROR_UNKNOWN_ERROR, MEMORY_LESS_MSG, null);
            }
        }
//        }
    }

    /**
     * 停止下载
     */
    public void stopDownload(ViodBean downloadMediaInfo) {
        if (downloadMediaInfo == null || downloadInfos == null) {
            return;
        }
        if (downloadMediaInfo.getmStatus() == DownloadMediaInfo.Status.Complete ||
                downloadMediaInfo.getmStatus() == DownloadMediaInfo.Status.Error
                || downloadMediaInfo.getmStatus() == DownloadMediaInfo.Status.Stop) {
            return;

        }
        AliMediaDownloader jniDownloader = downloadInfos.get(downloadMediaInfo);
        if (jniDownloader == null) {
            return;
        }
        jniDownloader.stop();
        if (innerDownloadInfoListener != null) {
            innerDownloadInfoListener.onStop(downloadMediaInfo);
        }
        autoDownload();
    }

    /**
     * 停止多个下载
     */
    public void stopDownloads(ConcurrentLinkedQueue<ViodBean> downloadMediaInfos) {
        if (downloadMediaInfos == null || downloadMediaInfos.size() == 0 || downloadInfos == null) {
            return;
        }
        for (ViodBean downloadMediaInfo : downloadMediaInfos) {
            if (downloadMediaInfo.getmStatus() == DownloadMediaInfo.Status.Start ||
                    downloadMediaInfo.getmStatus() == DownloadMediaInfo.Status.Wait) {
                AliMediaDownloader jniDownloader = downloadInfos.get(downloadMediaInfo);
                if (jniDownloader == null || downloadMediaInfo.getmStatus() != DownloadMediaInfo.Status.Start) {
                    continue;
                }
                jniDownloader.stop();
                if (innerDownloadInfoListener != null) {
                    innerDownloadInfoListener.onStop(downloadMediaInfo);
                }
            }
        }
    }


    /**
     * 删除下载文件
     */
    public void deleteFile(final ViodBean downloadMediaInfo) {
        if (downloadMediaInfo == null || downloadInfos == null) {
            Log.e(TAG, "deleteFile ERROR  downloadMediaInfo == null || downloadInfos == null");
            return;
        }

        if (downloadMediaInfo.getmStatus() == DownloadMediaInfo.Status.Delete) {
            return;

        }
        downloadMediaInfo.setmStatus(DownloadMediaInfo.Status.Delete);

        executeDelete(downloadMediaInfo);
    }

    /**
     * 执行删除操作
     */
    private void executeDelete(ViodBean downloadMediaInfo) {
        AliMediaDownloader jniDownloader = downloadInfos.get(downloadMediaInfo);

        if (downloadMediaInfo == null) {
            if (innerDownloadInfoListener != null) {
                innerDownloadInfoListener.onError(downloadMediaInfo, ErrorCode.ERROR_UNKNOWN_ERROR, mContext.getResources().getString(com.aliyun.vodplayer.R.string.alivc_player_delete_failed), null);
            }
            return;
        }
        String saveDir = getDownloadDir();
        String vid = downloadMediaInfo.getVid();
        String format = downloadMediaInfo.getFormat();
        int index = downloadMediaInfo.getQualityIndex();

        if (jniDownloader != null) {
            jniDownloader.stop();
        }
        int ret = AliDownloaderFactory.deleteFile(saveDir, vid, format, index);
        if (ret == 12 || ret == 11) { //删除失败。TODO 映射到java层
            Log.w(TAG, "deleteFile warning  ret = " + ret);
            //删除下载需要选择哪个清晰度,否则无法删除本地文件
//            jniDownloader.selectItem(index);
//            jniDownloader.deleteFile();
            if (innerDownloadInfoListener != null) {
                innerDownloadInfoListener.onError(downloadMediaInfo, ErrorCode.ERROR_UNKNOWN_ERROR, mContext.getResources().getString(com.aliyun.vodplayer.R.string.alivc_player_delete_failed), null);
            }
        }

        if (innerDownloadInfoListener != null) {
            innerDownloadInfoListener.onDelete(downloadMediaInfo);
        }
        autoDownload();
    }


    /**
     * 获取sts信息
     */
//    private void getVidSts(final ViodBean downloadMediaInfo, final int intentState) {
//        VidStsUtil.getVidSts(PlayParameter.PLAY_PARAM_VID, new VidStsUtil.OnStsResultListener() {
//            @Override
//            public void onSuccess(String vid, String akid, String akSecret, String token) {
//                VidSts vidSts = new VidSts();
//                vidSts.setVid(downloadMediaInfo.getVid());
//                vidSts.setRegion("cn-shanghai");
//                vidSts.setAccessKeyId(akid);
//                vidSts.setSecurityToken(token);
//                vidSts.setAccessKeySecret(akSecret);
//                vidSts.setQuality(downloadMediaInfo.getQuality(), false);
//                downloadMediaInfo.setVidSts(vidSts);
//                prepareDownloadByQuality(downloadMediaInfo, intentState);
//            }
//
//            @Override
//            public void onFail() {
//                ThreadUtils.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(mContext.getApplicationContext(), mContext.getResources().getString(com.aliyun.vodplayer.R.string.alivc_player_get_sts_failed), Toast.LENGTH_SHORT).show();
//                        if (innerDownloadInfoListener != null) {
//                            innerDownloadInfoListener.onError(downloadMediaInfo, ErrorCode.ERROR_UNKNOWN_ERROR, mContext.getResources().getString(com.aliyun.vodplayer.R.string.alivc_player_get_sts_failed), null);
//                        }
//                    }
//                });
//            }
//        });
//    }

    /**
     * 删除所有文件
     */
    public void deleteAllFile() {
        for (ViodBean mediaInfo : preparedList) {
            deleteFile(mediaInfo);
        }

        for (ViodBean mediaInfo : downloadingList) {
            deleteFile(mediaInfo);
        }

        for (ViodBean mediaInfo : completedList) {
            deleteFile(mediaInfo);
        }

        for (ViodBean mediaInfo : waitedList) {
            deleteFile(mediaInfo);
        }

        for (ViodBean mediaInfo : stopedList) {
            deleteFile(mediaInfo);
        }
    }

    /**
     * 设置监听
     */
    private void setListener(final ViodBean downloadMediaInfo, final AliMediaDownloader jniDownloader) {
        jniDownloader.setOnProgressListener(new AliMediaDownloader.OnProgressListener() {

            @Override
            public void onDownloadingProgress(int percent) {
                String downloadFilePath = jniDownloader.getFilePath();

                if (!TextUtils.isEmpty(downloadFilePath)) {
                    downloadMediaInfo.setSavePath(downloadFilePath);
                } else {
                    downloadMediaInfo.setSavePath("");
                }
                if (innerDownloadInfoListener != null) {
                    downloadMediaInfo.setProgress(percent);
                    innerDownloadInfoListener.onProgress(downloadMediaInfo, percent);
                }
            }

            @Override
            public void onProcessingProgress(int percent) {
                Log.i(TAG, "onProcessingProgress" + percent);
                if (innerDownloadInfoListener != null) {
                    downloadMediaInfo.setmFileHandleProgress(percent);
                    innerDownloadInfoListener.onFileProgress(downloadMediaInfo);
                }
            }
        });

        jniDownloader.setOnCompletionListener(new AliMediaDownloader.OnCompletionListener() {
            @Override
            public void onCompletion() {
                if (innerDownloadInfoListener != null) {
                    innerDownloadInfoListener.onCompletion(downloadMediaInfo);
                }
            }
        });

        jniDownloader.setOnErrorListener(new AliMediaDownloader.OnErrorListener() {
            @Override
            public void onError(ErrorInfo errorInfo) {
                if (innerDownloadInfoListener != null) {
                    innerDownloadInfoListener.onError(downloadMediaInfo, errorInfo.getCode(), errorInfo.getMsg(), "");
                }
            }
        });
    }

    /**
     * 初始化正在下载的缓存
     */
    public void initDownloading(LinkedList<ViodBean> list) {
        if (downloadingList.size() != 0) {
            downloadingList.clear();
        }
        downloadingList.addAll(list);
    }

    /**
     * 初始化下载完成的缓存
     */
    public void initCompleted(LinkedList<ViodBean> list) {
        if (completedList.size() != 0) {
            completedList.clear();
        }
        completedList.addAll(list);
    }

    /**
     * 自动开始等待中的下载任务
     */
    private void autoDownload() {
        //当前下载数小于设置的最大值,并且还有在等待中的下载任务
        if (downloadingList.size() < mMaxNum && waitedList.size() > 0) {
            ViodBean aliyunViodBean = waitedList.peek();
            if (aliyunViodBean.getmStatus() == DownloadMediaInfo.Status.Wait) {
                startDownload(aliyunViodBean);
            }
        }
    }

    private void deleteAllMediaInfo() {
        preparedList.clear();
        waitedList.clear();
        downloadingList.clear();
        stopedList.clear();
        completedList.clear();
        downloadInfos.clear();
    }

    private void deleteMediaInfo(ViodBean downloadMediaInfo) {
        Iterator<ViodBean> preparedIterator = preparedList.iterator();
        while (preparedIterator.hasNext()) {
            if (preparedIterator.next().equals(downloadMediaInfo)) {
                preparedIterator.remove();
            }
        }

        Iterator<ViodBean> waitedIterator = waitedList.iterator();
        while (waitedIterator.hasNext()) {
            if (waitedIterator.next().equals(downloadMediaInfo)) {
                waitedIterator.remove();
            }
        }

        Iterator<ViodBean> downloadingIterator = downloadingList.iterator();
        while (downloadingIterator.hasNext()) {
            if (downloadingIterator.next().equals(downloadMediaInfo)) {
                downloadingIterator.remove();
            }
        }

        Iterator<ViodBean> stopedIterator = stopedList.iterator();
        while (stopedIterator.hasNext()) {
            if (stopedIterator.next().equals(downloadMediaInfo)) {
                stopedIterator.remove();
            }
        }

        Iterator<ViodBean> completedIterator = completedList.iterator();
        while (completedIterator.hasNext()) {
            if (completedIterator.next().equals(downloadMediaInfo)) {
                completedIterator.remove();
            }
        }
        downloadInfos.remove(downloadMediaInfo);
    }

    private void waitMediaInfo(ViodBean downloadMediaInfo) {
        if (!waitedList.contains(downloadMediaInfo) && downloadMediaInfo != null) {
            waitedList.add(downloadMediaInfo);
        }
        preparedList.remove(downloadMediaInfo);
        downloadingList.remove(downloadMediaInfo);
        downloadMediaInfo.setmStatus(DownloadMediaInfo.Status.Wait);
    }

    private void stopMediaInfo(ViodBean downloadMediaInfo) {
        if (!stopedList.contains(downloadMediaInfo) && downloadMediaInfo != null) {
            stopedList.add(downloadMediaInfo);
        }
        downloadingList.remove(downloadMediaInfo);
        preparedList.remove(downloadMediaInfo);
        downloadMediaInfo.setmStatus(DownloadMediaInfo.Status.Stop);
    }

    private void prepareMediaInfo(ViodBean downloadMediaInfo) {
        if (!preparedList.contains(downloadMediaInfo) && downloadMediaInfo != null) {
            preparedList.add(downloadMediaInfo);
        }
        downloadingList.remove(downloadMediaInfo);
        completedList.remove(downloadMediaInfo);
        downloadMediaInfo.setmStatus(DownloadMediaInfo.Status.Prepare);
    }

    private void startMediaInfo(ViodBean downloadMediaInfo) {
        if (!downloadingList.contains(downloadMediaInfo) && downloadMediaInfo != null) {
            downloadingList.add(downloadMediaInfo);
        }
        preparedList.remove(downloadMediaInfo);
        stopedList.remove(downloadMediaInfo);
        completedList.remove(downloadMediaInfo);
        waitedList.remove(downloadMediaInfo);
        downloadMediaInfo.setmStatus(DownloadMediaInfo.Status.Start);
    }

    private void completedMediaInfo(ViodBean downloadMediaInfo) {
        if (!completedList.contains(downloadMediaInfo) && downloadMediaInfo != null) {
            completedList.add(downloadMediaInfo);
        }
        downloadingList.remove(downloadMediaInfo);
        downloadMediaInfo.setmStatus(DownloadMediaInfo.Status.Complete);
        autoDownload();
    }

    private void errorMediaInfo(ViodBean downloadMediaInfo, ErrorCode code, String msg) {
        //在prepare的时候,如果获取不到MediaInfo,downloadMediaInfo会作为空值传递,所以会导致空指针异常
        if (downloadMediaInfo == null) {
            return;
        }
        if (!stopedList.contains(downloadMediaInfo) && downloadMediaInfo != null) {
            stopedList.add(downloadMediaInfo);
        }
        preparedList.remove(downloadMediaInfo);
        downloadingList.remove(downloadMediaInfo);
        completedList.remove(downloadMediaInfo);
        waitedList.remove(downloadMediaInfo);
        downloadMediaInfo.setmStatus(DownloadMediaInfo.Status.Error);
        downloadMediaInfo.setErrorCode(code);
        downloadMediaInfo.setErrorMsg(msg);
    }

    public void removeDownloadInfoListener(ClassDownloadListener listener) {
        if (listener != null && outListenerList != null) {
            this.outListenerList.remove(listener);
        }
    }

    /**
     * 从数据库查询数据
     */
//    public void findDatasByDb(final VidSts vidSts, final LoadDbDatasListener listener) {
//        ThreadUtils.runOnSubThread(new Runnable() {
//            @Override
//            public void run() {
//                //查询所有准备完成状态的数据,用于展示
//                List<ViodBean> selectPreparedList = mDatabaseManager.selectPreparedList();
//                //查询所有等待状态的数据,用于展示
//                final List<ViodBean> selectStopedList = mDatabaseManager.selectStopedList();
//                //查询所有完成状态的数据,用于展示
//                final List<ViodBean> selectCompletedList = mDatabaseManager.selectCompletedList();
//                //查询所有下载状态中的数据
//                final List<ViodBean> selectDownloadingList = mDatabaseManager.selectDownloadingList();
//                final List<ViodBean> dataList = new ArrayList<>();
//                dataList.addAll(selectCompletedList);
//                dataList.addAll(selectStopedList);
//                dataList.addAll(selectPreparedList);
//                for (ViodBean mediaInfo : selectPreparedList) {
//                    mediaInfo.setStatus(ViodBean.Status.Stop);
//                }
//                for (ViodBean mediaInfo : selectDownloadingList) {
//                    mediaInfo.setStatus(ViodBean.Status.Stop);
//                }
//                dataList.addAll(selectDownloadingList);
//                if (stopedList != null) {
//                    stopedList.addAll(selectDownloadingList);
//                    stopedList.addAll(selectStopedList);
//                    stopedList.addAll(selectPreparedList);
//                }
//                if (completedList != null) {
//                    completedList.addAll(selectCompletedList);
//                }
//                /*
//                 * 这里不需要将从数据库查询的下载中状态的数据进行内存缓存,在prepareDownload这些数据的时候,
//                 * 会全部置为等待状态,需要手动点击开始下载
//                 */
//                ThreadUtils.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        prepareDownload(vidSts, dataList);
//                        if (listener != null) {
//                            listener.onLoadSuccess(dataList);
//                        }
//                    }
//                });
//            }
//        });
//
//    }

    /**
     * 从数据库查询数据
     */
//    public void findDatasByDb(final LoadDbDatasListener listener) {
//        if (mDatabaseManager != null) {
//            ThreadUtils.runOnSubThread(new Runnable() {
//                @Override
//                public void run() {
//                    //查询所有准备完成状态的数据,用于展示
//                    List<ViodBean> selectPreparedList = mDatabaseManager.selectPreparedList();
//                    //查询所有等待状态的数据,用于展示
//                    final List<ViodBean> selectStopedList = mDatabaseManager.selectStopedList();
//                    //查询所有完成状态的数据,用于展示
//                    final List<ViodBean> selectCompletedList = mDatabaseManager.selectCompletedList();
//                    //查询所有下载状态中的数据
//                    final List<ViodBean> selectDownloadingList = mDatabaseManager.selectDownloadingList();
//                    final List<ViodBean> dataList = new ArrayList<>();
//                    if (selectPreparedList != null) {
//                        for (ViodBean mediaInfo : selectPreparedList) {
//                            mediaInfo.setStatus(ViodBean.Status.Stop);
//                        }
//                    }
//
//                    if (selectDownloadingList != null) {
//                        Iterator<ViodBean> iterator = selectDownloadingList.iterator();
//                        while (iterator.hasNext()) {
//                            ViodBean mediaInfo = iterator.next();
//                            if (mediaInfo.getProgress() == 100) {
//                                mediaInfo.setStatus(ViodBean.Status.Complete);
//                                iterator.remove();
//                                if (selectCompletedList != null) {
//                                    selectCompletedList.add(mediaInfo);
//                                }
//                            } else {
//                                mediaInfo.setStatus(ViodBean.Status.Stop);
//                            }
//                        }
//                        dataList.addAll(selectDownloadingList);
//                    }
//
//                    if (selectStopedList != null) {
//                        dataList.addAll(selectStopedList);
//                    }
//                    if (selectPreparedList != null) {
//                        dataList.addAll(selectPreparedList);
//                    }
//                    if (selectCompletedList != null) {
//                        dataList.addAll(selectCompletedList);
//                    }
//
//                    if (stopedList != null) {
//                        if (selectDownloadingList != null) {
//                            stopedList.addAll(selectDownloadingList);
//                        }
//                        if (selectStopedList != null) {
//                            stopedList.addAll(selectStopedList);
//                        }
//                        if (selectPreparedList != null) {
//                            stopedList.addAll(selectPreparedList);
//                        }
//                    }
//                    if (completedList != null) {
//                        if (selectCompletedList != null) {
//                            completedList.addAll(selectCompletedList);
//                        }
//                    }
//
//                    //增加本地文件判断,如果文件手动删除,则从数据库中删除
//                    Iterator<ViodBean> iterator = dataList.iterator();
//                    while (iterator.hasNext()) {
//                        ViodBean nextViodBean = iterator.next();
//                        String savePath = nextViodBean.getSavePath();
//                        if (!TextUtils.isEmpty(savePath)) {
//                            File file = new File(savePath);
//                            if (!file.exists() && nextViodBean.getStatus() == ViodBean.Status.Complete) {
//                                iterator.remove();
//                                mDatabaseManager.delete(nextViodBean);
//                            }
//                        }
//                    }
//                    /*
//                     * 这里不需要将从数据库查询的下载中状态的数据进行内存缓存,在prepareDownload这些数据的时候,
//                     * 会全部置为等待状态,需要手动点击开始下载
//                     */
//                    ThreadUtils.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (listener != null) {
//                                listener.onLoadSuccess(dataList);
//                            }
//                        }
//                    });
//                }
//            });
//        }
//    }

    /**
     * 获取准备完成的数据
     */
    public ConcurrentLinkedQueue<ViodBean> getPreparedList() {
        return preparedList;
    }

    /**
     * 获取下载完成的数据
     */
    public ConcurrentLinkedQueue<ViodBean> getCompletedList() {
        return completedList;
    }

    /**
     * 获取下载中的数据
     */
    public ConcurrentLinkedQueue<ViodBean> getDownloadingList() {
        return downloadingList;
    }

    /**
     * 获取等待中的数据
     */
    public ConcurrentLinkedQueue<ViodBean> getWaitedList() {
        return waitedList;
    }

    /**
     * 获取暂停中的数据
     */
    public ConcurrentLinkedQueue<ViodBean> getStopedList() {
        return stopedList;
    }

    public void release() {
//        if (mDatabaseManager != null) {
//            mDatabaseManager.close();
//        }
        if (preparedList != null) {
            preparedList.clear();
        }
        if (downloadingList != null) {
            downloadingList.clear();
        }
        if (completedList != null) {
            completedList.clear();
        }
        if (waitedList != null) {
            waitedList.clear();
        }
        if (outListenerList != null) {
            outListenerList.clear();
        }
    }

    /**
     * sts 刷新回调
     */
    public void setRefreshStsCallback(final RefreshStsCallback refreshStsCallback) {

    }




}
