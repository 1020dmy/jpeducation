package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.classinfo.VideoUrlBean;
import com.jianpei.jpeducation.bean.mclass.DirectoryBean;
import com.jianpei.jpeducation.bean.mclass.MClassInfoBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.utils.classdownload.DownloadMediaInfo;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/3
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface OfflineClassRoomContract {

    interface Repository {
        //根据状态查询数据
        Observable<Integer> getRoomViodBean(int status);

//        //获取离线数据
//        Observable<List<DownloadMediaInfo>> getCompleteData(int status);

        //获取下载完成的章节
        Observable<List<DirectoryBean>> getOfflineCompleteData();

        //获取未下载完成的章节数据
        Observable<List<DirectoryBean>> getUndone();

        Observable<String> deleteViodBean(ViodBean viodBean);


//        //获取下载中的数据
//        Observable<List<DownloadMediaInfo>> downloadingClasss(int status);

        //获取正在下载的数量


    }


    interface Model {


        void getRoomViodBean(int status);

//        void getCompleteData(int status);


        void getOfflineCompleteData();


        void getUndone();

        void deleteViodBean(ViodBean viodBean);
    }

}
