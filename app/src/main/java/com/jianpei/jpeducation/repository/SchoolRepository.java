package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.json.AttentionJson;
import com.jianpei.jpeducation.bean.json.DelThreadJson;
import com.jianpei.jpeducation.bean.json.EvaluationDataJson;
import com.jianpei.jpeducation.bean.json.GardenPraiseJson;
import com.jianpei.jpeducation.bean.json.InsertEvaluationJson;
import com.jianpei.jpeducation.bean.json.MThreadDataJson;
import com.jianpei.jpeducation.bean.json.ReplyDataJson;
import com.jianpei.jpeducation.bean.json.ThreadDataJson;
import com.jianpei.jpeducation.bean.json.ThreadInfoJson;
import com.jianpei.jpeducation.bean.json.TopicDataJson;
import com.jianpei.jpeducation.bean.school.AttentionResultBean;
import com.jianpei.jpeducation.bean.school.EvaluationDataBean;
import com.jianpei.jpeducation.bean.school.GardenPraiseBean;
import com.jianpei.jpeducation.bean.school.MThreadDataBean;
import com.jianpei.jpeducation.bean.school.ReplyDataBean;
import com.jianpei.jpeducation.bean.school.ThreadDataBean;
import com.jianpei.jpeducation.bean.school.ThreadFromTopicDataBean;
import com.jianpei.jpeducation.contract.SchoolContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SchoolRepository extends BaseRepository implements SchoolContract.Repository {

    //帖子列表
    @Override
    public Observable<BaseEntity<List<ThreadDataBean>>> threadData(String start_id, String end_id, String follow) {
        return RetrofitFactory.getInstance().API().threadData(new ThreadDataJson(start_id, end_id, follow));
    }

    //话题列表
    @Override
    public Observable<BaseEntity<ThreadFromTopicDataBean>> threadFromTopicData(String start_id, String end_id, String follow, String topic_id, String is_hot) {
        return RetrofitFactory.getInstance().API().threadFromTopicData(new ThreadDataJson(start_id, end_id, follow, topic_id, is_hot));
    }

    //关注/取消关注
    @Override
    public Observable<BaseEntity<AttentionResultBean>> attention(String attention_id, String topic_id, String thread_id, List<ThreadDataBean> mThreadDataBeans) {
        return RetrofitFactory.getInstance().API().attention(new AttentionJson(attention_id, topic_id, thread_id)).map(new Function<BaseEntity<AttentionResultBean>, BaseEntity<AttentionResultBean>>() {
            @Override
            public BaseEntity<AttentionResultBean> apply(BaseEntity<AttentionResultBean> attentionResultBeanBaseEntity) throws Exception {
                if (mThreadDataBeans == null) {
                    return attentionResultBeanBaseEntity;
                }
                if (attentionResultBeanBaseEntity.isSuccess()) {
                    for (ThreadDataBean threadDataBean : mThreadDataBeans) {
                        if (threadDataBean.getUser_id().equals(attentionResultBeanBaseEntity.getData().getAttention_id())) {
                            threadDataBean.setIs_attention(attentionResultBeanBaseEntity.getData().getIs_attention());
                        }
                    }
                }
                return attentionResultBeanBaseEntity;
            }
        });
    }


//    @Override
//    public Observable<BaseEntity<ThreadDataBean>> attention(String attention_id, String topic_id, String thread_id, List<ThreadDataBean> mThreadDataBeans) {
//        return RetrofitFactory.getInstance().API().attention(new AttentionJson(attention_id, topic_id, thread_id)).map(new Function<BaseEntity<ThreadDataBean>, BaseEntity<ThreadDataBean>>() {
//            @Override
//            public BaseEntity<ThreadDataBean> apply(BaseEntity<ThreadDataBean> threadDataBeanBaseEntity) throws Exception {
//                if (mThreadDataBeans == null) {
//                    return threadDataBeanBaseEntity;
//                }
//                if (threadDataBeanBaseEntity.isSuccess()) {
//                    for (ThreadDataBean threadDataBean : mThreadDataBeans) {
//                        if (threadDataBean.getUser_id().equals(threadDataBeanBaseEntity.getData().getUser_id())) {
//                            threadDataBean.setIs_attention(threadDataBeanBaseEntity.getData().getIs_attention());
//                            threadDataBean.setIs_praise(threadDataBeanBaseEntity.getData().getIs_praise());
//                            threadDataBean.setLike_num(threadDataBeanBaseEntity.getData().getLike_num());
//                        }
//                    }
//                }
//                return threadDataBeanBaseEntity;
//            }
//        });
//    }

    //点赞/取消点赞
    @Override
    public Observable<BaseEntity<GardenPraiseBean>> gardenPraise(String type, String thread_id, String topic_id, String post_id) {
        return RetrofitFactory.getInstance().API().gardenPraise(new GardenPraiseJson(type, thread_id, topic_id, post_id));
    }

    //动态详情
    @Override
    public Observable<BaseEntity<ThreadDataBean>> threadInfo(String thread_id) {
        return RetrofitFactory.getInstance().API().threadInfo(new ThreadInfoJson(thread_id));
    }

    //动态评价列表
    @Override
    public Observable<BaseEntity<List<EvaluationDataBean>>> evaluationData(String thread_id, String start_id, String end_id) {
        return RetrofitFactory.getInstance().API().evaluationData(new EvaluationDataJson(thread_id, start_id, end_id));
    }

    //回复列表
    @Override
    public Observable<BaseEntity<List<ReplyDataBean>>> replyData(String thread_id, String post_id, String start_id, String end_id) {
        return RetrofitFactory.getInstance().API().replyData(new ReplyDataJson(thread_id, post_id, start_id, end_id));
    }

    //添加评论
    @Override
    public Observable<BaseEntity<String>> insertEvaluation(String thread_id, String content, String post_id, String user_id_at) {
        return RetrofitFactory.getInstance().API().insertEvaluation(new InsertEvaluationJson(thread_id, content, post_id, user_id_at));
    }

    //我的动态
    @Override
    public Observable<BaseEntity<MThreadDataBean>> mThreadData(int pageIndex, int pageSize, String userId) {
        return RetrofitFactory.getInstance().API().mThreadData(new MThreadDataJson(pageIndex, pageSize, userId));
    }

    //删除动态
    @Override
    public Observable<BaseEntity<String>> delThread(String thread_id) {
        return RetrofitFactory.getInstance().API().delThread(new DelThreadJson(thread_id));
    }
}
