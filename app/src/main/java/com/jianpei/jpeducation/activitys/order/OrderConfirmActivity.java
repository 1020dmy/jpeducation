package com.jianpei.jpeducation.activitys.order;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.Constants;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.mine.UserCouponActivity;
import com.jianpei.jpeducation.activitys.web.KeFuActivity;
import com.jianpei.jpeducation.adapter.coupon.CouponAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.CouponDataBean;
import com.jianpei.jpeducation.bean.order.CheckPayStatusBean;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.bean.order.GroupInfoBean;
import com.jianpei.jpeducation.bean.order.OrderInfoBean;
import com.jianpei.jpeducation.bean.order.OrderPaymentBean;
import com.jianpei.jpeducation.bean.order.WxInfo;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.pop.MyCouponPopup;
import com.jianpei.jpeducation.viewmodel.OrderConfirmModel;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderConfirmActivity extends BaseActivity {


    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_orderNum)
    TextView tvOrderNum;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_class_title)
    TextView tvClassTitle;
    @BindView(R.id.tv_classNames)
    TextView tvClassNames;
    @BindView(R.id.tv_material)
    TextView tvMaterial;
    @BindView(R.id.tv_material_name)
    TextView tvMaterialName;
    @BindView(R.id.tv_origin_price)
    TextView tvOriginPrice;
    @BindView(R.id.tv_discount_price)
    TextView tvDiscountPrice;
    @BindView(R.id.tv_real_price)
    TextView tvRealPrice;
    @BindView(R.id.iv_weixin)
    ImageView ivWeixin;
    @BindView(R.id.ll_weixin_pay)
    LinearLayout llWeixinPay;
    @BindView(R.id.iv_zhifubao)
    ImageView ivZhifubao;
    @BindView(R.id.ll_zhifubao_pay)
    LinearLayout llZhifubaoPay;
    @BindView(R.id.tv_pay_price)
    TextView tvPayPrice;
    @BindView(R.id.submit)
    TextView submit;

    @BindView(R.id.line)
    TextView tvLine;

    int payType = 1;
    @BindView(R.id.iv_quan)
    ImageView ivQuan;
    @BindView(R.id.tv_quan)
    TextView tvQuan;
    @BindView(R.id.ll_quan)
    LinearLayout llQuan;


//    private MyCouponPopup couponPopup;


    private ClassGenerateOrderBean mClassGenerateOrderBean;
//    private String type;

    private OrderConfirmModel orderConfirmModel;
//    private ArrayList<CouponDataBean.CouponData> mCouponDatas;

    private String mCouponTitle;

    private IWXAPI msgApi;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_order_confirm;
    }

    @Override
    protected void initView() {
        Constants.orderType = "0";
        msgApi = WXAPIFactory.createWXAPI(this, null);

        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.orderconfirm_kefu);
        tvTitle.setText("订单确认");

        llWeixinPay.setEnabled(false);
        ivWeixin.setEnabled(false);
        mClassGenerateOrderBean = getIntent().getParcelableExtra("classGenerateOrderBean");
//        type = getIntent().getStringExtra("type");

        orderConfirmModel = new ViewModelProvider(this).get(OrderConfirmModel.class);

        orderConfirmModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });
//        //优惠券
//        orderConfirmModel.getCouponDataBeanLiveData().observe(this, new Observer<CouponDataBean>() {
//            @Override
//            public void onChanged(CouponDataBean couponDataBean) {
//                dismissLoading();
//                if (mCouponDatas == null)
//                    mCouponDatas = new ArrayList<>();
//                mCouponDatas.addAll(couponDataBean.getData());
//
//                showPop();
//            }
//        });
        //选择优惠券后发起订单
        orderConfirmModel.getClassGenerateOrderBeanLiveData().observe(this, new Observer<ClassGenerateOrderBean>() {
            @Override
            public void onChanged(ClassGenerateOrderBean classGenerateOrderBean) {
                dismissLoading();
//                if (couponPopup != null) {
//                    couponPopup.dismiss();
//                }
                mClassGenerateOrderBean = classGenerateOrderBean;
                setData();
            }
        });
        //发起支付
        orderConfirmModel.getOrderPaymentBeanLiveData().observe(this, new Observer<OrderPaymentBean>() {
            @Override
            public void onChanged(OrderPaymentBean orderPaymentBean) {
                dismissLoading();
                if (payType == 1) {//微信支付
                    WxInfo wxInfo = orderPaymentBean.getWx_info();
                    if (wxInfo == null) {
                        shortToast("获取订单信息失败！");
                        return;
                    }
                    PayReq request = new PayReq();
                    request.appId = wxInfo.getAppid();
                    request.partnerId = wxInfo.getPartnerid();
                    request.prepayId = wxInfo.getPrepayid();
                    request.packageValue = "Sign=WXPay";
                    request.nonceStr = wxInfo.getNoncestr();
                    request.timeStamp = String.valueOf(wxInfo.getTimestamp());
                    request.sign = wxInfo.getSign();
                    msgApi.sendReq(request);
                } else {//支付宝支付
                    orderConfirmModel.aliPay(orderPaymentBean.getAli_info(), new PayTask(OrderConfirmActivity.this));

                }
            }
        });
        //支付宝支付返回的结果
        orderConfirmModel.getAliPayLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (TextUtils.equals(s, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    showLoading("");
                    orderConfirmModel.checkPayStatus(mClassGenerateOrderBean.getOrder_info().getId(), payType + "");
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    shortToast("支付失败！" + s);
                }
            }
        });
        //支付结果查询
        orderConfirmModel.getCheckPayStatusBeanLiveData().observe(this, new Observer<CheckPayStatusBean>() {
            @Override
            public void onChanged(CheckPayStatusBean checkPayStatusBean) {
                if ("0".equals(checkPayStatusBean.getState())) {//未支付
                    orderConfirmModel.checkPayStatus(mClassGenerateOrderBean.getOrder_info().getId(), payType + "");
                } else {
                    dismissLoading();
                    if ("2".equals(mClassGenerateOrderBean.getOrder_info().getGoods_type())) {
                        startActivity(new Intent(OrderConfirmActivity.this, GroupResultActivity.class).putExtra("state", checkPayStatusBean.getState()));

                    } else {
                        startActivity(new Intent(OrderConfirmActivity.this, OrderResultActivity.class).putExtra("state", checkPayStatusBean.getState()));

                    }
                }

            }
        });


    }

    @Override
    protected void onNewIntent(Intent intent) {
        int resultCode = intent.getIntExtra("resultCode", -10);
        if (resultCode == 0) {
            showLoading("");
            orderConfirmModel.checkPayStatus(mClassGenerateOrderBean.getOrder_info().getId(), payType + "");
        } else if (resultCode == -1) {
            shortToast("支付失败！");
        } else if (resultCode == -2) {
            shortToast("取消支付！");
        } else {
            shortToast("未知错误！");
        }
        super.onNewIntent(intent);

    }

    @Override
    protected void initData() {
        setData();
    }


    protected void setData() {

        if (mClassGenerateOrderBean == null)
            return;
        GroupInfoBean groupInfoBean = mClassGenerateOrderBean.getGroup_info();
        OrderInfoBean orderInfoBean = mClassGenerateOrderBean.getOrder_info();
        //用户信息
//        tvName.setText(orderInfoBean.getName());
//        tvPhone.setText(orderInfoBean.getTel());


        //订单信息
        tvOrderNum.setText(orderInfoBean.getNumber());
        tvTime.setText(orderInfoBean.getAdd_time_str());
        Glide.with(this).load(groupInfoBean.getImg()).into(ivIcon);
        tvClassTitle.setText(groupInfoBean.getTitle());
        tvClassNames.setText(orderInfoBean.getClass_name_str());
        if ("1".equals(orderInfoBean.getIs_material())) {
            tvMaterialName.setText(orderInfoBean.getMaterial_des());
        } else {
            tvMaterial.setVisibility(View.GONE);
            tvMaterialName.setVisibility(View.GONE);
            tvLine.setVisibility(View.GONE);
        }
        if ("2".equals(orderInfoBean.getGoods_type())) {
            llQuan.setVisibility(View.GONE);
        }
        //优惠券信息
        if ("1".equals(orderInfoBean.getIs_user_coupon())) {
            ivQuan.setImageResource(R.drawable.icon_quanou_is);
            tvQuan.setText("有可用优惠券");
            llQuan.setEnabled(true);
        }
        mCouponTitle = orderInfoBean.getCoupon_type_str();
        if (!TextUtils.isEmpty(mCouponTitle))
            tvQuan.setText(mCouponTitle);

        //价格信息
        tvOriginPrice.setText("￥" + orderInfoBean.getCount_integral());
        tvDiscountPrice.setText("￥" + orderInfoBean.getDiscount_integral());
        tvRealPrice.setText("￥" + orderInfoBean.getMoney());
        //
        tvPayPrice.setText("￥" + orderInfoBean.getMoney());
        //


    }

    @OnClick({R.id.iv_back, R.id.iv_right, R.id.ll_weixin_pay, R.id.ll_zhifubao_pay, R.id.submit, R.id.ll_quan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_right:
                startActivity(new Intent(this, KeFuActivity.class));
                break;
            case R.id.ll_weixin_pay:
                changeStatus(1);
                break;
            case R.id.ll_zhifubao_pay:
                changeStatus(2);
                break;
            case R.id.submit:
                showLoading("");
                orderConfirmModel.orderPayment(payType + "", mClassGenerateOrderBean.getOrder_info().getId());
                break;
            case R.id.ll_quan:
//                if (mCouponDatas == null || mCouponDatas.size() == 0) {
//                    orderConfirmModel.couponData(1, 10, 1);
//                } else {
//                    showPop();
//                }
                startActivityForResult(new Intent(this, UserCouponActivity.class).putExtra("formActivity", 0), 101);

                break;
        }
    }

    /**
     * 选择优惠券后返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 101 && data != null) {
            showLoading("");
            mCouponTitle = data.getStringExtra("couponTitle");
            String couponId = data.getStringExtra("couponId");
            orderConfirmModel.classGenerateOrder("1", mClassGenerateOrderBean.getGroup_info().getId(), couponId, mClassGenerateOrderBean.getOrder_info().getId());

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void changeStatus(int type) {
        payType = type;
        if (type == 1) {
            llWeixinPay.setEnabled(false);
            ivWeixin.setEnabled(false);
            llZhifubaoPay.setEnabled(true);
            ivZhifubao.setEnabled(true);
        } else {
            llWeixinPay.setEnabled(true);
            ivWeixin.setEnabled(true);
            llZhifubaoPay.setEnabled(false);
            ivZhifubao.setEnabled(false);
        }

    }

//    protected void showPop() {
//        if (couponPopup == null) {
//            couponPopup = new MyCouponPopup(this, mCouponDatas);
//            couponPopup.setMyCouponReceiveListener(new CouponAdapter.MyCouponReceiveListener() {
//                @Override
//                public void onClickCouponReceive(String couponId, String couponTitle) {
//                    showLoading("");
//                    mCouponTitle = couponTitle;
//                    orderConfirmModel.classGenerateOrder("1", mClassGenerateOrderBean.getGroup_info().getId(), couponId, mClassGenerateOrderBean.getOrder_info().getId());
//                }
//            });
//        }
//        couponPopup.showPop();
//    }

}
