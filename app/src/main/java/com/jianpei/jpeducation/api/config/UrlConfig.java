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
    //优惠券列表
    public static final String couponData = "coupon/couponData";
    //资料下载
    public static final String getDownloadUrl = "material/getDownloadUrl";
    //首页公告
    public static final String noticeData = "noticeData";
    //积分购买
    public static final String integrlPay = "user/integrlPay";
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
    //购买选择课程科目（含团购）
    public static final String groupClass = "group/groupClass";
    //课程优惠券列表
    public static final String groupCoupon = "coupon/groupCoupon";
    //课程章节列表
    public static final String viodList = "course/viodList";
    //获取视频播放url
    public static final String videoUrl = "video/videoUrl";



}
