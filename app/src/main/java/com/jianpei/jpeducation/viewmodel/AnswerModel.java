package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.tiku.AnswerBean;
import com.jianpei.jpeducation.bean.tiku.CurriculumDataBean;
import com.jianpei.jpeducation.bean.tiku.GetQuestionBean;
import com.jianpei.jpeducation.bean.tiku.InsertRecordBean;
import com.jianpei.jpeducation.bean.tiku.PaperCardBean;
import com.jianpei.jpeducation.bean.tiku.PaperEvaluationBean;
import com.jianpei.jpeducation.bean.tiku.PaperInfoBean;
import com.jianpei.jpeducation.bean.tiku.QuestionDataBean;
import com.jianpei.jpeducation.contract.AnswerContract;
import com.jianpei.jpeducation.repository.AnswerRepository;
import com.jianpei.jpeducation.utils.L;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/21
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class AnswerModel extends BaseViewModel implements AnswerContract.Model {

    private AnswerRepository answerRepository;

    public AnswerModel() {
        answerRepository = new AnswerRepository();
    }

    /**
     * 获取问题（添加答题记录）
     *
     * @param source
     * @param index_type
     * @param question_id
     * @param record_id
     * @param answering_time
     * @param answer
     */
    private MutableLiveData<GetQuestionBean> questionBeanLiveData;

    public MutableLiveData<GetQuestionBean> getQuestionBeanLiveData() {
        if (questionBeanLiveData == null)
            questionBeanLiveData = new MutableLiveData<>();
        return questionBeanLiveData;
    }

    @Override
    public void getQuestion(String source, String index_type, String question_id, String record_id, String answering_time, String answers,String classID) {
        L.e("=========我的答案:" + answers);
        answerRepository.getQuestion(source, index_type, question_id, record_id, answering_time, answers,classID).compose(setThread()).subscribe(new BaseObserver<GetQuestionBean>() {
            @Override
            protected void onSuccees(BaseEntity<GetQuestionBean> t) throws Exception {
                if (t.isSuccess()) {
                    questionBeanLiveData.setValue(t.getData());
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
     * 获取问题（添加答题记录）
     *
     * @param paper_id
     * @param record_id
     * @param restart_type
     */

    private MutableLiveData<InsertRecordBean> insertRecordBeanLiveData;

    public MutableLiveData<InsertRecordBean> getInsertRecordBeanLiveData() {
        if (insertRecordBeanLiveData == null)
            insertRecordBeanLiveData = new MutableLiveData<>();
        return insertRecordBeanLiveData;
    }

    @Override
    public void insertRecord(String paper_id, String record_id, String restart_type) {
        if (TextUtils.isEmpty(paper_id)) {
            errData.setValue("paper_id不能为null");
            return;
        }
        answerRepository.insertRecord(paper_id, record_id, restart_type).compose(setThread()).subscribe(new BaseObserver<InsertRecordBean>() {
            @Override
            protected void onSuccees(BaseEntity<InsertRecordBean> t) throws Exception {
                if (t.isSuccess()) {
                    insertRecordBeanLiveData.setValue(t.getData());
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
     * 试卷详情
     *
     * @param paper_id
     */

    private MutableLiveData<PaperInfoBean> paperInfoBeanLiveData;

    public MutableLiveData<PaperInfoBean> getPaperInfoBeanLiveData() {
        if (paperInfoBeanLiveData == null)
            paperInfoBeanLiveData = new MutableLiveData<>();
        return paperInfoBeanLiveData;
    }

    @Override
    public void paperInfo(String paper_id) {
        if (TextUtils.isEmpty(paper_id)) {
            errData.setValue("paper_id不能为null");
            return;
        }
        answerRepository.paperInfo(paper_id).compose(setThread()).subscribe(new BaseObserver<PaperInfoBean>() {
            @Override
            protected void onSuccees(BaseEntity<PaperInfoBean> t) throws Exception {
                if (t.isSuccess()) {
                    paperInfoBeanLiveData.setValue(t.getData());
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
     * 答题卡
     *
     * @param record_type
     */

    private MutableLiveData<PaperCardBean> paperCardBeanLiveData;

    public MutableLiveData<PaperCardBean> getPaperCardBeanLiveData() {
        if (paperCardBeanLiveData == null)
            paperCardBeanLiveData = new MutableLiveData<>();
        return paperCardBeanLiveData;
    }

    @Override
    public void paperCard(String recordId) {
        if (TextUtils.isEmpty(recordId)) {
            errData.setValue("record_type不能为null");
            return;
        }
        answerRepository.paperCard(recordId).compose(setThread()).subscribe(new BaseObserver<PaperCardBean>() {
            @Override
            protected void onSuccees(BaseEntity<PaperCardBean> t) throws Exception {
                if (t.isSuccess()) {
                    paperCardBeanLiveData.setValue(t.getData());
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
     * 1-问题收藏/取消收藏
     *
     * @param paper_id
     * @param question_id
     */
    private MutableLiveData<GetQuestionBean> favoriteLiveData;

    public MutableLiveData<GetQuestionBean> getFavoriteLiveData() {
        if (favoriteLiveData == null)
            favoriteLiveData = new MutableLiveData<>();
        return favoriteLiveData;
    }

    @Override
    public void favorites(String paper_id, String question_id) {
        if (TextUtils.isEmpty(paper_id)) {
            errData.setValue("paper_id不能为null");
            return;
        }
        if (TextUtils.isEmpty(question_id)) {
            errData.setValue("question_id不能为null");
            return;
        }

        answerRepository.favorites(paper_id, question_id).compose(setThread()).subscribe(new BaseObserver<GetQuestionBean>() {
            @Override
            protected void onSuccees(BaseEntity<GetQuestionBean> t) throws Exception {

                if (t.isSuccess()) {
                    favoriteLiveData.setValue(t.getData());
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
     * 交卷
     *
     * @param record_id
     * @param confirm_status
     */
    private MutableLiveData<PaperEvaluationBean> paperEvaluationBeanLiveData;

    public MutableLiveData<PaperEvaluationBean> getPaperEvaluationBeanLiveData() {
        if (paperEvaluationBeanLiveData == null)
            paperEvaluationBeanLiveData = new MutableLiveData<>();
        return paperEvaluationBeanLiveData;
    }

    @Override
    public void paperEvaluation(String record_id, String confirm_status) {
        if (TextUtils.isEmpty(record_id)) {
            errData.setValue("record_id不能为null");
            return;
        }
        answerRepository.paperEvaluation(record_id, confirm_status).compose(setThread()).subscribe(new BaseObserver<PaperEvaluationBean>() {
            @Override
            protected void onSuccees(BaseEntity<PaperEvaluationBean> t) throws Exception {
                if (t.isSuccess()) {
                    paperEvaluationBeanLiveData.setValue(t.getData());
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
     * 科目列表
     *
     * @param cat_id
     * @param parent_id
     */

    private MutableLiveData<List<CurriculumDataBean>> curriculumDataBeans;

    public MutableLiveData<List<CurriculumDataBean>> getCurriculumDataBeans() {
        if (curriculumDataBeans == null)
            curriculumDataBeans = new MutableLiveData<>();
        return curriculumDataBeans;
    }

    @Override
    public void curriculumData(String cat_id, String parent_id) {

        answerRepository.curriculumData(cat_id, parent_id).compose(setThread()).subscribe(new BaseObserver<List<CurriculumDataBean>>() {
            @Override
            protected void onSuccees(BaseEntity<List<CurriculumDataBean>> t) throws Exception {
                if (t.isSuccess()) {
                    curriculumDataBeans.setValue(t.getData());
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
     * 解答题平分
     *
     * @param score
     * @param questino_id
     * @param record_id
     * @param index_type
     */
    @Override
    public void answerScore(String score, String questino_id, String record_id, String index_type) {

        answerRepository.answerScore(score, questino_id, record_id, index_type).compose(setThread()).subscribe(new BaseObserver<GetQuestionBean>() {

            @Override
            protected void onSuccees(BaseEntity<GetQuestionBean> t) throws Exception {
                if (t.isSuccess()) {
                    questionBeanLiveData.setValue(t.getData());
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
     * -收藏/错题列表
     *
     * @param type
     * @param class_id
     * @param pageIndex
     * @param pageSize
     */
    private MutableLiveData<QuestionDataBean> questionDataBeanLiveData;

    public MutableLiveData<QuestionDataBean> getQuestionDataBeanLiveData() {
        if (questionDataBeanLiveData == null)
            questionDataBeanLiveData = new MutableLiveData<>();
        return questionDataBeanLiveData;
    }

    @Override
    public void questionData(int type, String class_id, int pageIndex, int pageSize) {

        answerRepository.questionData(type, class_id, pageIndex, pageSize).compose(setThread()).subscribe(new BaseObserver<QuestionDataBean>() {

            @Override
            protected void onSuccees(BaseEntity<QuestionDataBean> t) throws Exception {
                if (t.isSuccess()) {
                    questionDataBeanLiveData.setValue(t.getData());
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
     * 结束答题
     *
     * @param record_id
     * @param paper_id
     * @param type
     */

    private MutableLiveData<String> closePaperLiveData;

    public MutableLiveData<String> getClosePaperLiveData() {
        if (closePaperLiveData == null)
            closePaperLiveData = new MutableLiveData<>();
        return closePaperLiveData;
    }

    @Override
    public void closePaper(String record_id, String paper_id, String type) {

        answerRepository.closePaper(record_id, paper_id, type).compose(setThread()).subscribe(new BaseObserver<String>() {
            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {
                if (t.isSuccess()) {
                    closePaperLiveData.setValue(t.getData());
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
