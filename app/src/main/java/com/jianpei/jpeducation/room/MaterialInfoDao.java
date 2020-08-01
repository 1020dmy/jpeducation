package com.jianpei.jpeducation.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jianpei.jpeducation.bean.material.MaterialInfoBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
@Dao
public interface MaterialInfoDao {

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Query("SELECT * FROM material Where id = :value LIMIT 1")
    MaterialInfoBean getMaterialInfoBean(String value);


    /**
     * 根据cat_id查询
     *
     * @param
     * @return
     */
    @Query("SELECT * FROM material Where class_id= :value ")
    List<MaterialInfoBean> getMaterialInfoBeans(String value);


    /**
     * 获取全部下载的资料信息
     *
     * @return
     */
    @Query("SELECT * FROM material")
    LiveData<List<MaterialInfoBean>> getAllMaterialInfoBeans();

    /**
     * 插入数据
     *
     * @param materialInfoBean
     */
//    @Insert
//    void insertMaterialInfo(MaterialInfoBean materialInfoBean);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMaterialInfo(MaterialInfoBean... materialInfoBeans);

    /**
     * 删除
     *
     * @param materialInfoBean
     */
    @Delete
    void delete(MaterialInfoBean materialInfoBean);

    /**
     * "
     * 删除表
     */
    @Query("DELETE FROM material")
    void deleteAllMaterialInfos();

}
