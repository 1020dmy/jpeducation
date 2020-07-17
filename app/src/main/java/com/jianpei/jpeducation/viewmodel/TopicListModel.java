package com.jianpei.jpeducation.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.school.TopicDataBean;
import com.jianpei.jpeducation.contract.TopicListContract;
import com.jianpei.jpeducation.repository.TopicListRepository;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class TopicListModel extends BaseViewModel implements TopicListContract.Model {


    private TopicListRepository topicListRepository;

    public TopicListModel() {
        topicListRepository = new TopicListRepository();
    }

    private MutableLiveData<TopicDataBean> topicDataBeanLiveData;


    public MutableLiveData<TopicDataBean> getTopicDataBeanLiveData() {
        if (topicDataBeanLiveData == null)
            topicDataBeanLiveData = new MutableLiveData<>();
        return topicDataBeanLiveData;
    }

    @Override
    public void topicData(int pageIndex, int pageSize) {


        topicListRepository.topicData(pageIndex, pageSize).compose(setThread()).subscribe(new BaseObserver<TopicDataBean>() {

            @Override
            protected void onSuccees(BaseEntity<TopicDataBean> t) throws Exception {
                if (t.isSuccess()) {
                    topicDataBeanLiveData.setValue(t.getData());
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
