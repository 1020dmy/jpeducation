package com.jianpei.jpeducation.bean.mclass;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.aliyun.player.bean.ErrorCode;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.player.source.VidAuth;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.jianpei.jpeducation.utils.classdownload.DownloadMediaInfo;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/13
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
@Entity(tableName = "ViodBean")
public class ViodBean extends BaseNode {


    /**
     * id : 286
     * title : 建筑构造01
     * isfree : 0
     * chapter_id : 146
     * dqtime :
     * totaltime :
     * is_last_read : 0
     * schedule : 0.00
     */
    @NonNull
    @PrimaryKey
    private String id;
    private String title;
    private String isfree;
    private String chapter_id;
    private String dqtime;
    private String totaltime;
    private String is_last_read;
    private String schedule;
    //
    private int status;   //1.准备，3.下载状态，4.停止，5.完成
    private int progress;
    private String savePath;
    //
    private String vid;
    private String quality;
    private String mtitle;
    private String coverUrl;
    private long duration;
    private long size;
    private String format;
    private int qualityIndex;
    //需要忽略的
    @Ignore
    private DownloadMediaInfo.Status mStatus;
    @Ignore
    private int mDownloadIndex = 0;
    @Ignore
    private int isEncripted = 0;
    @Ignore
    private TrackInfo mTrackInfo;
    @Ignore
    private VidAuth vidAuth;
    @Ignore
    private ErrorCode errorCode;
    @Ignore
    private String errorMsg;
    @Ignore
    private int mFileHandleProgress = 0;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsfree() {
        return isfree;
    }

    public void setIsfree(String isfree) {
        this.isfree = isfree;
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getDqtime() {
        return dqtime;
    }

    public void setDqtime(String dqtime) {
        this.dqtime = dqtime;
    }

    public String getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(String totaltime) {
        this.totaltime = totaltime;
    }

    public String getIs_last_read() {
        return is_last_read;
    }

    public void setIs_last_read(String is_last_read) {
        this.is_last_read = is_last_read;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getQualityIndex() {
        return qualityIndex;
    }

    public void setQualityIndex(int qualityIndex) {
        this.qualityIndex = qualityIndex;
    }

    public DownloadMediaInfo.Status getmStatus() {
        return mStatus;
    }

    public void setmStatus(DownloadMediaInfo.Status mStatus) {
        this.mStatus = mStatus;
    }

    public int getmDownloadIndex() {
        return mDownloadIndex;
    }

    public void setmDownloadIndex(int mDownloadIndex) {
        this.mDownloadIndex = mDownloadIndex;
    }

    public int getIsEncripted() {
        return isEncripted;
    }

    public void setIsEncripted(int isEncripted) {
        this.isEncripted = isEncripted;
    }

    public TrackInfo getmTrackInfo() {
        return mTrackInfo;
    }

    public void setmTrackInfo(TrackInfo mTrackInfo) {
        this.mTrackInfo = mTrackInfo;
    }

    public VidAuth getVidAuth() {
        return vidAuth;
    }

    public void setVidAuth(VidAuth vidAuth) {
        this.vidAuth = vidAuth;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getmFileHandleProgress() {
        return mFileHandleProgress;
    }

    public void setmFileHandleProgress(int mFileHandleProgress) {
        this.mFileHandleProgress = mFileHandleProgress;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ViodBean that = (ViodBean) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {

        return Arrays.hashCode(new String[]{id});
    }

    @Override
    public String toString() {
        return "ViodBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", isfree='" + isfree + '\'' +
                ", chapter_id='" + chapter_id + '\'' +
                ", dqtime='" + dqtime + '\'' +
                ", totaltime='" + totaltime + '\'' +
                ", is_last_read='" + is_last_read + '\'' +
                ", schedule='" + schedule + '\'' +
                ", status=" + status +
                '}';
    }
}
