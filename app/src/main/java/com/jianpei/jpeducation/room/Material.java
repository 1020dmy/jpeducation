package com.jianpei.jpeducation.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
@Entity(tableName = "material")
public class Material {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name ="materialid")
    private String id;

}
