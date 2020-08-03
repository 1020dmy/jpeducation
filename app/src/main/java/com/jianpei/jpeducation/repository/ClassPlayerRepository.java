package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.classinfo.VideoUrlBean;
import com.jianpei.jpeducation.bean.json.ClassInfoJson;
import com.jianpei.jpeducation.bean.json.VideoUrlJson;
import com.jianpei.jpeducation.bean.mclass.DirectoryBean;
import com.jianpei.jpeducation.bean.mclass.MClassInfoBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.contract.ClassPlayerContract;
import com.jianpei.jpeducation.room.MyRoomDatabase;
import com.jianpei.jpeducation.utils.classdownload.DownloadMediaInfo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ClassPlayerRepository extends BaseRepository implements ClassPlayerContract.Repository {


    @Override
    public Observable<BaseEntity<MClassInfoBean>> classInfo(String class_id) {
        return RetrofitFactory.getInstance().API().classInfo(new ClassInfoJson(class_id)).map(new Function<BaseEntity<MClassInfoBean>, BaseEntity<MClassInfoBean>>() {
            @Override
            public BaseEntity<MClassInfoBean> apply(BaseEntity<MClassInfoBean> mClassInfoBeanBaseEntity) throws Exception {


                List<DirectoryBean> directoryBeans = mClassInfoBeanBaseEntity.getData().getDirectorys();
                //插入数据库
                MyRoomDatabase.getInstance().directoryDao().insertDirectoryBean(directoryBeans);
                //

                List<DownloadMediaInfo> downloadMediaInfos = MyRoomDatabase.getInstance().downloadMediaInfoDao().getAllViodBeans();

                for (DownloadMediaInfo downloadMediaInfo : downloadMediaInfos) {
                    for (DirectoryBean directoryBean : directoryBeans) {

                        for (ViodBean viodBean : directoryBean.getViods()) {

                            if (viodBean.getId().equals(downloadMediaInfo.getId())) {
                                viodBean.setStatus(downloadMediaInfo.getSstatus());
                                viodBean.setProgress(downloadMediaInfo.getProgress());
                                viodBean.setmSavePath(downloadMediaInfo.getSavePath());
                            }
                        }
                    }
                }


                return mClassInfoBeanBaseEntity;
            }
        });
    }

    @Override
    public Observable<BaseEntity<VideoUrlBean>> videoUrl(String type, String video_id, String buy_id) {
        return RetrofitFactory.getInstance().API().videoUrl(new VideoUrlJson(type, video_id, buy_id));
    }
}
