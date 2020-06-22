package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.UserInfoBean;
import com.jianpei.jpeducation.bean.classinfo.ClassInfoBean;
import com.jianpei.jpeducation.bean.classinfo.GroupCouponBean;
import com.jianpei.jpeducation.bean.classinfo.VideoUrlBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface ClassInfoFContract {

    interface Repository {

        Observable<BaseEntity<ClassInfoBean>> groupInfo(String groupId, String regimentId);


        Observable<BaseEntity<List<GroupCouponBean>>> groupCoupon(String catId);


        Observable<BaseEntity<VideoUrlBean>> videoUrl(String type, String video_id, String buy_id);

        Observable<BaseEntity<String>> couponReceive(String couponId,String shareUserId);

    }


    interface Model {
        void groupInfo(String groupId, String regimentId);

        void groupCoupon(String catId);

        void videoUrl(String video_id, String buy_id);

        void couponReceive(String couponId,String shareUserId);
    }
}
