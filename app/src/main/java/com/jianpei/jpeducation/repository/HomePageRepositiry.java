package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.NoticeDataBean;
import com.jianpei.jpeducation.bean.homedata.HomeDataBean;
import com.jianpei.jpeducation.bean.HomeInfoJson;
import com.jianpei.jpeducation.bean.homedata.MaterialInfoBean;
import com.jianpei.jpeducation.contract.HomePageContract;
import com.jianpei.jpeducation.room.MyRoomDatabase;
import com.jianpei.jpeducation.utils.L;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

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
        return RetrofitFactory.getInstance().API().getHomeInfo(new HomeInfoJson(catId)).map(new Function<BaseEntity<HomeDataBean>, BaseEntity<HomeDataBean>>() {

            @Override
            public BaseEntity<HomeDataBean> apply(BaseEntity<HomeDataBean> homeDataBeanBaseEntity) throws Exception {
                ///查询资料数据库，更改数据状态
                for (MaterialInfoBean materialInfoBean : homeDataBeanBaseEntity.getData().getMaterialData().getData()) {
                    //根据资料ID判断当前资料是和否已经下载完成

                    L.e("====materialInfoBeanId==:" + materialInfoBean.getId());
                    MaterialInfoBean materialInfoBean1 = MyRoomDatabase.getInstance().materialInfoDao().getMaterialInfoBean(materialInfoBean.getId());
                    if (materialInfoBean1 != null) {
                        L.e("======materialInfoBean1====" + materialInfoBean1.getId());
                        materialInfoBean.setStatus(materialInfoBean1.getStatus());
                        materialInfoBean.setProgress(materialInfoBean1.getProgress());
                        materialInfoBean.setPath(materialInfoBean1.getPath());
                    }

                }

                return homeDataBeanBaseEntity;
            }
        });

//        return RetrofitFactory.getInstance().API().getHomeInfo(new HomeInfoJson(catId));

    }

    @Override
    public Observable<BaseEntity<ArrayList<NoticeDataBean>>> noticeData(String catId) {
        return RetrofitFactory.getInstance().API().noticeData(new HomeInfoJson(catId));
    }

//    @Override
//    public Observable<BaseEntity<DownloadBean>> getDownloadUrl(String fileId) {
//        return RetrofitFactory.getInstance().API().getDownloadUrl(new DownloadJson(fileId));
//    }
}
