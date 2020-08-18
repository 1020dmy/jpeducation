package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.classinfo.DirectorySectionBean;
import com.jianpei.jpeducation.bean.classinfo.VideoUrlBean;
import com.jianpei.jpeducation.bean.json.ClassInfoJson;
import com.jianpei.jpeducation.bean.json.UpdateScheduleJson;
import com.jianpei.jpeducation.bean.json.VideoUrlJson;
import com.jianpei.jpeducation.bean.json.ViodListJson;
import com.jianpei.jpeducation.bean.mclass.DirectoryBean;
import com.jianpei.jpeducation.bean.mclass.MClassInfoBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.contract.ClassPlayerContract;
import com.jianpei.jpeducation.room.MyRoomDatabase;
import com.jianpei.jpeducation.utils.L;
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
                for (DirectoryBean directoryBean : directoryBeans) {
                    if (directoryBean.getViods() != null && directoryBean.getViods().size() != 0) {
                        directoryBean.setExpanded(true);
                    }
                }


                //插入数据库
                MyRoomDatabase.getInstance().directoryDao().insertDirectoryBean(directoryBeans);
                //
                List<ViodBean> viodBeans = MyRoomDatabase.getInstance().viodBeanDao().getAllViodBeans();
                if (viodBeans == null || viodBeans.size() == 0) {
                    return mClassInfoBeanBaseEntity;
                }
                for (ViodBean viodBean : viodBeans) {
                    for (DirectoryBean directoryBean : directoryBeans) {
                        for (ViodBean viodBean1 : directoryBean.getViods()) {
                            if (viodBean.getId().equals(viodBean1.getId())) {
                                viodBean1.setStatus(viodBean.getStatus());
                                viodBean1.setProgress(viodBean.getProgress());
                                viodBean1.setSavePath(viodBean.getSavePath());
                                viodBean1.setVid(viodBean.getVid());
                                viodBean1.setQuality(viodBean.getQuality());
                                viodBean1.setMtitle(viodBean.getMtitle());
                                viodBean1.setCoverUrl(viodBean.getCoverUrl());
                                viodBean1.setDuration(viodBean.getDuration());
                                viodBean1.setSize(viodBean.getSize());
                                viodBean1.setFormat(viodBean.getFormat());
                                viodBean1.setQualityIndex(viodBean.getQualityIndex());


                            }
                        }
                    }
                }
                return mClassInfoBeanBaseEntity;
            }
        });
    }

    @Override
    public Observable<BaseEntity<VideoUrlBean>> videoUrl(String type, String video_id, String buy_id, String group_id) {
        return RetrofitFactory.getInstance().API().videoUrl(new VideoUrlJson(type, video_id, buy_id, group_id));
    }

    /**
     * 更新视频观看记录
     *
     * @param total_second
     * @param current_second
     * @param class_id
     * @param chapter_id
     * @param viod_id
     * @param buy_id
     * @return
     */
    @Override
    public Observable<BaseEntity<String>> updateSchedule(String total_second, String current_second, String class_id, String chapter_id, String viod_id, String buy_id) {

        return RetrofitFactory.getInstance().API().updateSchedule(new UpdateScheduleJson(total_second, current_second, class_id, chapter_id, viod_id, buy_id));
    }

    /**
     * 章节列表
     *
     * @param class_id
     * @param chapter_id
     * @param type
     * @return
     */

    @Override
    public Observable<BaseEntity<List<ViodBean>>> viodList(String class_id, String chapter_id, String type) {
        return RetrofitFactory.getInstance().API().viodList(new ViodListJson(class_id, chapter_id, type)).map(new Function<BaseEntity<List<ViodBean>>, BaseEntity<List<ViodBean>>>() {
            @Override
            public BaseEntity<List<ViodBean>> apply(BaseEntity<List<ViodBean>> listBaseEntity) throws Exception {
                List<ViodBean> viodBeans = MyRoomDatabase.getInstance().viodBeanDao().getAllViodBeans();

                if (listBaseEntity.getData() == null)
                    return listBaseEntity;
                for (ViodBean viodBean : viodBeans) {
                    for (ViodBean viodBean1 : listBaseEntity.getData()) {
                        if (viodBean.getId().equals(viodBean1.getId())) {
                            viodBean1.setStatus(viodBean.getStatus());
                            viodBean1.setProgress(viodBean.getProgress());
                            viodBean1.setSavePath(viodBean.getSavePath());
                            viodBean1.setVid(viodBean.getVid());
                            viodBean1.setQuality(viodBean.getQuality());
                            viodBean1.setMtitle(viodBean.getMtitle());
                            viodBean1.setCoverUrl(viodBean.getCoverUrl());
                            viodBean1.setDuration(viodBean.getDuration());
                            viodBean1.setSize(viodBean.getSize());
                            viodBean1.setFormat(viodBean.getFormat());
                            viodBean1.setQualityIndex(viodBean.getQualityIndex());
                        }
                    }
                }

                return listBaseEntity;
            }
        });
    }
}
