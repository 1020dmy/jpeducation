package com.jianpei.jpeducation.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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
     * 根据ID查询视频
     *
     * @param id
     * @return
     */
    @Query("SELECT * FROM viod Where id = :value LIMIT 1")
    ViodBean getViodBean(String value);


    /**
     * 查询当前章节下的视频数量
     *
     * @param
     * @return
     */
    @Query("SELECT * FROM viod Where chapter_id= :value ")
    List<ViodBean> getViodBeans(String value);


    /**
     * 获取全部的视频
     *
     * @return
     */
    @Query("SELECT * FROM viod")
    List<ViodBean> getAllViodBeans();

    /**
     * 插入数据视频
     *
     * @param viodBeans
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertViodBean(ViodBean... viodBeans);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertViodBean(List<ViodBean> viodBeans);

    /**
     * 删除视频
     *
     * @param viodBean
     */
    @Delete
    void delete(ViodBean viodBean);

    /**
     * "
     * 删除视频表
     */
    @Query("DELETE FROM viod")
    void deleteViodbean();


}
