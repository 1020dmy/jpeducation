package com.jianpei.jpeducation.api;


import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.config.UrlConfig;
import com.jianpei.jpeducation.bean.BindPhoneJson;
import com.jianpei.jpeducation.bean.CommentListBean;
import com.jianpei.jpeducation.bean.CouponDataBean;
import com.jianpei.jpeducation.bean.CouponDataJson;
import com.jianpei.jpeducation.bean.CouponReceiveJson;
import com.jianpei.jpeducation.bean.DisciplinesBean;
import com.jianpei.jpeducation.bean.DownloadBean;
import com.jianpei.jpeducation.bean.DownloadJson;
import com.jianpei.jpeducation.bean.ForgetPwdJson;
import com.jianpei.jpeducation.bean.IntegrlPayJson;
import com.jianpei.jpeducation.bean.NoticeDataBean;
import com.jianpei.jpeducation.bean.SubMaterialDataJson;
import com.jianpei.jpeducation.bean.VersionDetectBean;
import com.jianpei.jpeducation.bean.classinfo.ClassInfoBean;
import com.jianpei.jpeducation.bean.classinfo.DirectoryProfessionBean;
import com.jianpei.jpeducation.bean.classinfo.DirectorySectionBean;
import com.jianpei.jpeducation.bean.classinfo.GroupClassBean;
import com.jianpei.jpeducation.bean.classinfo.GroupCouponBean;
import com.jianpei.jpeducation.bean.classinfo.ImputedPriceBean;
import com.jianpei.jpeducation.bean.classinfo.RegimentBean;
import com.jianpei.jpeducation.bean.classinfo.RegimentDataBean;
import com.jianpei.jpeducation.bean.classinfo.VideoUrlBean;
import com.jianpei.jpeducation.bean.elective.GroupHomeBean;
import com.jianpei.jpeducation.bean.gold.CashWithdrawalBean;
import com.jianpei.jpeducation.bean.gold.VirtualCurrencyListBean;
import com.jianpei.jpeducation.bean.gold.WithdrawalDataBean;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;
import com.jianpei.jpeducation.bean.homedata.HomeDataBean;
import com.jianpei.jpeducation.bean.HomeInfoJson;
import com.jianpei.jpeducation.bean.LauncherBean;
import com.jianpei.jpeducation.bean.LoginJson;
import com.jianpei.jpeducation.bean.SendCodeJson;
import com.jianpei.jpeducation.bean.UserInfoBean;
import com.jianpei.jpeducation.bean.WxLoginJson;
import com.jianpei.jpeducation.bean.integral.IntegralDataBean;
import com.jianpei.jpeducation.bean.integral.IntegralInfoBean;
import com.jianpei.jpeducation.bean.integral.IntegralTaskBean;
import com.jianpei.jpeducation.bean.json.AnswerScoreJson;
import com.jianpei.jpeducation.bean.json.AttentionJson;
import com.jianpei.jpeducation.bean.json.CancelOrderJson;
import com.jianpei.jpeducation.bean.json.CarInfoJson;
import com.jianpei.jpeducation.bean.json.CashWithdrawalJson;
import com.jianpei.jpeducation.bean.json.CheckPayStatusJson;
import com.jianpei.jpeducation.bean.json.ClassDataJson;
import com.jianpei.jpeducation.bean.json.ClassGenerateOrderJson;
import com.jianpei.jpeducation.bean.json.ClassInfoJson;
import com.jianpei.jpeducation.bean.json.ClosePaperJson;
import com.jianpei.jpeducation.bean.json.CommentListJson;
import com.jianpei.jpeducation.bean.json.CurriculumDataJson;
import com.jianpei.jpeducation.bean.json.DelThreadJson;
import com.jianpei.jpeducation.bean.json.EditUserJson;
import com.jianpei.jpeducation.bean.json.EvaluationDataJson;
import com.jianpei.jpeducation.bean.json.FavoritesJson;
import com.jianpei.jpeducation.bean.json.FeedbackJson;
import com.jianpei.jpeducation.bean.json.GardenPraiseJson;
import com.jianpei.jpeducation.bean.json.GetQuestionJson;
import com.jianpei.jpeducation.bean.json.GroupCouponJson;
import com.jianpei.jpeducation.bean.json.GroupDataJson;
import com.jianpei.jpeducation.bean.json.GroupInfoJson;
import com.jianpei.jpeducation.bean.json.ImputedPriceJson;
import com.jianpei.jpeducation.bean.json.InsertCarJson;
import com.jianpei.jpeducation.bean.json.InsertCommentJson;
import com.jianpei.jpeducation.bean.json.InsertEvaluationJson;
import com.jianpei.jpeducation.bean.json.InsertGardenJson;
import com.jianpei.jpeducation.bean.json.InsertRecordJson;
import com.jianpei.jpeducation.bean.json.IntegralDataJson;
import com.jianpei.jpeducation.bean.json.MThreadDataJson;
import com.jianpei.jpeducation.bean.json.MaterialDataJson;
import com.jianpei.jpeducation.bean.json.OrderDataJson;
import com.jianpei.jpeducation.bean.json.OrderInfoJson;
import com.jianpei.jpeducation.bean.json.OrderPaymentJson;
import com.jianpei.jpeducation.bean.json.PaperCardJson;
import com.jianpei.jpeducation.bean.json.PaperDataJson;
import com.jianpei.jpeducation.bean.json.PaperEvaluationJson;
import com.jianpei.jpeducation.bean.json.PaperInfoJson;
import com.jianpei.jpeducation.bean.json.QuestionDataJson;
import com.jianpei.jpeducation.bean.json.RegimentDataJson;
import com.jianpei.jpeducation.bean.json.RegimentInfoJson;
import com.jianpei.jpeducation.bean.json.RemoveCarJson;
import com.jianpei.jpeducation.bean.json.ReplyDataJson;
import com.jianpei.jpeducation.bean.json.ThreadDataJson;
import com.jianpei.jpeducation.bean.json.ThreadInfoJson;
import com.jianpei.jpeducation.bean.json.TopicDataJson;
import com.jianpei.jpeducation.bean.json.UpdateMessageStatusJson;
import com.jianpei.jpeducation.bean.json.UpdateScheduleJson;
import com.jianpei.jpeducation.bean.json.VideoUrlJson;
import com.jianpei.jpeducation.bean.json.ViodListJson;
import com.jianpei.jpeducation.bean.material.MaterialDataBean;
import com.jianpei.jpeducation.bean.material.MaterialInfoBean;
import com.jianpei.jpeducation.bean.mclass.ClassDataBean;
import com.jianpei.jpeducation.bean.mclass.MClassInfoBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.bean.mine.MessageDataBean;
import com.jianpei.jpeducation.bean.order.CheckPayStatusBean;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.bean.order.MIneOrderInfoBean;
import com.jianpei.jpeducation.bean.order.OrderInfoBean;
import com.jianpei.jpeducation.bean.order.OrderListBean;
import com.jianpei.jpeducation.bean.order.OrderPaymentBean;
import com.jianpei.jpeducation.bean.school.AttentionDataBean;
import com.jianpei.jpeducation.bean.school.AttentionResultBean;
import com.jianpei.jpeducation.bean.school.EvaluationDataBean;
import com.jianpei.jpeducation.bean.school.GardenPraiseBean;
import com.jianpei.jpeducation.bean.school.MThreadDataBean;
import com.jianpei.jpeducation.bean.school.ReplyDataBean;
import com.jianpei.jpeducation.bean.school.ThreadDataBean;
import com.jianpei.jpeducation.bean.school.ThreadFromTopicDataBean;
import com.jianpei.jpeducation.bean.school.TopicDataBean;
import com.jianpei.jpeducation.bean.tiku.CurriculumDataBean;
import com.jianpei.jpeducation.bean.tiku.GetQuestionBean;
import com.jianpei.jpeducation.bean.tiku.InsertRecordBean;
import com.jianpei.jpeducation.bean.tiku.PaperCardBean;
import com.jianpei.jpeducation.bean.tiku.PaperDataBean;
import com.jianpei.jpeducation.bean.tiku.PaperEvaluationBean;
import com.jianpei.jpeducation.bean.tiku.PaperHomeBean;
import com.jianpei.jpeducation.bean.tiku.PaperInfoBean;
import com.jianpei.jpeducation.bean.tiku.QuestionDataBean;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/7
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface APIFunction {


    /**
     * 获取启动图和引导图
     *
     * @return
     */
    @POST(UrlConfig.appset)
    Observable<BaseEntity<LauncherBean>> appSet();

    /**
     * 账号登陆接口
     *
     * @param loginJson
     * @return
     */
//    @POST(UrlConfig.LOGIN)
//    Observable<BaseEntity<LoginBean>> login(@Body LoginJson loginJson);

    /**
     * 获取验证码接口
     */
    @POST(UrlConfig.sendCode)
    Observable<BaseEntity<String>> getCode(@Body SendCodeJson sendCodeJson);


    /**
     * 用户验证码登陆
     */
    @POST(UrlConfig.codeLogin)
    Observable<BaseEntity<UserInfoBean>> codeLogin(@Body LoginJson loginJson);

    /**
     * 忘记密码
     */
    @POST(UrlConfig.forgetPwd)
    Observable<BaseEntity<String>> forgetPwd(@Body ForgetPwdJson forgetPwdJson);

    /**
     * 获取专业列表
     */
    @POST(UrlConfig.courseData)
    Observable<BaseEntity<ArrayList<DisciplinesBean>>> getCourseData();

    /**
     * 首页数据
     */
    @POST(UrlConfig.homeInfo)
    Observable<BaseEntity<HomeDataBean>> getHomeInfo(@Body HomeInfoJson homeInfoJson);

    /**
     * 微信登陆
     */
    @POST(UrlConfig.wxLogin)
    Observable<BaseEntity<UserInfoBean>> wxLogin(@Body WxLoginJson wxLoginJson);

    /**
     * 绑定手机号
     */
    @POST(UrlConfig.bindPhone)
    Observable<BaseEntity<UserInfoBean>> bingPhone(@Body BindPhoneJson bindPhoneJson);

    /**
     * 退出登陆
     */
    @POST(UrlConfig.loginOut)
    Observable<BaseEntity<String>> loginOut();

    /**
     * 领取优惠券
     */
    @POST(UrlConfig.couponReceive)
    Observable<BaseEntity<String>> couponReceive(@Body CouponReceiveJson couponReceiveJson);

    /**
     * 我的优惠券列表
     */
    @POST(UrlConfig.couponData)
    Observable<BaseEntity<CouponDataBean>> couponData(@Body CouponDataJson couponDataJson);


    /**
     * 首页公告
     */
    @POST(UrlConfig.noticeData)
    Observable<BaseEntity<ArrayList<NoticeDataBean>>> noticeData(@Body HomeInfoJson homeInfoJson);


    /**
     * 1-班级课程详情（含团购）
     */
    @POST(UrlConfig.groupInfo)
    Observable<BaseEntity<ClassInfoBean>> groupInfo(@Body GroupInfoJson groupInfoJson);

    /**
     * 班级课程目录
     */
    @POST(UrlConfig.classDirectory)
    Observable<BaseEntity<List<DirectoryProfessionBean>>> classDirectory(@Body GroupInfoJson groupInfoJson);

    /**
     * 评价列表
     */
    @POST(UrlConfig.commentList)
    Observable<BaseEntity<CommentListBean>> commentList(@Body CommentListJson commentListJson);

    /**
     * 购买选择课程科目（含团购）
     */
    @POST(UrlConfig.groupClass)
    Observable<BaseEntity<List<GroupClassBean>>> groupClass(@Body GroupInfoJson groupInfoJson);

    /**
     * 课程优惠券列表
     */
    @POST(UrlConfig.groupCoupon)
    Observable<BaseEntity<List<GroupCouponBean>>> groupCoupon(@Body GroupCouponJson groupCouponJson);

    /**
     * 课程章节列表
     */
    @POST(UrlConfig.viodList)
    Observable<BaseEntity<List<ViodBean>>> viodList(@Body ViodListJson viodListJson);

    /**
     * 获取视频播放url
     */
    @POST(UrlConfig.videoUrl)
    Observable<BaseEntity<VideoUrlBean>> videoUrl(@Body VideoUrlJson videoUrlJson);

    /**
     * 购买课程下单/计算价格
     */
    @POST(UrlConfig.classGenerateOrder)
    Observable<BaseEntity<MIneOrderInfoBean>> classGenerateOrder(@Body ClassGenerateOrderJson classGenerateOrderJson);

//    Observable<BaseEntity<ClassGenerateOrderBean>> classGenerateOrder(@Body ClassGenerateOrderJson classGenerateOrderJson);

    /**
     * 1-订单详情
     */
    @POST(UrlConfig.orderInfo)
    Observable<BaseEntity<MIneOrderInfoBean>> orderInfo(@Body OrderInfoJson orderInfoJson);

    /**
     * 1-计算价格
     */
    @POST(UrlConfig.imputedPrice)
    Observable<BaseEntity<ImputedPriceBean>> imputedPrice(@Body ImputedPriceJson imputedPriceJson);


    /**
     * 1-参与拼团详情
     */
    @POST(UrlConfig.regimentInfo)
    Observable<BaseEntity<RegimentBean>> regimentInfo(@Body RegimentInfoJson regimentInfoJson);


    /**
     * 1-团列表
     */
    @POST(UrlConfig.regimentData)
    Observable<BaseEntity<RegimentDataBean>> regimentData(@Body RegimentDataJson regimentDataJson);


    /**
     * 1-订单发起支付
     */
    @POST(UrlConfig.orderPayment)
    Observable<BaseEntity<OrderPaymentBean>> orderPayment(@Body OrderPaymentJson orderPaymentJson);

    /**
     * 支付状态查询
     */
    @POST(UrlConfig.checkPayStatus)
    Observable<BaseEntity<MIneOrderInfoBean>> checkPayStatus(@Body CheckPayStatusJson checkPayStatusJson);

    /**
     * 添加购物车
     */
    @POST(UrlConfig.insertCar)
    Observable<BaseEntity<String>> insertCar(@Body InsertCarJson insertCarJson);

    /**
     * 从购物车删除
     */
    @POST(UrlConfig.removeCar)
    Observable<BaseEntity<String>> removeCar(@Body RemoveCarJson removeCarJson);

    /**
     * 购物车详情
     */
    @POST(UrlConfig.carInfo)
    Observable<BaseEntity<MIneOrderInfoBean>> carInfo(@Body CarInfoJson carInfoJson);

//    Observable<BaseEntity<ClassGenerateOrderBean>> carInfo(@Body CarInfoJson carInfoJson);


    /**
     * 1-订单列表
     */
    @POST(UrlConfig.orderData)
    Observable<BaseEntity<OrderListBean>> orderData(@Body OrderDataJson orderDataJson);

    /**
     * 1-添加评价
     */
    @POST(UrlConfig.insertComment)
    Observable<BaseEntity<String>> insertComment(@Body InsertCommentJson insertCommentJson);

    /**
     * 1-取消订单
     */
    @POST(UrlConfig.cancelOrder)
    Observable<BaseEntity<String>> cancelOrder(@Body CancelOrderJson cancelOrderJson);

    /**
     * 1--积分首页
     */
    @POST(UrlConfig.integralInfo)
    Observable<BaseEntity<IntegralInfoBean>> integralInfo(@Body CarInfoJson carInfoJson);

    /**
     * 1-积分任务列表
     */
    @POST(UrlConfig.integralTask)
    Observable<BaseEntity<List<IntegralTaskBean>>> integralTask(@Body CarInfoJson carInfoJson);

    /**
     * 积分支付
     */
    @POST(UrlConfig.integrlPay)
    Observable<BaseEntity<String>> integrlPay(@Body IntegrlPayJson integrlPayJson);

    /**
     * 积分列表
     */
    @POST(UrlConfig.integralData)
    Observable<BaseEntity<IntegralDataBean>> integralData(@Body IntegralDataJson integralDataJson);

    /**
     * 资料一级列表
     */
    @POST(UrlConfig.materialData)
    Observable<BaseEntity<MaterialDataBean>> materialData(@Body MaterialDataJson materialDataJson);

    /**
     * 资料二级列表
     */
    @POST(UrlConfig.subMaterialData)
    Observable<BaseEntity<ArrayList<MaterialInfoBean>>> subMaterialData(@Body SubMaterialDataJson subMaterialDataJson);

    /**
     * 资料下载
     */
    @POST(UrlConfig.getDownloadUrl)
    Observable<BaseEntity<DownloadBean>> getDownloadUrl(@Body DownloadJson downloadJson);

    /**
     * 1-我的资料
     */
    @POST(UrlConfig.myMaterialData)
    Observable<BaseEntity<MaterialDataBean>> myMaterialData(@Body MaterialDataJson integralDataJson);

    /**
     * 1-我的课程列表
     */
    @POST(UrlConfig.classData)
    Observable<BaseEntity<ClassDataBean>> classData(@Body ClassDataJson classDataJson);

    /**
     * 1-1-我的课程详情
     */
    @POST(UrlConfig.classInfo)
    Observable<BaseEntity<MClassInfoBean>> classInfo(@Body ClassInfoJson classInfoJson);

    /**
     * 1-1-更新看课记录
     */
    @POST(UrlConfig.updateSchedule)
    Observable<BaseEntity<String>> updateSchedule(@Body UpdateScheduleJson updateScheduleJson);

    /**
     * 话题列表
     */
    @POST(UrlConfig.topicData)
    Observable<BaseEntity<TopicDataBean>> topicData(@Body TopicDataJson topicDataJson);

    /**
     * 我的关注用户列表
     */
    @POST(UrlConfig.attentionData)
    Observable<BaseEntity<AttentionDataBean>> attentionData(@Body TopicDataJson topicDataJson);


    /**
     * 发布动态
     */
    @POST(UrlConfig.insertGarden)
    Observable<BaseEntity<String>> insertGarden(@Body InsertGardenJson insertGardenJson);

    /**
     * 广场帖子列表
     */
    @POST(UrlConfig.threadData)
    Observable<BaseEntity<List<ThreadDataBean>>> threadData(@Body ThreadDataJson threadDataJson);

    /**
     * 话题帖子列表
     */
    @POST(UrlConfig.threadData)
    Observable<BaseEntity<ThreadFromTopicDataBean>> threadFromTopicData(@Body ThreadDataJson threadDataJson);

    /**
     * 关注/取消关注
     */
    @POST(UrlConfig.attention)
    Observable<BaseEntity<AttentionResultBean>> attention(@Body AttentionJson attentionJson);
//    Observable<BaseEntity<ThreadDataBean>> attention(@Body AttentionJson attentionJson);

    /**
     * 1-点赞/取消点赞
     */
    @POST(UrlConfig.gardenPraise)
    Observable<BaseEntity<GardenPraiseBean>> gardenPraise(@Body GardenPraiseJson gardenPraiseJson);


    /**
     * 1-动态详情
     */
    @POST(UrlConfig.threadInfo)
    Observable<BaseEntity<ThreadDataBean>> threadInfo(@Body ThreadInfoJson threadInfoJson);

    /**
     * 1-动态评价列表
     */
    @POST(UrlConfig.evaluationData)
    Observable<BaseEntity<List<EvaluationDataBean>>> evaluationData(@Body EvaluationDataJson evaluationDataJson);

    /**
     * 1-回复列表
     */
    @POST(UrlConfig.replyData)
    Observable<BaseEntity<List<ReplyDataBean>>> replyData(@Body ReplyDataJson replyDataJson);

    /**
     * 1-添加评论
     */
    @POST(UrlConfig.insertEvaluation)
    Observable<BaseEntity<String>> insertEvaluation(@Body InsertEvaluationJson insertEvaluationJson);

    /**
     * 1-我的动态
     */
    @POST(UrlConfig.mThreadData)
    Observable<BaseEntity<MThreadDataBean>> mThreadData(@Body MThreadDataJson mThreadDataJson);

    /**
     * 1-删除评论
     */
    @POST(UrlConfig.delEval)
    Observable<BaseEntity<String>> delEval(@Field("post_id") String post_id);

    /**
     * 1-删除动态
     */
    @POST(UrlConfig.delThread)
    Observable<BaseEntity<String>> delThread(@Body DelThreadJson delThreadJson);

    /**
     * 1-选课首页
     */
    @POST(UrlConfig.groupHome)
    Observable<BaseEntity<GroupHomeBean>> groupHome(@Body HomeInfoJson homeInfoJson);

    /**
     * 1-1-题库首页
     */
    @POST(UrlConfig.paperHome)
    Observable<BaseEntity<PaperHomeBean>> paperHome(@Body HomeInfoJson homeInfoJson);

    /**
     * 1-试卷列表
     */
    @POST(UrlConfig.paperData)
    Observable<BaseEntity<PaperDataBean>> paperData(@Body PaperDataJson paperDataJson);

    /**
     * 1-获取问题（添加答题记录）
     */
    @POST(UrlConfig.getQuestion)
    Observable<BaseEntity<GetQuestionBean>> getQuestion(@Body GetQuestionJson getQuestionJson);

    /**
     * 1-获取问题（添加答题记录）
     */
    @POST(UrlConfig.insertRecord)
    Observable<BaseEntity<InsertRecordBean>> insertRecord(@Body InsertRecordJson insertRecordJson);

    /**
     * 1-试卷详情
     */
    @POST(UrlConfig.paperInfo)
    Observable<BaseEntity<PaperInfoBean>> paperInfo(@Body PaperInfoJson paperInfoJson);

    /**
     * 1-结束答题
     */
    @POST(UrlConfig.closePaper)
    Observable<BaseEntity<String>> closePaper(@Body ClosePaperJson closePaperJson);

    /**
     * 1-答题卡
     */
    @POST(UrlConfig.paperCard)
    Observable<BaseEntity<PaperCardBean>> paperCard(@Body PaperCardJson paperCardJson);

    /**
     * 1-问题收藏/取消收藏
     */
    @POST(UrlConfig.favorites)
    Observable<BaseEntity<GetQuestionBean>> favorites(@Body FavoritesJson favoritesJson);

    /**
     * 1-交卷
     */
    @POST(UrlConfig.paperEvaluation)
    Observable<BaseEntity<PaperEvaluationBean>> paperEvaluation(@Body PaperEvaluationJson paperEvaluationJson);

    /**
     * 1-1-科目列表
     */
    @POST(UrlConfig.curriculumData)
    Observable<BaseEntity<List<CurriculumDataBean>>> curriculumData(@Body CurriculumDataJson curriculumDataJson);

    /**
     * 1-解答题评分
     */
    @POST(UrlConfig.answerScore)
    Observable<BaseEntity<GetQuestionBean>> answerScore(@Body AnswerScoreJson answerScoreJson);

    /**
     * 1-收藏/错题列表
     */
    @POST(UrlConfig.questionData)
    Observable<BaseEntity<QuestionDataBean>> questionData(@Body QuestionDataJson questionDataJson);

    /**
     * 1金币列表
     */
    @POST(UrlConfig.virtualCurrencyList)
    Observable<BaseEntity<VirtualCurrencyListBean>> virtualCurrencyList(@Body IntegralDataJson integralDataJson);

    /**
     * 提现列表
     */
    @POST(UrlConfig.withdrawalData)
    Observable<BaseEntity<WithdrawalDataBean>> withdrawalData(@Body IntegralDataJson integralDataJson);

    /**
     * 申请提现
     */
    @POST(UrlConfig.cashWithdrawal)
    Observable<BaseEntity<CashWithdrawalBean>> cashWithdrawal(@Body CashWithdrawalJson cashWithdrawalJson);

    /**
     * 1-用户详情
     */
    @POST(UrlConfig.userInfo)
    Observable<BaseEntity<UserInfoBean>> userInfo(@Body CarInfoJson carInfoJson);

    /**
     * 1-修改用户信息
     */
    @POST(UrlConfig.editUser)
    Observable<BaseEntity<UserInfoBean>> editUser(@Body EditUserJson editUserJson);

    /**
     * 1-用户消息列表
     */
    @POST(UrlConfig.messageData)
    Observable<BaseEntity<MessageDataBean>> messageData(@Body TopicDataJson topicDataJson);

    /**
     * 1-修改消息状态
     */
    @POST(UrlConfig.updateMessageStatus)
    Observable<BaseEntity<String>> updateMessageStatus(@Body UpdateMessageStatusJson updateMessageStatusJson);


    /**
     * 1-课程列表
     */
    @POST(UrlConfig.groupData)
    Observable<BaseEntity<List<GroupInfoBean>>> groupData(@Body GroupDataJson groupDataJson);

    /**
     * 文件上传
     */
    @POST("http://www.jianpei.com.cn/api/uploadFile")
    @Headers({"upfile: 111"})
    @Multipart
    Observable<BaseEntity<List<String>>> uploadFile(@Part List<MultipartBody.Part> imgs);

    /**
     * 1-版本检测
     */
    @POST(UrlConfig.versionDetect)
    Observable<BaseEntity<VersionDetectBean>> versionDetect();

    /**
     * 1-意见反馈
     */
    @POST(UrlConfig.feedback)
    Observable<BaseEntity<String>> feedback(@Body FeedbackJson feedbackJson);


    /**
     * 获取微信ACCESS_TOKEN
     *
     * @return
     */
//    @GET(UrlConfig.WEIXIN_ACCESSTOKEN)
//    Observable<AccessTokenBean> weixinAccessToken(@Query("appid") String appid, @Query("secret") String secret, @Query("code") String code);

    /**
     * 获取用户个人信息（UnionID 机制）
     *
     * @return
     */
//    @GET(UrlConfig.WEIXIN_USERINFO)
//    Observable<WxUserInfoBean> weixinUserInfo(@Query("access_token") String access_token, @Query("openid") String openid);

    /**
     * 微信登陆接口
     *
     * @param loginJson
     * @return
     */
//    @POST(UrlConfig.WX_LOGIN)
//    Observable<BaseEntity<String>> wxLogin();

}
