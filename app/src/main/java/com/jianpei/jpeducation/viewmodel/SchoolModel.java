package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.school.ThreadDataBean;
import com.jianpei.jpeducation.contract.SchoolContract;
import com.jianpei.jpeducation.repository.SchoolRepository;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SchoolModel extends BaseViewModel implements SchoolContract.Model {


    private SchoolRepository schoolRepository;


    public SchoolModel() {
        schoolRepository = new SchoolRepository();
    }

    /**
     * 帖子列表
     *
     * @param start_id
     * @param end_id
     * @param follow
     */
    private MutableLiveData<List<ThreadDataBean>> threadDataBeansLiveData;

    public MutableLiveData<List<ThreadDataBean>> getThreadDataBeansLiveData() {
        if (threadDataBeansLiveData == null)
            threadDataBeansLiveData = new MutableLiveData<>();
        return threadDataBeansLiveData;
    }

    @Override
    public void threadData(String start_id, String end_id, String follow) {

        schoolRepository.threadData(start_id, end_id, follow).compose(setThread()).subscribe(new BaseObserver<List<ThreadDataBean>>() {
            @Override
            protected void onSuccees(BaseEntity<List<ThreadDataBean>> t) throws Exception {
                if (t.isSuccess()) {
                    threadDataBeansLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络问题！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });
    }

    /**
     * 关注/取消关注
     *
     * @param attention_id
     * @param topic_id
     */
    private MutableLiveData<ThreadDataBean> threadDataBeanLiveData;

    public MutableLiveData<ThreadDataBean> getThreadDataBeanLiveData() {
        if (threadDataBeanLiveData == null)
            threadDataBeanLiveData = new MutableLiveData<>();
        return threadDataBeanLiveData;
    }

    @Override
    public void attention(String attention_id, String topic_id, String thread_id, List<ThreadDataBean> mThreadDataBeans) {
        if (TextUtils.isEmpty(attention_id)) {
            return;
        }
        schoolRepository.attention(attention_id, topic_id, thread_id, mThreadDataBeans).compose(setThread()).subscribe(new BaseObserver<ThreadDataBean>() {
            @Override
            protected void onSuccees(BaseEntity<ThreadDataBean> t) throws Exception {
                if (t.isSuccess()) {
                    threadDataBeanLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络问题！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });
    }

    /**
     * 1-点赞/取消点赞
     *
     * @param type
     * @param thread_id
     * @param topic_id
     * @param post_id
     */

    @Override
    public void gardenPraise(String type, String thread_id, String topic_id, String post_id) {
        schoolRepository.gardenPraise(type, thread_id, topic_id, post_id).compose(setThread()).subscribe(new BaseObserver<ThreadDataBean>() {

            @Override
            protected void onSuccees(BaseEntity<ThreadDataBean> t) throws Exception {
                if (t.isSuccess()) {
                    threadDataBeanLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络问题！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });
    }
}
