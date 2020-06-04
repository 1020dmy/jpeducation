package com.jianpei.jpeducation.utils.down;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/1
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface AndroidDownloadManagerListener {

    void onPrepare();//准备

    void onDownLoading(int progress);

    void onSuccess(String path); //下载成功

    void onFailed(Throwable throwable);//下载失败
}
