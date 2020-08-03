package com.jianpei.jpeducation.utils.classdownload;

import com.aliyun.player.bean.ErrorCode;

import java.util.List;

public interface ClassDownloadListener {

    public void onPrepared(/*String vid, String quality*/List<DownloadMediaInfo> viodBeans);

    public void onAdd(DownloadMediaInfo info);

    public void onStart(/*String vid, String quality*/DownloadMediaInfo info);

    public void onProgress(/*String vid, String quality*/DownloadMediaInfo info, int percent);

    public void onStop(/*String vid, String quality*/DownloadMediaInfo info);

    public void onCompletion(/*String vid, String quality*/DownloadMediaInfo info);

    public void onError(/*String vid, String quality*/DownloadMediaInfo info, ErrorCode code, String msg, String requestId);

    public void onWait(DownloadMediaInfo outMediaInfo);

    public void onDelete(DownloadMediaInfo info);

    public void onDeleteAll();

    public void onFileProgress(DownloadMediaInfo info);

//    public void onM3u8IndexUpdate(AliyunDownloadMediaInfo outMediaInfo , int index);
}
