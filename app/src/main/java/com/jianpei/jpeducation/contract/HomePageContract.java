package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.DownloadBean;
import com.jianpei.jpeducation.bean.NoticeDataBean;
import com.jianpei.jpeducation.bean.homedata.HomeDataBean;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/1
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface HomePageContract {
    interface Repository {
        Observable<BaseEntity<HomeDataBean>> getHomeData(String catId);

        Observable<BaseEntity<ArrayList<NoticeDataBean>>> noticeData(String catId);

//        Observable<BaseEntity<DownloadBean>> getDownloadUrl(String fileId);

    }


    interface Model {
        void getHomeData(String catId);

        void noticeData(String catId);
//        void  getDownloadUrl(String fileId);

    }
}
