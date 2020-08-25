package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.classinfo.DirectorySectionBean;
import com.jianpei.jpeducation.bean.classinfo.VideoUrlBean;
import com.jianpei.jpeducation.bean.mclass.MClassInfoBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface ClassPlayerContract {
    interface Repository {
        //章节列表
        Observable<BaseEntity<List<ViodBean>>> viodList(String class_id, String chapter_id, String type);

        //课程详情
        Observable<BaseEntity<MClassInfoBean>> classInfo(String class_id);

        //获取视频播放地址
        Observable<BaseEntity<VideoUrlBean>> videoUrl(String type, String video_id, String buy_id, String group_id);

        //更新视频观看记录
        Observable<BaseEntity<String>> updateSchedule(String total_second, String current_second, String class_id, String chapter_id, String viod_id, String buy_id);


    }


    interface Model {
        void classInfo(String class_id);

        void videoUrl(String video_id, String buy_id, String group_id);


        void updateSchedule(String total_second, String current_second, String class_id, String chapter_id, String viod_id, String buy_id);


        void viodList(String class_id, String chapter_id,String type);

    }
}
