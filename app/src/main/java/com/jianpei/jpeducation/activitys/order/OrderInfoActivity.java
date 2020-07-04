package com.jianpei.jpeducation.activitys.order;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.web.KeFuActivity;
import com.jianpei.jpeducation.adapter.order.OrderInfoAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.order.OrderDataBean;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

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
    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.tv_leader)
    TextView tvLeader;
    @BindView(R.id.civ_item)
    CircleImageView civItem;
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

    private OrderInfoAdapter orderInfoAdapter;


    private OrderDataBean orderDataBean;

    int payType = 1;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_order_info;
    }

    @Override
    protected void initView() {
        tvTitle.setText("订单详情");
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.orderconfirm_kefu);
        orderDataBean = getIntent().getParcelableExtra("orderDataBean");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void initData() {
        setData();

    }


    @OnClick({R.id.iv_back, R.id.iv_right, R.id.button, R.id.ll_weixin_pay, R.id.ll_zhifubao_pay, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_right:
                startActivity(new Intent(this, KeFuActivity.class));
                break;
            case R.id.button:
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


    public void setData() {
        if (orderDataBean == null) {
            return;
        }
        if ("0".equals(orderDataBean.getState())) {//未支付
            llWeixinPay.setEnabled(false);
            ivWeixin.setEnabled(false);

        } else if ("1".equals(orderDataBean.getState())) {//已支付
            llBottom.setVisibility(View.GONE);
            llPayType.setVisibility(View.GONE);
            llQuan.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        } else {

        }

//        //优惠券信息
//        if ("1".equals(orderDataBean.getIs_user_coupon())) {
//            ivQuan.setImageResource(R.drawable.icon_quanou_is);
//            tvQuan.setText("有可用优惠券");
//            llQuan.setEnabled(true);
//        }
//
//        if (!TextUtils.isEmpty(mCouponTitle))
//            tvQuan.setText(mCouponTitle);
        //课程
        if (orderDataBean.getGroup_list() == null || orderDataBean.getGroup_list().size() == 0) {
            orderInfoAdapter = new OrderInfoAdapter(this, orderDataBean.getGroup_info(), orderDataBean.getClass_name_str(), orderDataBean.getCount_integral(), orderDataBean.getIs_material(), orderDataBean.getMaterial_des());
        } else {
            orderInfoAdapter = new OrderInfoAdapter(orderDataBean.getGroup_list(), this);
        }
        recyclerView.setAdapter(orderInfoAdapter);
        //订单
        tvOrderNum.setText(orderDataBean.getNumber());
        tvTime.setText(orderDataBean.getAdd_time_str());
        //价格
        tvOriginPrice.setText("￥" + orderDataBean.getCount_integral());
        tvDiscountPrice.setText("￥" + orderDataBean.getDiscount_integral());
        tvRealPrice.setText("￥" + orderDataBean.getMoney());
        //
        tvPayPrice.setText("￥" + orderDataBean.getMoney());

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

}
