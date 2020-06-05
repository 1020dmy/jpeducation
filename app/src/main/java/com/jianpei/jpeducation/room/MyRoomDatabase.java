package com.jianpei.jpeducation.room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jianpei.jpeducation.base.MyApplication;
import com.jianpei.jpeducation.bean.homedata.MaterialInfoBean;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
@Database(entities = {MaterialInfoBean.class}, version = 1)
public abstract class MyRoomDatabase extends RoomDatabase {

    private static volatile MyRoomDatabase INSTANCE;

    public abstract MaterialInfoDao materialInfoDao();

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
