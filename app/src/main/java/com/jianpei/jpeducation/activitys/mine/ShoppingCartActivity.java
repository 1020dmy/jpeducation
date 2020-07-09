package com.jianpei.jpeducation.activitys.mine;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
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
import com.jianpei.jpeducation.activitys.order.OrderResultActivity;
import com.jianpei.jpeducation.activitys.web.KeFuActivity;
import com.jianpei.jpeducation.adapter.ShoppingCatAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.order.CheckPayStatusBean;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.bean.order.OrderInfoBean;
import com.jianpei.jpeducation.bean.order.OrderPaymentBean;
import com.jianpei.jpeducation.bean.order.WxInfo;
import com.jianpei.jpeducation.bean.shop.GroupBean;
import com.jianpei.jpeducation.viewmodel.OrderConfirmModel;
import com.jianpei.jpeducation.viewmodel.ShoppingCartModel;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShoppingCartActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_orderNum)
    TextView tvOrderNum;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
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
    @BindView(R.id.tv_nodata)
    TextView tvNodata;

    int payType = 1;


    private ShoppingCatAdapter shoppingCatAdapter;
    private List<GroupBean> groupBeans;

//    private ArrayList<CouponDataBean.CouponData> mCouponDatas;

    private ShoppingCartModel shoppingCartModel;
    private OrderConfirmModel orderConfirmModel;


    //    private MyCouponPopup couponPopup;
    private String mCouponTitle;
    private ClassGenerateOrderBean mClassGenerateOrderBean;
    private IWXAPI msgApi;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_shopping_cart;
    }

    @Override
    protected void initView() {
        Constants.orderType = "1";

        msgApi = WXAPIFactory.createWXAPI(this, null);

        shoppingCartModel = new ViewModelProvider(this).get(ShoppingCartModel.class);
        orderConfirmModel = new ViewModelProvider(this).get(OrderConfirmModel.class);

        tvTitle.setText("购物车");
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.orderconfirm_kefu);
        llWeixinPay.setEnabled(false);
        ivWeixin.setEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void initData() {
        shoppingCartModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });
        //购物车信息
        shoppingCartModel.getCarInfoBeanLiveData().observe(this, new Observer<ClassGenerateOrderBean>() {
            @Override
            public void onChanged(ClassGenerateOrderBean carInfoBean) {
                dismissLoading();
                setData(carInfoBean);

            }
        });
        shoppingCartModel.getRemoveCarLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                dismissLoading();
//                shortToast(s);
                shoppingCartModel.carInfo();
            }
        });
//        //优惠券列表
//        shoppingCartModel.getCouponDataBeanLiveData().observe(this, new Observer<CouponDataBean>() {
//            @Override
//            public void onChanged(CouponDataBean couponDataBean) {
//                dismissLoading();
//                if (mCouponDatas == null)
//                    mCouponDatas = new ArrayList<>();
//                mCouponDatas.addAll(couponDataBean.getData());
//                showPop();
//            }
//        });
        //订单支付相关
        orderConfirmModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });
        //选择优惠券后
        orderConfirmModel.getClassGenerateOrderBeanLiveData().observe(this, new Observer<ClassGenerateOrderBean>() {
            @Override
            public void onChanged(ClassGenerateOrderBean classGenerateOrderBean) {
                dismissLoading();
//                if (couponPopup != null) {
//                    couponPopup.dismiss();
//                }
                mClassGenerateOrderBean = classGenerateOrderBean;
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
                    orderConfirmModel.aliPay(orderPaymentBean.getAli_info(), new PayTask(ShoppingCartActivity.this));

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
                    startActivity(new Intent(ShoppingCartActivity.this, OrderResultActivity.class).putExtra("state", checkPayStatusBean.getState()));


                }

            }
        });

        groupBeans = new ArrayList<>();
        shoppingCatAdapter = new ShoppingCatAdapter(groupBeans, this);

        shoppingCatAdapter.setMyItemDeleteListener(new ShoppingCatAdapter.MyItemDeleteListener() {
            @Override
            public void onDeleteClick(int position, String carId) {
                showLoading("");
                shoppingCartModel.removeCar(carId, mClassGenerateOrderBean.getOrder_info().getId());

            }
        });

        recyclerView.setAdapter(shoppingCatAdapter);

        //获取购物车详情
        showLoading("");
        shoppingCartModel.carInfo();
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


    @OnClick({R.id.iv_back, R.id.iv_right, R.id.ll_quan, R.id.ll_weixin_pay, R.id.ll_zhifubao_pay, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_right:
                startActivity(new Intent(this, KeFuActivity.class));
                break;
            case R.id.ll_quan:
//                if (mCouponDatas == null || mCouponDatas.size() == 0) {
//                    shoppingCartModel.couponData(1, 10, 1);
//                } else {
//                    showPop();
//                }
                startActivityForResult(new Intent(this, UserCouponActivity.class).putExtra("formActivity", 0), 101);
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
        }
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
            orderConfirmModel.classGenerateOrder("1", mClassGenerateOrderBean.getOrder_info().getGroup_id(), couponId, mClassGenerateOrderBean.getOrder_info().getId());

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setData(ClassGenerateOrderBean carInfoBean) {
        if (carInfoBean == null) {
            tvNodata.setVisibility(View.VISIBLE);
            return;
        }
        tvNodata.setVisibility(View.GONE);

        mClassGenerateOrderBean = carInfoBean;
        OrderInfoBean orderInfoBean = carInfoBean.getOrder_info();
        tvOrderNum.setText(orderInfoBean.getNumber());
        tvTime.setText(orderInfoBean.getAdd_time_str());
        //课程信息
        groupBeans.clear();
        groupBeans.addAll(carInfoBean.getGroup_list());
        //
        //优惠券信息
        if ("1".equals(orderInfoBean.getIs_user_coupon())) {
            ivQuan.setImageResource(R.drawable.icon_quanou_is);
            tvQuan.setText("有可用优惠券");
            llQuan.setEnabled(true);
        }
        if (!TextUtils.isEmpty(mCouponTitle))
            tvQuan.setText(mCouponTitle);
        //价格信息
        tvOriginPrice.setText("￥" + orderInfoBean.getCount_integral());
        tvDiscountPrice.setText("￥" + orderInfoBean.getDiscount_integral());
        tvRealPrice.setText("￥" + orderInfoBean.getMoney());
        //
        tvPayPrice.setText("￥" + orderInfoBean.getMoney());

        shoppingCatAdapter.notifyDataSetChanged();


    }

//    protected void showPop() {
//        if (couponPopup == null) {
//            couponPopup = new MyCouponPopup(this, mCouponDatas);
//            couponPopup.setMyCouponReceiveListener(new CouponAdapter.MyCouponReceiveListener() {
//                @Override
//                public void onClickCouponReceive(String couponId, String couponTitle) {
//                    showLoading("");
//                    mCouponTitle = couponTitle;
//                    orderConfirmModel.classGenerateOrder("1", mClassGenerateOrderBean.getOrder_info().getGroup_id(), couponId, mClassGenerateOrderBean.getOrder_info().getId());
//                }
//            });
//        }
//        couponPopup.showPop();
//    }
}
