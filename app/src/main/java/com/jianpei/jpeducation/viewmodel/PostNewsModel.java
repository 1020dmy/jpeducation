package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.school.AttentionBean;
import com.jianpei.jpeducation.bean.school.TopicBean;
import com.jianpei.jpeducation.contract.PostNewsContract;
import com.jianpei.jpeducation.repository.PostNewsRepository;
import com.jianpei.jpeducation.utils.L;
import com.shuyu.textutillib.model.TopicModel;
import com.shuyu.textutillib.model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class PostNewsModel extends BaseViewModel implements PostNewsContract.Model {

    private PostNewsRepository postNewsRepository;

    public PostNewsModel() {
        postNewsRepository = new PostNewsRepository();
    }

    private MutableLiveData<String> successLiveData;

    public MutableLiveData<String> getSuccessLiveData() {
        if (successLiveData == null)
            successLiveData = new MutableLiveData<>();
        return successLiveData;
    }

    @Override
    public void insertGarden(String content, List<String> images, ArrayList<UserModel> selectAttentionBeans, ArrayList<TopicModel> selectTopicBean) {
        if (TextUtils.isEmpty(content)) {
            errData.setValue("内容不能为空");
            return;
        }
        String imagess = "";
        String remind_id="";
        String topic_id="";
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
        L.e("iamgess:" + imagess);
        if (selectAttentionBeans!=null && selectAttentionBeans.size()>0){
            StringBuilder stringBuilder = new StringBuilder();
            for (UserModel userModel : selectAttentionBeans) {
                stringBuilder.append(userModel.getUser_id());
                stringBuilder.append(",");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
            remind_id = stringBuilder.toString();
            stringBuilder.replace(0, stringBuilder.length(), "");
            stringBuilder.reverse();
            stringBuilder = null;
        }
        L.e("remind_id:" + remind_id);
        if (selectTopicBean!=null && selectTopicBean.size()>0){
            StringBuilder stringBuilder = new StringBuilder();
            for (TopicModel topicBean : selectTopicBean) {
                stringBuilder.append(topicBean.getTopicId());
                stringBuilder.append(",");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
            topic_id = stringBuilder.toString();
            stringBuilder.replace(0, stringBuilder.length(), "");
            stringBuilder.reverse();
            stringBuilder = null;
        }
        L.e("topic_id:" + topic_id);

        postNewsRepository.insertGarden(content, imagess, remind_id, topic_id).compose(setThread()).subscribe(new BaseObserver<String>() {
            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {
                if (t.isSuccess()) {
                    successLiveData.setValue(t.getMsg());
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

//    @Override
//    public void insertGarden(String content, List<String> images, ArrayList<AttentionBean> selectAttentionBeans, ArrayList<TopicBean> selectTopicBean) {
//        if (TextUtils.isEmpty(content)) {
//            errData.setValue("内容不能为空");
//            return;
//        }
//        String imagess = "";
//        String remind_id="";
//        String topic_id="";
//        if (images != null && images.size() > 0) {
//            StringBuilder stringBuilder = new StringBuilder();
//            for (String image : images) {
//                stringBuilder.append(image);
//                stringBuilder.append(";");
//            }
//            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
//            imagess = stringBuilder.toString();
//            stringBuilder.replace(0, stringBuilder.length(), "");
//            stringBuilder.reverse();
//            stringBuilder = null;
//        }
//        L.e("iamgess:" + imagess);
//        if (selectAttentionBeans!=null && selectAttentionBeans.size()>0){
//            StringBuilder stringBuilder = new StringBuilder();
//            for (AttentionBean attentionBean : selectAttentionBeans) {
//                stringBuilder.append(attentionBean.getId());
//                stringBuilder.append(",");
//            }
//            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
//            remind_id = stringBuilder.toString();
//            stringBuilder.replace(0, stringBuilder.length(), "");
//            stringBuilder.reverse();
//            stringBuilder = null;
//        }
//        L.e("remind_id:" + remind_id);
//        if (selectTopicBean!=null && selectTopicBean.size()>0){
//            StringBuilder stringBuilder = new StringBuilder();
//            for (TopicBean topicBean : selectTopicBean) {
//                stringBuilder.append(topicBean.getId());
//                stringBuilder.append(",");
//            }
//            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
//            topic_id = stringBuilder.toString();
//            stringBuilder.replace(0, stringBuilder.length(), "");
//            stringBuilder.reverse();
//            stringBuilder = null;
//        }
//        L.e("topic_id:" + topic_id);
//
//        postNewsRepository.insertGarden(content, imagess, remind_id, topic_id).compose(setThread()).subscribe(new BaseObserver<String>() {
//            @Override
//            protected void onSuccees(BaseEntity<String> t) throws Exception {
//                if (t.isSuccess()) {
//                    successLiveData.setValue(t.getMsg());
//                } else {
//                    errData.setValue(t.getMsg());
//                }
//            }
//
//            @Override
//            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                if (isNetWorkError) {
//                    errData.setValue("网络问题！");
//                } else {
//                    errData.setValue(e.getMessage());
//                }
//            }
//        });
//    }
}
