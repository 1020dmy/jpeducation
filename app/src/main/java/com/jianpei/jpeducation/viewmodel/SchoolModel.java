package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.school.AttentionResultBean;
import com.jianpei.jpeducation.bean.school.EvaluationDataBean;
import com.jianpei.jpeducation.bean.school.GardenPraiseBean;
import com.jianpei.jpeducation.bean.school.MThreadDataBean;
import com.jianpei.jpeducation.bean.school.ReplyDataBean;
import com.jianpei.jpeducation.bean.school.ThreadDataBean;
import com.jianpei.jpeducation.bean.school.ThreadFromTopicDataBean;
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
     * 话题帖子列表
     *
     * @param start_id
     * @param end_id
     * @param follow
     * @param topic_id
     * @param is_hot
     */
    private MutableLiveData<ThreadFromTopicDataBean> threadFromTopicDataBeanLiveData;

    public MutableLiveData<ThreadFromTopicDataBean> getThreadFromTopicDataBeanLiveData() {
        if (threadFromTopicDataBeanLiveData == null)
            threadFromTopicDataBeanLiveData = new MutableLiveData<>();
        return threadFromTopicDataBeanLiveData;
    }

    @Override
    public void threadFromTopicData(String start_id, String end_id, String follow, String topic_id, String is_hot) {
        schoolRepository.threadFromTopicData(start_id, end_id, follow, topic_id, is_hot).compose(setThread()).subscribe(new BaseObserver<ThreadFromTopicDataBean>() {
            @Override
            protected void onSuccees(BaseEntity<ThreadFromTopicDataBean> t) throws Exception {
                if (t.isSuccess()) {
                    threadFromTopicDataBeanLiveData.setValue(t.getData());
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
    private MutableLiveData<AttentionResultBean> attentionLiveData;

    public MutableLiveData<AttentionResultBean> getAttentionLiveData() {
        if (attentionLiveData == null)
            attentionLiveData = new MutableLiveData<>();
        return attentionLiveData;
    }

    @Override
    public void attention(String attention_id, String topic_id, String thread_id, List<ThreadDataBean> mThreadDataBeans) {
        if (TextUtils.isEmpty(attention_id)) {
            return;
        }
        schoolRepository.attention(attention_id, topic_id, thread_id, mThreadDataBeans).compose(setThread()).subscribe(new BaseObserver<AttentionResultBean>() {
            @Override
            protected void onSuccees(BaseEntity<AttentionResultBean> t) throws Exception {
                if (t.isSuccess()) {
                    attentionLiveData.setValue(t.getData());
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
    //    private MutableLiveData<ThreadDataBean> threadDataBeanLiveData;
//
//    public MutableLiveData<ThreadDataBean> getThreadDataBeanLiveData() {
//        if (threadDataBeanLiveData == null)
//            threadDataBeanLiveData = new MutableLiveData<>();
//        return threadDataBeanLiveData;
//    }

//    @Override
//    public void attention(String attention_id, String topic_id, String thread_id, List<ThreadDataBean> mThreadDataBeans) {
//        if (TextUtils.isEmpty(attention_id)) {
//            return;
//        }
//        schoolRepository.attention(attention_id, topic_id, thread_id, mThreadDataBeans).compose(setThread()).subscribe(new BaseObserver<ThreadDataBean>() {
//            @Override
//            protected void onSuccees(BaseEntity<ThreadDataBean> t) throws Exception {
//                if (t.isSuccess()) {
//                    threadDataBeanLiveData.setValue(t.getData());
//                } else {
//                    errData.setValue(t.getMsg());
//                }
//
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

    /**
     * 1-点赞/取消点赞
     *
     * @param type
     * @param thread_id
     * @param topic_id
     * @param post_id
     */

    private MutableLiveData<GardenPraiseBean> gardenPraiseBeanLiveData;

    public MutableLiveData<GardenPraiseBean> getGardenPraiseBeanLiveData() {
        if (gardenPraiseBeanLiveData == null)
            gardenPraiseBeanLiveData = new MutableLiveData<>();
        return gardenPraiseBeanLiveData;
    }

    @Override
    public void gardenPraise(String type, String thread_id, String topic_id, String post_id) {
        schoolRepository.gardenPraise(type, thread_id, topic_id, post_id).compose(setThread()).subscribe(new BaseObserver<GardenPraiseBean>() {

            @Override
            protected void onSuccees(BaseEntity<GardenPraiseBean> t) throws Exception {
                if (t.isSuccess()) {
                    gardenPraiseBeanLiveData.setValue(t.getData());
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
     * 动态详情
     *
     * @param thread_id
     */

    private MutableLiveData<ThreadDataBean> threadInfoBeanLiveData;

    public MutableLiveData<ThreadDataBean> getThreadInfoBeanLiveData() {
        if (threadInfoBeanLiveData == null)
            threadInfoBeanLiveData = new MutableLiveData<>();
        return threadInfoBeanLiveData;
    }

    /**
     * 动态详情
     * @param thread_id
     */

    @Override
    public void threadInfo(String thread_id) {

        if (TextUtils.isEmpty(thread_id)) {
            return;
        }
        schoolRepository.threadInfo(thread_id).compose(setThread()).subscribe(new BaseObserver<ThreadDataBean>() {
            @Override
            protected void onSuccees(BaseEntity<ThreadDataBean> t) throws Exception {
                if (t.isSuccess()) {
                    threadInfoBeanLiveData.setValue(t.getData());
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
     * 动态评价列表
     *
     * @param thread_id
     * @param start_id
     * @param end_id
     */

    private MutableLiveData<List<EvaluationDataBean>> evaluationDataBeansLiveData;

    public MutableLiveData<List<EvaluationDataBean>> getEvaluationDataBeansLiveData() {
        if (evaluationDataBeansLiveData == null) {
            evaluationDataBeansLiveData = new MutableLiveData<>();
        }
        return evaluationDataBeansLiveData;
    }

    @Override
    public void evaluationData(String thread_id, String start_id, String end_id) {

        if (TextUtils.isEmpty(thread_id)) {
            return;
        }
        schoolRepository.evaluationData(thread_id, start_id, end_id).compose(setThread()).subscribe(new BaseObserver<List<EvaluationDataBean>>() {
            @Override
            protected void onSuccees(BaseEntity<List<EvaluationDataBean>> t) throws Exception {

                if (t.isSuccess()) {
                    evaluationDataBeansLiveData.setValue(t.getData());
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
     * 回复列表
     *
     * @param thread_id
     * @param post_id
     * @param start_id
     * @param end_id
     */
    private MutableLiveData<List<ReplyDataBean>> replyDataBeansLiveData;

    public MutableLiveData<List<ReplyDataBean>> getReplyDataBeansLiveData() {
        if (replyDataBeansLiveData == null)
            replyDataBeansLiveData = new MutableLiveData<>();
        return replyDataBeansLiveData;
    }

    @Override
    public void replyData(String thread_id, String post_id, String start_id, String end_id) {
        if (TextUtils.isEmpty(thread_id)) {
            return;
        }

        schoolRepository.replyData(thread_id, post_id, start_id, end_id).compose(setThread()).subscribe(new BaseObserver<List<ReplyDataBean>>() {
            @Override
            protected void onSuccees(BaseEntity<List<ReplyDataBean>> t) throws Exception {
                if (t.isSuccess()) {
                    replyDataBeansLiveData.setValue(t.getData());
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
     * 添加评论
     *
     * @param thread_id
     * @param content
     * @param post_id
     * @param user_id_at
     */
    private MutableLiveData<String> insertEvaluationLiveData;

    public MutableLiveData<String> getInsertEvaluationLiveData() {
        if (insertEvaluationLiveData == null)
            insertEvaluationLiveData = new MutableLiveData<>();
        return insertEvaluationLiveData;
    }

    @Override
    public void insertEvaluation(String thread_id, String content, String post_id, String user_id_at) {
        if (TextUtils.isEmpty(thread_id)) {
            return;
        }
        schoolRepository.insertEvaluation(thread_id, content, post_id, user_id_at).compose(setThread()).subscribe(new BaseObserver<String>() {
            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {
                if (t.isSuccess()) {
                    insertEvaluationLiveData.setValue(t.getMsg());
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
     * 我的动态
     *
     * @param pageIndex
     * @param pageSize
     */

    private MutableLiveData<MThreadDataBean> mThreadDataBeanLiveData;

    public MutableLiveData<MThreadDataBean> getmThreadDataBeanLiveData() {
        if (mThreadDataBeanLiveData == null)
            mThreadDataBeanLiveData = new MutableLiveData<>();
        return mThreadDataBeanLiveData;
    }

    @Override
    public void mThreadData(int pageIndex, int pageSize, String userId) {
        schoolRepository.mThreadData(pageIndex, pageSize, userId).compose(setThread()).subscribe(new BaseObserver<MThreadDataBean>() {
            @Override
            protected void onSuccees(BaseEntity<MThreadDataBean> t) throws Exception {
                if (t.isSuccess()) {
                    mThreadDataBeanLiveData.setValue(t.getData());
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
     * 删除动态
     *
     * @param thread_id
     */
    private MutableLiveData<String> delThreadLiveData;

    public MutableLiveData<String> getDelThreadLiveData() {
        if (delThreadLiveData == null)
            delThreadLiveData = new MutableLiveData<>();
        return delThreadLiveData;
    }

    @Override
    public void delThread(String thread_id) {
        if (TextUtils.isEmpty(thread_id)) {
            return;
        }
        schoolRepository.delThread(thread_id).compose(setThread()).subscribe(new BaseObserver<String>() {
            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {
                if (t.isSuccess()) {
                    delThreadLiveData.setValue(t.getMsg());
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
