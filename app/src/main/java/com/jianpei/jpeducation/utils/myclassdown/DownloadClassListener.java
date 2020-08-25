package com.jianpei.jpeducation.utils.myclassdown;

import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.nativeclass.TrackInfo;
import com.jianpei.jpeducation.bean.mclass.ViodBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/18
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface DownloadClassListener {
    //准备监听
    void onPrepared(List<TrackInfo> trackInfos, ViodBean viodBean);

    //开始
    void onStart(ViodBean viodBean);

    //停止
    void onStop(ViodBean viodBean);

    //进度
    void onProgress(ViodBean viodBean, int progress);

    //处理进度
    void onFileProgress(ViodBean viodBean, int progress);

    //错误
    void onError(ViodBean viodBean, ErrorInfo errorInfo);

    //完成
    void onCompletion(ViodBean viodBean);

    void deleteFile(ViodBean viodBean);

////    //重新获取vid
//    void againStart(ViodBean viodBean);

}
