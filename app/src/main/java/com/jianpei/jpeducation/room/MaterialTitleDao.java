package com.jianpei.jpeducation.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jianpei.jpeducation.bean.material.MaterialTitle;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/31
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
@Dao
public interface MaterialTitleDao {


    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Query("SELECT * FROM materialtitle Where id = :value LIMIT 1")
    MaterialTitle getMaterialTitle(String value);

//
//    /**
//     * 根据cat_id查询
//     *
//     * @param
//     * @return
//     */
//    @Query("SELECT * FROM materialtitle Where class_id= :value ")
//    List<MaterialInfoBean> getMaterialInfoBeans(String value);


    /**
     * 获取全部下载的资料信息
     *
     * @return
     */
    @Query("SELECT * FROM materialtitle")
    List<MaterialTitle> getAllMaterialTitles();

    /**
     * 插入数据
     *
     * @param materialTitles
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMaterialTitle(MaterialTitle... materialTitles);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMaterialTitle(List<MaterialTitle> materialTitles);


    /**
     * 删除
     *
     * @param materialTitle
     */
    @Delete
    void delete(MaterialTitle materialTitle);

    /**
     * "
     * 删除表
     */
    @Query("DELETE FROM materialtitle")
    void deleteAllMaterialTitles();

}
