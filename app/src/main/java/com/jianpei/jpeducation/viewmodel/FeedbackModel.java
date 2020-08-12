package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.contract.FeedbackContract;
import com.jianpei.jpeducation.repository.FeedbackRepository;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class FeedbackModel extends BaseViewModel implements FeedbackContract.Model {

    private FeedbackRepository repository;

    public FeedbackModel() {
        repository = new FeedbackRepository();
    }

    /**
     * 意见反馈
     *
     * @param images
     * @param content
     * @param phone
     */
    private MutableLiveData<String> feedbackLiveData;

    public MutableLiveData<String> getFeedbackLiveData() {
        if (feedbackLiveData == null) {
            feedbackLiveData = new MutableLiveData<>();
        }
        return feedbackLiveData;
    }

    @Override
    public void feedback(List<String> images, String content, String phone, String type) {
        String imagess = "";
        if (images != null && images.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String image : images) {
                stringBuilder.append(image);
                stringBuilder.append(";");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
            imagess = stringBuilder.toString();
            stringBuilder.replace(0, stringBuilder.length(), "");
            stringBuilder.reverse();
            stringBuilder = null;
        }
        if (TextUtils.isEmpty(imagess) && TextUtils.isEmpty(content)) {
            errData.setValue("请填写所反馈的内容");
            return;
        }
        repository.feedback(imagess, content, phone, type).compose(setThread()).subscribe(new BaseObserver<String>() {
            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {
                if (t.isSuccess()) {
                    feedbackLiveData.setValue(t.getMsg());
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
