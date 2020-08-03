package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.classinfo.VideoUrlBean;
import com.jianpei.jpeducation.bean.mclass.MClassInfoBean;
import com.jianpei.jpeducation.contract.ClassPlayerContract;
import com.jianpei.jpeducation.repository.ClassPlayerRepository;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ClassPlayerModel extends BaseViewModel implements ClassPlayerContract.Model {

    private ClassPlayerRepository classPlayerRepository;

    public ClassPlayerModel() {
        classPlayerRepository = new ClassPlayerRepository();
    }


    ///
    private MutableLiveData<VideoUrlBean> playUrlBean;

    public MutableLiveData<VideoUrlBean> getPlayUrlBean() {
        if (playUrlBean == null) {
            playUrlBean = new MutableLiveData<>();
        }
        return playUrlBean;
    }

    //本地播放地址
    private MutableLiveData<String> playLocationUrl;

    public MutableLiveData<String> getPlayLocationUrl() {
        if (playLocationUrl == null)
            playLocationUrl = new MutableLiveData<>();
        return playLocationUrl;
    }

    /**
     * 课程详情
     *
     * @param class_id
     */
    private MutableLiveData<MClassInfoBean> mClassInfoBeanLiveData;

    public MutableLiveData<MClassInfoBean> getmClassInfoBeanLiveData() {
        if (mClassInfoBeanLiveData == null)
            mClassInfoBeanLiveData = new MutableLiveData<>();
        return mClassInfoBeanLiveData;
    }

    @Override
    public void classInfo(String class_id) {
        if (TextUtils.isEmpty(class_id))
            return;
        classPlayerRepository.classInfo(class_id).compose(setThread()).subscribe(new BaseObserver<MClassInfoBean>() {
            @Override
            protected void onSuccees(BaseEntity<MClassInfoBean> t) throws Exception {
                if (t.isSuccess()) {
                    mClassInfoBeanLiveData.setValue(t.getData());
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


    private MutableLiveData<VideoUrlBean> videoUrlBeansLiveData;

    public MutableLiveData<VideoUrlBean> getVideoUrlBeansLiveData() {
        if (videoUrlBeansLiveData == null) {
            videoUrlBeansLiveData = new MutableLiveData<>();
        }
        return videoUrlBeansLiveData;
    }

    /**
     * 1-获取视频播放url
     *
     * @param video_id
     * @param buy_id
     */

    @Override
    public void videoUrl(String video_id, String buy_id) {
        if (TextUtils.isEmpty(video_id)) {
            return;
        }
        classPlayerRepository.videoUrl("normal", video_id, buy_id).compose(setThread()).subscribe(new BaseObserver<VideoUrlBean>() {
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
     * 通知更新下载数量
     */
    private MutableLiveData<String> stringMutableLiveData;

    public MutableLiveData<String> getStringMutableLiveData() {
        if (stringMutableLiveData == null)
            stringMutableLiveData = new MutableLiveData<>();
        return stringMutableLiveData;
    }
}
