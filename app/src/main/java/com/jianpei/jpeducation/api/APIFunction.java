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
import com.jianpei.jpeducation.bean.MaterialDataBean;
import com.jianpei.jpeducation.bean.MaterialDataJson;
import com.jianpei.jpeducation.bean.NoticeDataBean;
import com.jianpei.jpeducation.bean.SubMaterialDataJson;
import com.jianpei.jpeducation.bean.classinfo.ClassInfoBean;
import com.jianpei.jpeducation.bean.classinfo.DirectoryProfessionBean;
import com.jianpei.jpeducation.bean.classinfo.DirectorySectionBean;
import com.jianpei.jpeducation.bean.classinfo.GroupClassBean;
import com.jianpei.jpeducation.bean.classinfo.GroupCouponBean;
import com.jianpei.jpeducation.bean.classinfo.ImputedPriceBean;
import com.jianpei.jpeducation.bean.classinfo.RegimentBean;
import com.jianpei.jpeducation.bean.classinfo.RegimentDataBean;
import com.jianpei.jpeducation.bean.classinfo.VideoUrlBean;
import com.jianpei.jpeducation.bean.classinfo.ViodListBean;
import com.jianpei.jpeducation.bean.homedata.HomeDataBean;
import com.jianpei.jpeducation.bean.HomeInfoJson;
import com.jianpei.jpeducation.bean.LauncherBean;
import com.jianpei.jpeducation.bean.LoginJson;
import com.jianpei.jpeducation.bean.SendCodeJson;
import com.jianpei.jpeducation.bean.UserInfoBean;
import com.jianpei.jpeducation.bean.WxLoginJson;
import com.jianpei.jpeducation.bean.homedata.MaterialInfoBean;
import com.jianpei.jpeducation.bean.json.CancelOrderJson;
import com.jianpei.jpeducation.bean.json.CarInfoJson;
import com.jianpei.jpeducation.bean.json.CheckPayStatusJson;
import com.jianpei.jpeducation.bean.json.ClassGenerateOrderJson;
import com.jianpei.jpeducation.bean.json.CommentListJson;
import com.jianpei.jpeducation.bean.json.GroupInfoJson;
import com.jianpei.jpeducation.bean.json.ImputedPriceJson;
import com.jianpei.jpeducation.bean.json.InsertCarJson;
import com.jianpei.jpeducation.bean.json.InsertCommentJson;
import com.jianpei.jpeducation.bean.json.OrderDataJson;
import com.jianpei.jpeducation.bean.json.OrderInfoJson;
import com.jianpei.jpeducation.bean.json.OrderPaymentJson;
import com.jianpei.jpeducation.bean.json.RegimentDataJson;
import com.jianpei.jpeducation.bean.json.RegimentInfoJson;
import com.jianpei.jpeducation.bean.json.RemoveCarJson;
import com.jianpei.jpeducation.bean.json.VideoUrlJson;
import com.jianpei.jpeducation.bean.json.ViodListJson;
import com.jianpei.jpeducation.bean.order.CheckPayStatusBean;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.bean.order.OrderDataBean;
import com.jianpei.jpeducation.bean.order.OrderInfoBean;
import com.jianpei.jpeducation.bean.order.OrderListBean;
import com.jianpei.jpeducation.bean.order.OrderPaymentBean;
import com.jianpei.jpeducation.bean.shop.CarInfoBean;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

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
     * 优惠券列表
     */
    @POST(UrlConfig.couponData)
    Observable<BaseEntity<CouponDataBean>> couponData(@Body CouponDataJson couponDataJson);

    /**
     * 资料下载
     */
    @POST(UrlConfig.getDownloadUrl)
    Observable<BaseEntity<DownloadBean>> getDownloadUrl(@Body DownloadJson downloadJson);

    /**
     * 首页公告
     */
    @POST(UrlConfig.noticeData)
    Observable<BaseEntity<ArrayList<NoticeDataBean>>> noticeData(@Body HomeInfoJson homeInfoJson);

    /**
     * 积分支付
     */
    @POST(UrlConfig.integrlPay)
    Observable<BaseEntity<String>> integrlPay(@Body IntegrlPayJson integrlPayJson);

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
    Observable<BaseEntity<List<GroupCouponBean>>> groupCoupon(@Body HomeInfoJson homeInfoJson);

    /**
     * 课程章节列表
     */
    @POST(UrlConfig.viodList)
    Observable<BaseEntity<List<DirectorySectionBean>>> viodList(@Body ViodListJson viodListJson);

    /**
     * 获取视频播放url
     */
    @POST(UrlConfig.videoUrl)
    Observable<BaseEntity<VideoUrlBean>> videoUrl(@Body VideoUrlJson videoUrlJson);

    /**
     * 购买课程下单/计算价格
     */
    @POST(UrlConfig.classGenerateOrder)
    Observable<BaseEntity<ClassGenerateOrderBean>> classGenerateOrder(@Body ClassGenerateOrderJson classGenerateOrderJson);

    /**
     * 1-订单详情
     */
    @POST(UrlConfig.orderInfo)
    Observable<BaseEntity<OrderInfoBean>> orderInfo(@Body OrderInfoJson orderInfoJson);

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
    Observable<BaseEntity<CheckPayStatusBean>> checkPayStatus(@Body CheckPayStatusJson checkPayStatusJson);

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
    Observable<BaseEntity<ClassGenerateOrderBean>> carInfo(@Body CarInfoJson carInfoJson);


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
