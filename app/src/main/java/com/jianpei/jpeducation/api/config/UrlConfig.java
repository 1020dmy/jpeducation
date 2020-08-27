package com.jianpei.jpeducation.api.config;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/7
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class UrlConfig {


    public static final String LOGIN = "";

//    public static final String WEIXIN_ACCESSTOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code";

//    public static final String WEIXIN_USERINFO = "https://api.weixin.qq.com/sns/userinfo?";

//    public static final String WX_LOGIN = "";


    public static final String appset = "appSet";
    //获取验证码
    public static final String sendCode = "send/sendCode";
    //1-用户验证码注册/登录
    public static final String codeLogin = "user/userLogin";
    //忘记密码
    public static final String forgetPwd = "user/editPwd";
    //获取课程目录
    public static final String courseData = "course/courseData";

    //首页配置参数
    public static final String homeInfo = "homeInfo";

    //微信登陆
    public static final String wxLogin = "user/weChatAuth";
    //绑定手机号
    public static final String bindPhone = "user/phoneBinding";
    //退出登陆
    public static final String loginOut = "user/userOutLogin";
    //领取优惠券
    public static final String couponReceive = "coupon/couponReceive";
    //我的优惠券列表
    public static final String couponData = "coupon/couponData";
    //资料下载
    public static final String getDownloadUrl = "material/getDownloadUrl";
    //首页公告
    public static final String noticeData = "noticeData";

    //资料一级列表
    public static final String materialData = "material/materialData";
    //资料二级列表
    public static final String subMaterialData = "material/subMaterialData";
    //班级课程详情（含团购）
    public static final String groupInfo = "group/groupInfo";
    //班级课程目录
    public static final String classDirectory = "group/classDirectory";
    //课程列表
    public static final String groupData = "group/groupData";
    //评价列表
    public static final String commentList = "comment/commentList";
    // 1-添加评价
    public static final String insertComment = "comment/insertComment";

    //购买选择课程科目（含团购）
    public static final String groupClass = "group/groupClass";
    //课程优惠券列表
    public static final String groupCoupon = "coupon/groupCoupon";
    //课程章节列表
    public static final String viodList = "course/viodList";
    //获取视频播放url
    public static final String videoUrl = "video/videoUrl";

    //1-购买课程下单/计算价格
    public static final String classGenerateOrder = "order/classGenerateOrder";
    //订单发起支付
    public static final String orderPayment = "order/orderPayment";

    //1-订单详情
    public static final String orderInfo = "order/orderInfo";
    //1-计算价格
    public static final String imputedPrice = "order/imputedPrice";

    //1-参与拼团详情
    public static final String regimentInfo = "group/regimentInfo";

    //1-团列表
    public static final String regimentData = "group/regimentData";

    //1-支付状态查询
    public static final String checkPayStatus = "order/checkPayStatus";

    //1-添加购物车
    public static final String insertCar = "car/insertCar";

    //1-从购物车删除
    public static final String removeCar = "car/removeCar";

    //    1-购物车详情
    public static final String carInfo = "car/carInfo";

    // 1-订单列表
    public static final String orderData = "order/orderData";

    // 1-取消订单
    public static final String cancelOrder = "order/cancelOrder";

    // 1-积分首页
    public static final String integralInfo = "integral/integralInfo";
    //    1-积分任务列表
    public static final String integralTask = "integral/integralTask";
    //积分购买
    public static final String integrlPay = "integral/integralPay";
    //1-积分列表
    public static final String integralData = "integral/integralData";

    //1-我的资料
    public static final String myMaterialData = "material/myMaterialData";
    //1-我的课程列表
    public static final String classData = "course/classData";

    //1-1-我的课程详情
    public static final String classInfo = "course/classInfo";
    //1-更新看课记录
    public static final String updateSchedule = "video/updateSchedule";


    //1-话题列表
    public static final String topicData = "garden/topicData";
    //1-1-我的关注用户列表
    public static final String attentionData = "garden/attentionData";
    //1-发布动态
    public static final String insertGarden = "garden/insertGarden";

    //1-广场帖子列表
    public static final String threadData = "garden/threadData";

    //1-1-关注/取消关注
    public static final String attention = "garden/attention";

    //1-点赞/取消点赞
    public static final String gardenPraise = "garden/gardenPraise";

    //1-动态详情
    public static final String threadInfo = "garden/threadInfo";

    //1-动态评价列表
    public static final String evaluationData = "garden/evaluationData";
    //1-回复列表
    public static final String replyData = "garden/replyData";
    //1-添加评论
    public static final String insertEvaluation = "garden/insertEvaluation";
    //1-我的动态
    public static final String mThreadData = "garden/mThreadData";
    //1-删除评论
    public static final String delEval = "garden/delEval";

    //1-1-删除动态
    public static final String delThread = "garden/delThread";


    //1-选课首页
    public static final String groupHome = "group/groupHome";

    //1-1-题库首页
    public static final String paperHome = "paperHome";

    //1-试卷列表
    public static final String paperData = "paper/paperData";

    //1-获取问题（添加答题记录）
    public static final String getQuestion = "paper/getQuestion";

    //1-添加答题记录（答题）
    public static final String insertRecord = "paper/insertRecord";

    //1-试卷详情
    public static final String paperInfo = "paper/paperInfo";

    //1-1-结束答题
    public static final String closePaper = "paper/closePaper";

    //1-答题卡
    public static final String paperCard = "paper/paperCard";

    //1-问题收藏/取消收藏
    public static final String favorites = "paper/favorites";

    //1-交卷
    public static final String paperEvaluation = "paper/paperEvaluation";

    //1-1-科目列表
    public static final String curriculumData = "paper/curriculumData";
    //1-解答题评分
    public static final String answerScore = "paper/answerScore";

    //1-收藏/错题列表
    public static final String questionData = "paper/questionData";

    //1-金币列表
    public static final String virtualCurrencyList = "user/virtualCurrencyList";

    //1-提现列表
    public static final String withdrawalData = "user/withdrawalData";

    //1-申请提现
    public static final String cashWithdrawal = "user/cashWithdrawal";

    //1-用户详情
    public static final String userInfo = "user/userInfo";
    //1-1-修改用户信息
    public static final String editUser = "user/editUser";

    //1-用户消息列表
    public static final String messageData = "msg/messageData";

    //1-1-修改消息状态
    public static final String updateMessageStatus = "msg/updateMessageStatus";


    //1-版本检测
    public static final String versionDetect = "versionDetect";
    //1-文件上传
    public static final String uploadFile = "uploadFile";

    //1-意见反馈
    public static final String feedback = "Feedback";
}
