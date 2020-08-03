package com.jianpei.jpeducation.room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jianpei.jpeducation.base.MyApplication;
import com.jianpei.jpeducation.bean.material.MaterialInfoBean;
import com.jianpei.jpeducation.bean.material.MaterialTitle;
import com.jianpei.jpeducation.bean.mclass.DirectoryBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.utils.classdownload.DownloadMediaInfo;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */

@Database(entities = {MaterialInfoBean.class, MaterialTitle.class, DirectoryBean.class, ViodBean.class, DownloadMediaInfo.class}, version = 1)
public abstract class MyRoomDatabase extends RoomDatabase {

    private static volatile MyRoomDatabase INSTANCE;

    //资料详情
    public abstract MaterialInfoDao materialInfoDao();

    //资料标题
    public abstract MaterialTitleDao materialTitleDao();

    //视频章节
    public abstract DirectoryDao directoryDao();

    //视频
    public abstract ViodBeanDao viodBeanDao();

    //下载
    public abstract DownloadMediaInfoDao downloadMediaInfoDao();



    public static MyRoomDatabase getInstance() {
        if (INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(MyApplication.getInstance(),
                            MyRoomDatabase.class, "JianPei.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
