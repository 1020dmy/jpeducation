package com.jianpei.jpeducation.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jianpei.jpeducation.utils.classdownload.DownloadMediaInfo;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/3
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
@Dao
public interface DownloadMediaInfoDao {

    /**
     * 根据ID查询视频
     *
     * @return
     */
    @Query("SELECT * FROM downloadmediainfo Where id = :value LIMIT 1")
    DownloadMediaInfo getViodBean(String value);


    /**
     * 根据章ID查询
     *
     * @param
     * @return
     */
    @Query("SELECT * FROM downloadmediainfo Where chapter_id= :value ")
    List<DownloadMediaInfo> getViodBeans(String value);


    /**
     * 根据下载状态查询
     *
     * @param
     * @return
     */
    @Query("SELECT * FROM downloadmediainfo Where sstatus= :value ")
    List<DownloadMediaInfo> getViodBeans(int value);


//    /**
//     * 查询当前章节下的视频数量
//     *
//     * @param
//     * @return
//     */
//    @Query("SELECT * FROM downloadmediainfo Where chapter_id= :value ")
//    List<DownloadMediaInfo> getViodBeans(String value);


    /**
     * 获取全部的视频
     *
     * @return
     */
    @Query("SELECT * FROM downloadmediainfo")
    List<DownloadMediaInfo> getAllViodBeans();

    /**
     * 插入数据视频
     *
     * @param downloadMediaInfo
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertViodBean(DownloadMediaInfo... downloadMediaInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertViodBean(List<DownloadMediaInfo> downloadMediaInfos);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void upDateViodBean(DownloadMediaInfo... downloadMediaInfo);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void upDateViodBean(List<DownloadMediaInfo> downloadMediaInfos);

    /**
     * 删除视频
     */
    @Delete
    void delete(DownloadMediaInfo downloadMediaInfo);


    /**
     * "
     * 删除视频表
     */
    @Query("DELETE FROM downloadmediainfo")
    void deleteViodbean();
}
