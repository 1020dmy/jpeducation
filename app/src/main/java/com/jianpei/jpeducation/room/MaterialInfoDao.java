package com.jianpei.jpeducation.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.jianpei.jpeducation.bean.homedata.MaterialInfoBean;

import java.util.ArrayList;

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
    @Query("SELECT * FROM material Where materialid= :id LIMIT 1")
    MaterialInfoBean getMaterialInfoBean(String id);

    /**
     * 获取全部下载的资料信息
     *
     * @return
     */
    @Query("SELECT * FROM material")
    ArrayList<MaterialInfoBean> getAllMaterialInfoBeans();

    @Insert
    void insertMaterialInfo(MaterialInfoBean materialInfoBean);


    @Query("DELETE FROM material")
    void deleteAllMaterialInfos();

}
