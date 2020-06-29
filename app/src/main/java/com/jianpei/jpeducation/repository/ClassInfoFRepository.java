package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.CouponReceiveJson;
import com.jianpei.jpeducation.bean.HomeInfoJson;
import com.jianpei.jpeducation.bean.classinfo.ClassInfoBean;
import com.jianpei.jpeducation.bean.classinfo.GroupCouponBean;
import com.jianpei.jpeducation.bean.classinfo.RegimentBean;
import com.jianpei.jpeducation.bean.classinfo.RegimentDataBean;
import com.jianpei.jpeducation.bean.classinfo.VideoUrlBean;
import com.jianpei.jpeducation.bean.json.GroupInfoJson;
import com.jianpei.jpeducation.bean.json.RegimentDataJson;
import com.jianpei.jpeducation.bean.json.RegimentInfoJson;
import com.jianpei.jpeducation.bean.json.VideoUrlJson;
import com.jianpei.jpeducation.contract.ClassInfoFContract;

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
public class ClassInfoFRepository extends BaseRepository implements ClassInfoFContract.Repository {

    @Override
    public Observable<BaseEntity<ClassInfoBean>> groupInfo(String groupId, String regimentId) {
        return RetrofitFactory.getInstance().API().groupInfo(new GroupInfoJson(groupId, regimentId));
    }

    @Override
    public Observable<BaseEntity<List<GroupCouponBean>>> groupCoupon(String catId) {
        return RetrofitFactory.getInstance().API().groupCoupon(new HomeInfoJson(catId));
    }

    @Override
    public Observable<BaseEntity<VideoUrlBean>> videoUrl(String type, String video_id, String buy_id) {
        return RetrofitFactory.getInstance().API().videoUrl(new VideoUrlJson(type, video_id, buy_id));
    }

    @Override
    public Observable<BaseEntity<String>> couponReceive(String couponId, String shareUserId) {
        return RetrofitFactory.getInstance().API().couponReceive(new CouponReceiveJson(couponId, shareUserId));
    }

    @Override
    public Observable<BaseEntity<RegimentBean>> regimentInfo(String regiment_id) {
        return RetrofitFactory.getInstance().API().regimentInfo(new RegimentInfoJson(regiment_id));
    }

    @Override
    public Observable<BaseEntity<RegimentDataBean>> regimentData(String huod_id) {
        return RetrofitFactory.getInstance().API().regimentData(new RegimentDataJson(huod_id, 1, 6));
    }
}
