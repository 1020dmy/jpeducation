package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.DownloadBean;
import com.jianpei.jpeducation.bean.DownloadJson;
import com.jianpei.jpeducation.bean.NoticeDataBean;
import com.jianpei.jpeducation.bean.homedata.HomeDataBean;
import com.jianpei.jpeducation.bean.HomeInfoJson;
import com.jianpei.jpeducation.contract.HomePageContract;

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
public class HomePageRepositiry implements HomePageContract.Repository {

    @Override
    public Observable<BaseEntity<HomeDataBean>> getHomeData(String catId) {
        return RetrofitFactory.getInstance().API().getHomeInfo(new HomeInfoJson(catId));
    }

    @Override
    public Observable<BaseEntity<ArrayList<NoticeDataBean>>> noticeData(String catId) {
        return RetrofitFactory.getInstance().API().noticeData(new HomeInfoJson(catId));
    }

    @Override
    public Observable<BaseEntity<DownloadBean>> getDownloadUrl(String fileId) {
        return RetrofitFactory.getInstance().API().getDownloadUrl(new DownloadJson(fileId));
    }
}
