package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.classinfo.ClassInfoBean;
import com.jianpei.jpeducation.bean.classinfo.GroupCouponBean;
import com.jianpei.jpeducation.bean.classinfo.RegimentBean;
import com.jianpei.jpeducation.bean.classinfo.RegimentDataBean;
import com.jianpei.jpeducation.bean.classinfo.VideoUrlBean;
import com.jianpei.jpeducation.contract.ClassInfoFContract;
import com.jianpei.jpeducation.repository.ClassInfoFRepository;
import com.jianpei.jpeducation.utils.L;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ClassInfoFModel extends BaseViewModel implements ClassInfoFContract.Model {

    ClassInfoFRepository classInfoRepository;

    private MutableLiveData<ClassInfoBean> classInfoBean;

    private MutableLiveData<List<GroupCouponBean>> groupCouponBeansLiveData;

    private MutableLiveData<VideoUrlBean> videoUrlBeansLiveData;

    private MutableLiveData<String> couponReceiveLiveData;

    private MutableLiveData<RegimentBean> regimentBeanLiveData;


    private MutableLiveData<RegimentDataBean> regimentDataBeanLiveData;


    public MutableLiveData<RegimentDataBean> getRegimentDataBeanLiveData() {

        if (regimentDataBeanLiveData == null)
            regimentDataBeanLiveData = new MutableLiveData<>();
        return regimentDataBeanLiveData;
    }

    public MutableLiveData<RegimentBean> getRegimentBeanLiveData() {
        if (regimentBeanLiveData == null)
            regimentBeanLiveData = new MutableLiveData<>();
        return regimentBeanLiveData;
    }

    public MutableLiveData<VideoUrlBean> getVideoUrlBeansLiveData() {
        if (videoUrlBeansLiveData == null) {
            videoUrlBeansLiveData = new MutableLiveData<>();
        }
        return videoUrlBeansLiveData;
    }

    public MutableLiveData<List<GroupCouponBean>> getGroupCouponBeansLiveData() {
        if (groupCouponBeansLiveData == null)
            groupCouponBeansLiveData = new MutableLiveData<>();
        return groupCouponBeansLiveData;
    }

    public MutableLiveData<ClassInfoBean> getClassInfoBean() {
        if (classInfoBean == null) {
            classInfoBean = new MutableLiveData<>();
        }
        return classInfoBean;
    }

    public MutableLiveData<String> getCouponReceiveLiveData() {
        if (couponReceiveLiveData == null) {
            couponReceiveLiveData = new MutableLiveData<>();
        }
        return couponReceiveLiveData;
    }

    public ClassInfoFModel() {

        classInfoRepository = new ClassInfoFRepository();
    }

    @Override
    public void groupInfo(String groupId, String regimentId) {

        if (TextUtils.isEmpty(groupId)) {
            return;
        }

        classInfoRepository.groupInfo(groupId, regimentId).compose(setThread()).subscribe(new BaseObserver<ClassInfoBean>() {

            @Override
            protected void onSuccees(BaseEntity<ClassInfoBean> t) throws Exception {
                if (t.isSuccess()) {
                    classInfoBean.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }


            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络异常！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });

    }

    @Override
    public void groupCoupon(String catId, String groupCoupon) {
        if (TextUtils.isEmpty(catId)) {
            return;
        }
        classInfoRepository.groupCoupon(catId, groupCoupon).compose(setThread()).subscribe(new BaseObserver<List<GroupCouponBean>>() {

            @Override
            protected void onSuccees(BaseEntity<List<GroupCouponBean>> t) throws Exception {
                if (t.isSuccess()) {
                    groupCouponBeansLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getData());
                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络异常！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });

    }

    @Override
    public void videoUrl(String video_id, String buy_id, String group_id) {
        if (TextUtils.isEmpty(video_id)) {
            return;
        }
        classInfoRepository.videoUrl("try", video_id, buy_id, group_id).compose(setThread()).subscribe(new BaseObserver<VideoUrlBean>() {
            @Override
            protected void onSuccees(BaseEntity<VideoUrlBean> t) throws Exception {
                if (t.isSuccess()) {
                    videoUrlBeansLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getData());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络异常！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });

    }

    /**
     * 领取优惠券
     *
     * @param couponId
     * @param shareUserId
     */
    @Override
    public void couponReceive(String couponId, String shareUserId) {
        classInfoRepository.couponReceive(couponId, shareUserId).compose(setThread()).subscribe(new BaseObserver<String>() {
            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {
                if (t.isSuccess()) {
                    couponReceiveLiveData.setValue(t.getMsg());
                } else {
                    errData.setValue(t.getMsg());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络异常！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });

    }

    /**
     * 拼团详情
     *
     * @param regiment_id
     */

    @Override
    public void regimentInfo(String regiment_id) {
        if (TextUtils.isEmpty(regiment_id)) {
            return;
        }

        classInfoRepository.regimentInfo(regiment_id).compose(setThread()).subscribe(new BaseObserver<RegimentBean>() {

            @Override
            protected void onSuccees(BaseEntity<RegimentBean> t) throws Exception {
                if (t.isSuccess()) {
                    regimentBeanLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络异常！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });
    }

    /**
     * 拼团列表
     *
     * @param huod_id
     */

    @Override
    public void regimentData(String huod_id) {

        if (TextUtils.isEmpty(huod_id)) {
            return;
        }
        classInfoRepository.regimentData(huod_id).compose(setThread()).subscribe(new BaseObserver<RegimentDataBean>() {

            @Override
            protected void onSuccees(BaseEntity<RegimentDataBean> t) throws Exception {

                if (t.isSuccess()) {
                    regimentDataBeanLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());

                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络异常！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });

    }
}
