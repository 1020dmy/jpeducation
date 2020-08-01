package com.jianpei.jpeducation.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jianpei.jpeducation.bean.mclass.DirectoryBean;

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
public interface DirectoryDao {


    /**
     * 根据ID查询章节
     * * @return
     */
    @Query("SELECT * FROM directory Where id = :value LIMIT 1")
    DirectoryBean getDirectoryBean(String value);

//
//    /**
//     * 查询当前章节下的视频数量
//     *
//     * @param
//     * @return
//     */
//    @Query("SELECT * FROM viod Where chapter_id= :value ")
//    List<ViodBean> getViodBeans(String value);
//

    /**
     * 获取全部章节
     *
     * @return
     */
    @Query("SELECT * FROM directory")
    List<DirectoryBean> getAllDirectoryBeans();

    /**
     * 插入章节
     *
     * @param directoryBeans
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDirectoryBeans(DirectoryBean... directoryBeans);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDirectoryBean(List<DirectoryBean> directoryBeans);

    /**
     * 删除章节
     *
     * @param directoryBean
     */
    @Delete
    void delete(DirectoryBean directoryBean);

    /**
     * "
     * 删除视频表
     */
    @Query("DELETE FROM directory")
    void deleteDirectory();


}
