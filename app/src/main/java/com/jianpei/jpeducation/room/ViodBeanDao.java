package com.jianpei.jpeducation.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jianpei.jpeducation.bean.mclass.ViodBean;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/1
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */

@Dao
public interface ViodBeanDao {

    /**
     * 根据章节查询视频
     *
     * @return
     */
    @Query("SELECT * FROM viodbean Where chapter_id = :value LIMIT 1")
    List<ViodBean> getAllViodBean(String value);


    /**
     * 根据章ID查询和状态查询
     *
     * @param
     * @return
     */
    @Query("SELECT * FROM viodbean Where chapter_id= :value And status = :value2")
    List<ViodBean> getViodBeans(String value, int value2);


    /**
     * 查询未下载完成的数据
     *
     * @param
     * @return
     */
    @Query("SELECT * FROM viodbean Where chapter_id= :value And status != :value2")
    List<ViodBean> getUndone(String value, int value2);


    /**
     * 查询未下载完成的数据
     *
     * @param
     * @return
     */
    @Query("SELECT count(*) FROM viodbean Where  status != :value2")
    int getUndoneNums(int value2);

    /**
     * 根据下载状态查询
     *
     * @param
     * @return
     */
    @Query("SELECT * FROM viodbean Where status= :value ")
    List<ViodBean> getViodBeans(int value);


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
    @Query("SELECT * FROM viodbean")
    List<ViodBean> getAllViodBeans();

    /**
     * 插入数据视频
     *
     * @param downloadMediaInfo
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertViodBean(ViodBean... downloadMediaInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertViodBean(List<ViodBean> downloadMediaInfos);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void upDateViodBean(ViodBean... downloadMediaInfo);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void upDateViodBean(List<ViodBean> downloadMediaInfos);

    /**
     * 删除视频
     */
    @Delete
    void delete(ViodBean downloadMediaInfo);


    /**
     * "
     * 删除视频表
     */
    @Query("DELETE FROM viodbean")
    void deleteViodbean();


}
