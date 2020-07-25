package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.tiku.CurriculumDataBean;
import com.jianpei.jpeducation.bean.tiku.GetQuestionBean;
import com.jianpei.jpeducation.bean.tiku.InsertRecordBean;
import com.jianpei.jpeducation.bean.tiku.PaperCardBean;
import com.jianpei.jpeducation.bean.tiku.PaperEvaluationBean;
import com.jianpei.jpeducation.bean.tiku.PaperInfoBean;
import com.jianpei.jpeducation.bean.tiku.QuestionDataBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/21
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface AnswerContract {

    interface Repository {
        //获取问题（添加答题记录）
        Observable<BaseEntity<GetQuestionBean>> getQuestion(String source, String index_type, String question_id, String record_id, String answering_time, String answer);

        //获取问题（添加答题记录）
        Observable<BaseEntity<InsertRecordBean>> insertRecord(String paper_id, String record_id, String restart_type);

        //试卷详情
        Observable<BaseEntity<PaperInfoBean>> paperInfo(String paper_id);

        //答题卡
        Observable<BaseEntity<PaperCardBean>> paperCard(String record_id);

        //1-问题收藏/取消收藏
        Observable<BaseEntity<GetQuestionBean>> favorites(String paper_id, String question_id);

        //交卷
        Observable<BaseEntity<PaperEvaluationBean>> paperEvaluation(String record_id, String confirm_status);

        //科目列表
        Observable<BaseEntity<List<CurriculumDataBean>>> curriculumData(String cat_id, String parent_id);

        //解答题评分
        Observable<BaseEntity<GetQuestionBean>> answerScore(String score, String questino_id, String record_id, String index_type);


        //收藏/错题列表
        Observable<BaseEntity<QuestionDataBean>> questionData(String type, String class_id, int pageIndex, int pageSize);
    }

    interface Model {
        void getQuestion(String source, String index_type, String question_id, String record_id, String answering_time, String answer);

        void insertRecord(String paper_id, String record_id, String restart_type);

        void paperInfo(String paper_id);

        void paperCard(String record_id);

        void favorites(String paper_id, String question_id);

        void paperEvaluation(String record_id, String confirm_status);

        void curriculumData(String cat_id, String parent_id);

        void answerScore(String score, String questino_id, String record_id, String index_type);

        void questionData(String type, String class_id, int pageIndex, int pageSize);
    }
}
