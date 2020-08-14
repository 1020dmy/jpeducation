package com.jianpei.jpeducation.activitys.order;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.PayTask;
import com.jianpei.jpeducation.Constants;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.mine.UserCouponActivity;
import com.jianpei.jpeducation.activitys.web.KeFuActivity;
import com.jianpei.jpeducation.adapter.GroupInfoAdapter;
import com.jianpei.jpeducation.adapter.order.OrderInfoAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.classinfo.RegimentBean;
import com.jianpei.jpeducation.bean.order.MIneOrderInfoBean;
import com.jianpei.jpeducation.bean.order.OrderPaymentBean;
import com.jianpei.jpeducation.bean.order.WxInfo;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.viewmodel.OrderConfirmModel;
import com.jianpei.jpeducation.viewmodel.OrderListModel;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderInfoActivity extends BaseActivity {


    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_group_statue)
    TextView tvGroupStatue;
    //    @BindView(R.id.civ_head)
//    CircleImageView civHead;
//    @BindView(R.id.tv_leader)
//    TextView tvLeader;
//    @BindView(R.id.civ_item)
//    CircleImageView civItem;
    //拼团信息
    @BindView(R.id.rv_group)
    RecyclerView rv_group;
    //
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.ll_groupInfo)
    LinearLayout llGroupInfo;
    @BindView(R.id.tv_orderNum)
    TextView tvOrderNum;
    @BindView(R.id.tv_time)
    TextView tvTime;
    //    @BindView(R.id.iv_icon)
//    ImageView ivIcon;
//    @BindView(R.id.tv_class_title)
//    TextView tvClassTitle;
//    @BindView(R.id.tv_classNames)
//    TextView tvClassNames;
//    @BindView(R.id.tv_material)
//    TextView tvMaterial;
//    @BindView(R.id.tv_material_name)
//    TextView tvMaterialName;
    @BindView(R.id.line)
    TextView line;
    @BindView(R.id.iv_quan)
    ImageView ivQuan;
    @BindView(R.id.tv_quan)
    TextView tvQuan;
    @BindView(R.id.ll_quan)
    LinearLayout llQuan;
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
    @BindView(R.id.ll_pay_type)
    LinearLayout llPayType;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

//    private OrderInfoAdapter orderInfoAdapter;

    private OrderListModel orderListModel;
    private OrderConfirmModel orderConfirmModel;


//    private OrderDataBean orderDataBean;

    private String orderId;

    int payType = 1;

    private IWXAPI msgApi;

    private String mCouponTitle;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_order_info;
    }

    @Override
    protected void initView() {
        Constants.orderType = "2";
        tvTitle.setText("订单详情");
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.orderconfirm_kefu);
        msgApi = WXAPIFactory.createWXAPI(this, null);


//        orderDataBean = getIntent().getParcelableExtra("orderDataBean");
        orderId = getIntent().getStringExtra("orderId");


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderListModel = new ViewModelProvider(this).get(OrderListModel.class);
        orderConfirmModel = new ViewModelProvider(this).get(OrderConfirmModel.class);


    }

    @Override
    protected void initData() {
        orderListModel.getOrderInfoBeanMutableLiveData().observe(this, new Observer<MIneOrderInfoBean>() {
            @Override
            public void onChanged(MIneOrderInfoBean orderInfoBean) {
                dismissLoading();
                setData(orderInfoBean);


            }
        });
        orderListModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast("");
            }
        });
        orderConfirmModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });
        //选择优惠券后发起订单
        orderConfirmModel.getClassGenerateOrderBeanLiveData().observe(this, new Observer<MIneOrderInfoBean>() {
            @Override
            public void onChanged(MIneOrderInfoBean classGenerateOrderBean) {
                dismissLoading();
                setData(classGenerateOrderBean);
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
                    orderConfirmModel.aliPay(orderPaymentBean.getAli_info(), new PayTask(OrderInfoActivity.this));

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
                    orderConfirmModel.checkPayStatus(orderId, payType + "");
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    shortToast("支付失败！" + s);
                }
            }
        });
        //支付结果查询
        orderConfirmModel.getCheckPayStatusLiveData().observe(this, new Observer<MIneOrderInfoBean>() {
            @Override
            public void onChanged(MIneOrderInfoBean checkPayStatusBean) {
                if ("0".equals(checkPayStatusBean.getState())) {//未支付
                    orderConfirmModel.checkPayStatus(orderId, payType + "");
                } else {
                    dismissLoading();
                    if ("2".equals(mOrderInfoBean.getGoods_type())) {
                        startActivity(new Intent(OrderInfoActivity.this, GroupResultActivity.class)
                                .putExtra("classGenerateOrderBean", checkPayStatusBean)
                                .putExtra("state", checkPayStatusBean.getState()));
                        Constants.orderIspay = 1;
                        finish();
                    } else {
                        startActivity(new Intent(OrderInfoActivity.this, OrderResultActivity.class)
                                .putExtra("orderId", checkPayStatusBean.getId())
                                .putExtra("state", checkPayStatusBean.getState()));
                        Constants.orderIspay = 1;
                        finish();
                    }
                }

            }
        });
        showLoading("");
        orderListModel.orderInfo(orderId);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        int resultCode = intent.getIntExtra("resultCode", -10);
        if (resultCode == 0) {
            showLoading("");
            orderConfirmModel.checkPayStatus(orderId, payType + "");
        } else if (resultCode == -1) {
            shortToast("支付失败！");
        } else if (resultCode == -2) {
            shortToast("取消支付！");
        } else {
            shortToast("未知错误！");
        }
        super.onNewIntent(intent);

    }


    @OnClick({R.id.iv_back, R.id.iv_right, R.id.button, R.id.ll_weixin_pay, R.id.ll_zhifubao_pay, R.id.submit, R.id.ll_quan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_right:
                startActivity(new Intent(this, KeFuActivity.class));
                break;
            case R.id.button://分享
                if (mShareAction == null) {
                    initShare();
                }
                mShareAction.open();
                break;
            case R.id.ll_weixin_pay:
                changeStatus(1);
                break;
            case R.id.ll_zhifubao_pay:
                changeStatus(2);
                break;
            case R.id.submit:
                //发起支付
                showLoading("");
                orderConfirmModel.orderPayment(payType + "", orderId);
                break;
            case R.id.ll_quan://优惠券
//                startActivityForResult(new Intent(this, UserCouponActivity.class).putExtra("formActivity", 0), 101);
                startActivityForResult(new Intent(this, UserCouponActivity.class)
                        .putExtra("formActivity", 0)
                        .putExtra("cat_id", SpUtils.getValue(SpUtils.catId))
                        .putExtra("group_id", group_id), 101);
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
            orderConfirmModel.classGenerateOrder("1", mOrderInfoBean.getGroup_info().getId(), couponId, orderId);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private MIneOrderInfoBean mOrderInfoBean;


    private String group_id;

    public void setData(MIneOrderInfoBean orderInfoBean) {

        if (orderInfoBean == null) {
            return;
        }
        mOrderInfoBean = orderInfoBean;
        group_id = orderInfoBean.getGroup_id();
        //是否需要显示支付
        if ("0".equals(orderInfoBean.getState())) {//未支付的订单
            llBottom.setVisibility(View.VISIBLE);
            llWeixinPay.setEnabled(false);
            ivWeixin.setEnabled(false);
            tvPayPrice.setText("￥" + orderInfoBean.getMoney());
        } else {//取消的订单/已经完成的订单
            llBottom.setVisibility(View.GONE);
            llPayType.setVisibility(View.GONE);
            llQuan.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }
        //如果是拼团订单则不显示优惠券
        if ("2".equals(orderInfoBean.getGoods_type())) {
            llQuan.setVisibility(View.GONE);

            if ("1".equals(orderInfoBean.getState())) {
                tvGroupStatue.setText("支付成功");
            } else {
                tvGroupStatue.setText("未支付");
            }
            if ("2".equals(orderInfoBean.getIs_reg_succ())) {
                tvGroupStatue.setText("拼团成功");
            }
            if ("1".equals(orderInfoBean.getState()) && "1".equals(orderInfoBean.getIs_reg_succ())) {
                button.setVisibility(View.VISIBLE);
            } else {
                button.setVisibility(View.GONE);
            }


            RegimentBean regimentBean = orderInfoBean.getRegiment_info();
            llGroupInfo.setVisibility(View.VISIBLE);//显示拼团信息
            rv_group.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            GroupInfoAdapter myAdapter = new GroupInfoAdapter(regimentBean, this);
            rv_group.setAdapter(myAdapter);
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
        //课程
        OrderInfoAdapter orderInfoAdapter;
        if (orderInfoBean.getGroup_list() == null || orderInfoBean.getGroup_list().size() == 0) {
            orderInfoAdapter = new OrderInfoAdapter(this, orderInfoBean.getGroup_info(), orderInfoBean.getClass_name_str(), orderInfoBean.getCount_integral(), mOrderInfoBean.getIs_material(), mOrderInfoBean.getMaterial_des());
        } else {
            orderInfoAdapter = new OrderInfoAdapter(orderInfoBean.getGroup_list(), this);
        }
        recyclerView.setAdapter(orderInfoAdapter);

        //订单
        tvOrderNum.setText(orderInfoBean.getNumber());
        tvTime.setText(orderInfoBean.getAdd_time_str());
        //价格
        tvOriginPrice.setText("￥" + orderInfoBean.getCount_integral());
        tvDiscountPrice.setText("- ￥" + orderInfoBean.getDiscount_integral());
        tvRealPrice.setText("￥" + orderInfoBean.getMoney());
    }


//    public void setData() {
//        if (orderDataBean == null) {
//            return;
//        }
//        if ("0".equals(orderDataBean.getState()) || "2".equals(orderDataBean.getState())) {//未支付//支付失败
//            llWeixinPay.setEnabled(false);
//            ivWeixin.setEnabled(false);
//
//        } else if ("1".equals(orderDataBean.getState())) {//已支付
//            llBottom.setVisibility(View.GONE);
//            llPayType.setVisibility(View.GONE);
//            llQuan.setVisibility(View.GONE);
//            line.setVisibility(View.GONE);
//        }
//
////        //优惠券信息
////        if ("1".equals(orderDataBean.getIs_user_coupon())) {
////            ivQuan.setImageResource(R.drawable.icon_quanou_is);
////            tvQuan.setText("有可用优惠券");
////            llQuan.setEnabled(true);
////        }
////
////        if (!TextUtils.isEmpty(mCouponTitle))
////            tvQuan.setText(mCouponTitle);
//        //课程
//        if (orderDataBean.getGroup_list() == null || orderDataBean.getGroup_list().size() == 0) {
//            orderInfoAdapter = new OrderInfoAdapter(this, orderDataBean.getGroup_info(), orderDataBean.getClass_name_str(), orderDataBean.getCount_integral(), orderDataBean.getIs_material(), orderDataBean.getMaterial_des());
//        } else {
//            orderInfoAdapter = new OrderInfoAdapter(orderDataBean.getGroup_list(), this);
//        }
//        recyclerView.setAdapter(orderInfoAdapter);
//        //订单
//        tvOrderNum.setText(orderDataBean.getNumber());
//        tvTime.setText(orderDataBean.getAdd_time_str());
//        //价格
//        tvOriginPrice.setText("￥" + orderDataBean.getCount_integral());
//        tvDiscountPrice.setText("￥" + orderDataBean.getDiscount_integral());
//        tvRealPrice.setText("￥" + orderDataBean.getMoney());
//        //
//        tvPayPrice.setText("￥" + orderDataBean.getMoney());
//    }

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

}
