package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.json.AnswerScoreJson;
import com.jianpei.jpeducation.bean.json.ClosePaperJson;
import com.jianpei.jpeducation.bean.json.CurriculumDataJson;
import com.jianpei.jpeducation.bean.json.FavoritesJson;
import com.jianpei.jpeducation.bean.json.GetQuestionJson;
import com.jianpei.jpeducation.bean.json.InsertRecordJson;
import com.jianpei.jpeducation.bean.json.PaperCardJson;
import com.jianpei.jpeducation.bean.json.PaperEvaluationJson;
import com.jianpei.jpeducation.bean.json.PaperInfoJson;
import com.jianpei.jpeducation.bean.json.QuestionDataJson;
import com.jianpei.jpeducation.bean.tiku.CurriculumDataBean;
import com.jianpei.jpeducation.bean.tiku.GetQuestionBean;
import com.jianpei.jpeducation.bean.tiku.InsertRecordBean;
import com.jianpei.jpeducation.bean.tiku.PaperCardBean;
import com.jianpei.jpeducation.bean.tiku.PaperEvaluationBean;
import com.jianpei.jpeducation.bean.tiku.PaperInfoBean;
import com.jianpei.jpeducation.bean.tiku.QuestionDataBean;
import com.jianpei.jpeducation.contract.AnswerContract;

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
public class AnswerRepository extends BaseRepository implements AnswerContract.Repository {

    @Override
    public Observable<BaseEntity<GetQuestionBean>> getQuestion(String source, String index_type, String question_id, String record_id, String answering_time, String answer,String class_id) {
        if ("2".equals(source) || "3".equals(source)) {//搜藏/错题集
            return RetrofitFactory.getInstance().API().getQuestion(new GetQuestionJson(source, index_type, question_id, class_id, answer));
        } else {
            return RetrofitFactory.getInstance().API().getQuestion(new GetQuestionJson(source, index_type, question_id, record_id, answering_time, answer));

        }

    }

    @Override
    public Observable<BaseEntity<InsertRecordBean>> insertRecord(String paper_id, String record_id, String restart_type) {
        return RetrofitFactory.getInstance().API().insertRecord(new InsertRecordJson(paper_id, record_id, restart_type));
    }

    /**
     * 1-试卷详情
     *
     * @param paper_id
     * @return
     */
    @Override
    public Observable<BaseEntity<PaperInfoBean>> paperInfo(String paper_id) {
        return RetrofitFactory.getInstance().API().paperInfo(new PaperInfoJson(paper_id));
    }

    /**
     * 答题卡
     *
     * @param record_id
     * @return
     */
    @Override
    public Observable<BaseEntity<PaperCardBean>> paperCard(String record_id) {
        return RetrofitFactory.getInstance().API().paperCard(new PaperCardJson(record_id));
    }

    /**
     * 1-问题收藏/取消收藏
     *
     * @param paper_id
     * @param question_id
     * @return
     */
    @Override
    public Observable<BaseEntity<GetQuestionBean>> favorites(String paper_id, String question_id) {
        return RetrofitFactory.getInstance().API().favorites(new FavoritesJson(paper_id, question_id));
    }

    /**
     * 交卷
     *
     * @param record_id
     * @param confirm_status
     * @return
     */
    @Override
    public Observable<BaseEntity<PaperEvaluationBean>> paperEvaluation(String record_id, String confirm_status) {
        return RetrofitFactory.getInstance().API().paperEvaluation(new PaperEvaluationJson(record_id, confirm_status));
    }

    /**
     * 科目列表
     *
     * @param cat_id
     * @param parent_id
     * @return
     */
    @Override
    public Observable<BaseEntity<List<CurriculumDataBean>>> curriculumData(String cat_id, String parent_id) {
        return RetrofitFactory.getInstance().API().curriculumData(new CurriculumDataJson(cat_id, parent_id));
    }

    /**
     * 解答题评分
     *
     * @param score
     * @param questino_id
     * @param record_id
     * @param index_type
     * @return
     */

    @Override
    public Observable<BaseEntity<GetQuestionBean>> answerScore(String score, String questino_id, String record_id, String index_type) {
        return RetrofitFactory.getInstance().API().answerScore(new AnswerScoreJson(score, questino_id, record_id, index_type));
    }

    /**
     * 收藏/错题列表
     *
     * @param type
     * @param class_id
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public Observable<BaseEntity<QuestionDataBean>> questionData(int type, String class_id, int pageIndex, int pageSize) {
        return RetrofitFactory.getInstance().API().questionData(new QuestionDataJson(type, class_id, pageIndex, pageSize));
    }

    /**
     * 结束答题
     *
     * @param record_id
     * @param paper_id
     * @param type
     * @return
     */
    @Override
    public Observable<BaseEntity<String>> closePaper(String record_id, String paper_id, String type) {
        return RetrofitFactory.getInstance().API().closePaper(new ClosePaperJson(record_id, paper_id, type));
    }
}
