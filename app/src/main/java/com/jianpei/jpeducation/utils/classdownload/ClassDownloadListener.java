package com.jianpei.jpeducation.utils.classdownload;

import com.aliyun.player.bean.ErrorCode;
import com.jianpei.jpeducation.bean.mclass.ViodBean;

import java.util.List;

public interface ClassDownloadListener {

    public void onPrepared(/*String vid, String quality*/List<ViodBean> viodBeans);

    public void onAdd(ViodBean info);

    public void onStart(/*String vid, String quality*/ViodBean info);

    public void onProgress(/*String vid, String quality*/ViodBean info, int percent);

    public void onStop(/*String vid, String quality*/ViodBean info);

    public void onCompletion(/*String vid, String quality*/ViodBean info);

    public void onError(/*String vid, String quality*/ViodBean info, ErrorCode code, String msg, String requestId);

    public void onWait(ViodBean outMediaInfo);

    public void onDelete(ViodBean info);

    public void onDeleteAll();

    public void onFileProgress(ViodBean info);

//    public void onM3u8IndexUpdate(AliyunDownloadMediaInfo outMediaInfo , int index);
}
