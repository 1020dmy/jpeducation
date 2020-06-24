package com.jianpei.jpeducation.activitys.order;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.web.KeFuActivity;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.bean.order.GroupInfoBean;
import com.jianpei.jpeducation.bean.order.OrderInfoBean;

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

    int payType = 1;


    private ClassGenerateOrderBean classGenerateOrderBean;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_order_confirm;
    }

    @Override
    protected void initView() {
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.orderconfirm_kefu);
        tvTitle.setText("订单确认");

        llWeixinPay.setEnabled(false);
        ivWeixin.setEnabled(false);

        classGenerateOrderBean = getIntent().getParcelableExtra("classGenerateOrderBean");


    }

    @Override
    protected void initData() {
        setData();
    }


    protected void setData() {

        if (classGenerateOrderBean == null)
            return;
        GroupInfoBean groupInfoBean = classGenerateOrderBean.getGroup_info();
        OrderInfoBean orderInfoBean = classGenerateOrderBean.getOrder_info();
        //用户信息
        tvName.setText(orderInfoBean.getName());
        tvPhone.setText(orderInfoBean.getTel());
        //订单信息
        tvOrderNum.setText(orderInfoBean.getNumber());
        tvTime.setText(orderInfoBean.getUpdtime());
        Glide.with(this).load(groupInfoBean.getImg()).into(ivIcon);
        tvClassTitle.setText(groupInfoBean.getTitle());
        tvClassNames.setText(orderInfoBean.getClass_name_str());
        if ("1".equals(orderInfoBean.getIs_material())) {
            tvMaterialName.setText(orderInfoBean.getMaterial_des());
        } else {
            tvMaterial.setVisibility(View.GONE);
            tvMaterialName.setVisibility(View.GONE);
        }

        //价格信息
        tvOriginPrice.setText("￥" + orderInfoBean.getCount_integral());
        tvDiscountPrice.setText("￥" + orderInfoBean.getDiscount_integral());
        tvRealPrice.setText("￥" + orderInfoBean.getMoney());
        //
        tvPayPrice.setText("￥" + orderInfoBean.getMoney());
        //


    }

    @OnClick({R.id.iv_back, R.id.iv_right, R.id.ll_weixin_pay, R.id.ll_zhifubao_pay, R.id.submit})
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
                break;
        }
    }

    public void changeStatus(int type) {
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
